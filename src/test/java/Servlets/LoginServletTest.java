package Servlets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
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
	HttpServletRequest request;     
    HttpServletResponse response;
    ServletContext context;
    
    UserDatabase mockDB;
	
	@Before
    public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);       
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        
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
        
        LoginServlet spyTest = spy(new LoginServlet());

        try {
        	spyTest.doGet(request, response);
        	verify(spyTest, times(1)).processRequest(request, response);
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
        
        LoginServlet spyTest = spy(new LoginServlet());
        try {
        	spyTest.doPost(request, response);
        	verify(spyTest, times(1)).processRequest(request, response);
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
        	new LoginServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(context, times(1)).getRequestDispatcher("error.html"); // verify dispatcher called correctly
	}
	
	/**
	 * Test method for {@link LoginServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testProcessSignupRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("sign_up");
        
        Gson gson = new Gson();
        User user = new User();
        user.setEmail("test");
        user.setName("testName");
        when(request.getParameter("user")).thenReturn(gson.toJson(user));
        when(request.getParameter("password")).thenReturn("testpassword");

        try {
        	new LoginServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("user");
        verify(request, atLeast(1)).getParameter("password");
        verify(mockDB, times(1)).addUser(any(User.class)); //TODO compare actual user information once User.equals workss
        verify(request, times(1)).setAttribute(eq("user"), any(User.class)); 
        verify(request, times(1)).setAttribute(eq("token"), any(UUID.class)); 
        verify(context, times(1)).getRequestDispatcher("index.html");
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
        
        when(request.getParameter("user")).thenReturn("");
        when(request.getParameter("password")).thenReturn("testpassword");

        try {
        	new LoginServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("user");
        verify(mockDB, times(0)).addUser(any(User.class));
        verify(request, times(0)).setAttribute(eq("user"), any(User.class)); 
        verify(request, times(0)).setAttribute(eq("token"), anyString()); 
        verify(context, times(1)).getRequestDispatcher("error.html");
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
        when(request.getParameter("email")).thenReturn(user.getEmail());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        
        when(mockDB.login(anyString(), anyString())).thenReturn(user);

        try {
        	new LoginServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(mockDB, times(1)).login(user.getEmail(), user.getPassword());
        verify(request, times(1)).setAttribute("user", user); 
        verify(request, times(1)).setAttribute(eq("token"), any(UUID.class)); 
        verify(context, times(1)).getRequestDispatcher("index.html");
        //TODO run again and verify token is different
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
        when(request.getParameter("email")).thenReturn(user.getEmail());
        when(request.getParameter("password")).thenReturn(user.getPassword()+"change");
        
        when(mockDB.login(anyString(), anyString())).thenReturn(null);

        try {
        	new LoginServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("email");
        verify(request, atLeast(1)).getParameter("password");
        verify(mockDB, times(1)).login(user.getEmail(), user.getPassword()+"change");
        verify(request, times(0)).setAttribute("user", user); 
        verify(request, times(0)).setAttribute(eq("token"), any(UUID.class)); 
        verify(context, times(1)).getRequestDispatcher("login.html");
	}
	
	
}
