package Servlets;

import static org.junit.Assert.*;
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

import com.google.gson.Gson;

import Beans.User;
import Databases.UserDatabase;

@RunWith(JUnitParamsRunner.class)
public class LoginServletTest {
    
    LoginServlet underTest;
    
    HttpServletRequest request;
    HttpServletResponse response;
    ServletContext context;
    
    UserDatabase mockDB;
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
        
        mockDB = mock(UserDatabase.class);
        try {
            Field instance = UserDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mockDB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @After
    public void tearDown() throws Exception {
        mockDB=null;
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
        String userJson = gson.toJson(user);
        
        doReturn(userJson).when(underTest).getBody(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockDB, times(1)).addUser(any(User.class)); //TODO compare actual user information once User.equals workss
        //verify(context, times(1)).getRequestDispatcher("index.html");
        //TODO run again and verify token is different
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
        verify(mockDB, times(0)).addUser(any(User.class));
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
        String userJson = gson.toJson(user);
        
        LoginServlet underTest = spy(new LoginServlet());
        doReturn(userJson).when(underTest).getBody(any(HttpServletRequest.class));
        
        //when(request.getParameter("email")).thenReturn(user.getEmail());
        //when(request.getParameter("password")).thenReturn(user.getPassword());
        
        when(mockDB.login(anyString(), anyString())).thenReturn(user);
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockDB, times(1)).login(user.getEmail(), user.getPassword());
        
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
        String userJson = gson.toJson(user);
        
        doReturn(userJson).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockDB.login(anyString(), anyString())).thenReturn(null);
        try {
            
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }
        
        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockDB, times(1)).login(user.getEmail(), user.getPassword());
        //verify(context, times(1)).getRequestDispatcher("login.html");
    }
    
    
}
