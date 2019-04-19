package Servlets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junitparams.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.google.gson.Gson;

import Beans.User;
import Databases.UserDatabase;
import Servlets.utils.BaseResponse;
import Servlets.utils.PasswordHash;

@RunWith(JUnitParamsRunner.class)
public class LoginServletTest {
    
    LoginServlet underTest;
    
    HttpServletRequest request;
    HttpServletResponse response;
    ServletContext context;
    
    UserDatabase mockUserDB;
    StringWriter stringWriter;
    PrintWriter writer;
    Gson gson = new Gson();
    
    @Before
    public void setUp() throws Exception {
        underTest = spy(new LoginServlet());
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        mockUserDB = mock(UserDatabase.class);
        try {
            Field instance = UserDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mockUserDB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @After
    public void tearDown() throws Exception {
        mockUserDB=null;
        try {
            Field instance = UserDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Test method for {@link LoginServlet#doGet(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testGetRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");
        
        try {
            underTest.doGet(request, response);
            verify(underTest, times(1)).processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
    }
    
    /**
     * Test method for {@link LoginServlet#doPost(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testPostRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");
        
        try {
            underTest.doPost(request, response);
            verify(underTest, times(1)).processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessBadRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        //verify(context, times(1)).getRequestDispatcher("error.html"); // verify dispatcher called correctly
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessSignupRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("sign_up");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("testpassword");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        doReturn(gson.toJson(baseResponse)).when(underTest).getBody(any(HttpServletRequest.class));
        doReturn(true).when(mockUserDB).addUser(any(User.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        
        verify(mockUserDB, times(1)).addUser(any(User.class));
        
        writer.flush(); // it may not have been flushed yet...
        baseResponse = gson.fromJson(stringWriter.toString(), BaseResponse.class);
        assertEquals(user.getEmail(), baseResponse.getUser().getEmail());
        assertEquals(user.getPassword(), baseResponse.getUser().getPassword()); // Password should be hashed
        
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessSignupRequestExistingUser() throws IOException {
        
        when(request.getParameter("action")).thenReturn("sign_up");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("testpassword");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        doReturn(gson.toJson(baseResponse)).when(underTest).getBody(any(HttpServletRequest.class));
        doReturn(false).when(mockUserDB).addUser(any(User.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        
        writer.flush(); // it may not have been flushed yet...
        System.out.println(stringWriter.toString());
        
        verify(mockUserDB, times(1)).addUser(any(User.class));
        
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_CONFLICT, argument.getValue().intValue());
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessSignupRequestNoPassword() throws IOException {
        
        when(request.getParameter("action")).thenReturn("sign_up");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        doReturn(gson.toJson(baseResponse)).when(underTest).getBody(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        
        verify(mockUserDB, times(0)).addUser(any(User.class));
        
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessRequestCorrupt() throws IOException {
        
        when(request.getParameter("action")).thenReturn("sign_up");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("asdf");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        doReturn(gson.toJson(baseResponse).substring(15)).when(underTest).getBody(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
                
        verify(mockUserDB, times(0)).addUser(any(User.class));
        
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_BAD_REQUEST, argument.getValue().intValue());
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessBadSignupRequest() throws IOException {
        
        //TODO test corrupted user, test empty password, test missing parameters
        when(request.getParameter("action")).thenReturn("sign_up");
        
        User user = new User();
        user.setName("");
        user.setPassword("testpassword");
        String userJson = gson.toJson(user);
        
        doReturn(userJson).when(underTest).getBody(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDB, times(0)).addUser(any(User.class));
        //verify(context, times(1)).getRequestDispatcher("error.html");
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessLoginRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("login");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("testpassword");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        String json = gson.toJson(baseResponse);
        
        LoginServlet underTest = spy(new LoginServlet());
        doReturn(json).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockUserDB.login(anyString(), anyString())).thenReturn(user);
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        
        writer.flush(); // it may not have been flushed yet...
        //TODO check returned string
        //assertTrue(stringWriter.toString().contains("My expected string"));
    }
    
    /**
     * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException
     */
    @Test
    public void testProcessLoginRequestIncorrectPassword() throws IOException {
        
        when(request.getParameter("action")).thenReturn("login");
        
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        user.setPassword("testpassword");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setUser(user);
        String json = gson.toJson(baseResponse);
        
        doReturn(json).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockUserDB.login(anyString(), anyString())).thenReturn(null);
        try {
            
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        // FIXME fix once we know this works
        //verify(mockDB, times(1)).login(user.getEmail(), user.getPassword());
        //verify(context, times(1)).getRequestDispatcher("login.html");
    }
    
    
}
