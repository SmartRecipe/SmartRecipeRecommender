package Servlets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

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

import Beans.Ingredient;
import Beans.Recipe;
import Databases.RecipeDatabase;

@RunWith(JUnitParamsRunner.class)
public class CookbookServletTest {
	HttpServletRequest request;     
    HttpServletResponse response;
    ServletContext context;
    
    StringWriter stringWriter;
    PrintWriter writer;
    
    RecipeDatabase mockDB;
	
	@Before
    public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);       
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        mockDB = mock(RecipeDatabase.class);
		try {
            Field instance = RecipeDatabase.class.getDeclaredField("instance");
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
            Field instance = RecipeDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
	 * Test method for {@link CookbookServlet#doGet(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testGetRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");
        
        CookbookServlet spyTest = spy(new CookbookServlet());

        try {
        	spyTest.doGet(request, response);
        	verify(spyTest, times(1)).processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}
	}
	
	/**
	 * Test method for {@link CookbookServlet#doPost(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testPostRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");
        
        CookbookServlet spyTest = spy(new CookbookServlet());

        try {
        	spyTest.doPost(request, response);
        	verify(spyTest, times(1)).processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}
        
	}
    
    /**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testProcessBadRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("bad_action");

        try {
        	new CookbookServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_NOT_FOUND, argument.getValue().intValue());
	}
	
    /**
     * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
     */
    @Test
    public void testProcessMissingParameterRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn(null);

        try {
            new CookbookServlet().processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_NOT_FOUND, argument.getValue().intValue());
    }
	
	/**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testProcessAddRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_recipe");
        
        Gson gson = new Gson();
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ing1", 1, "cups"));
        ingredients.add(new Ingredient("ing2", 2, "cups"));
        ingredients.add(new Ingredient("ing3", 3, "cups"));
        recipe.setIngredients(ingredients);
        String recipeJSON = gson.toJson(recipe);
        when(request.getParameter("recipe")).thenReturn(recipeJSON);

        try {
        	new CookbookServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("recipe"); 
        verify(mockDB, times(1)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
	}
	
	/**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
	 * The recipe json is modified to cause an error
	 * @throws IOException
	 */
	@Test
	public void testProcessCorruptAddRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_recipe");
        
        Gson gson = new Gson();
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ing1", 1, "cups"));
        recipe.setIngredients(ingredients);
        String recipeJSON = gson.toJson(recipe);
        recipeJSON = recipeJSON.substring(5); //Corrupt the message //TODO test with blank string too
        when(request.getParameter("recipe")).thenReturn(recipeJSON);

        try {
        	new CookbookServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(request, atLeast(1)).getParameter("recipe"); 
        verify(mockDB, times(0)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
	}
	
	/**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testProcessRecipesRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("get_recipes");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ing1", 1, "cups"));
        ingredients.add(new Ingredient("ing2", 2, "cups"));
        ingredients.add(new Ingredient("ing3", 3, "cups"));
        recipe.setIngredients(ingredients);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        
        when(mockDB.getAllRecipes()).thenReturn(recipes);

        try {
        	new CookbookServlet().processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockDB, times(1)).getAllRecipes();
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
        //TODO verify recipes returned correctly
	}
	
}
