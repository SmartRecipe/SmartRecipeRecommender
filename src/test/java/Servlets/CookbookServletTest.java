package Servlets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Payload;
import javax.validation.constraints.Null;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junitparams.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import com.google.gson.Gson;

import Beans.Cookbook;
import Beans.Ingredient;
import Beans.Recipe;
import Beans.User;
import Beans.VirtualRefrigerator;
import Databases.RecipeDatabase;
import Databases.UserDatabase;
import Servlets.utils.BaseRequest;

@RunWith(JUnitParamsRunner.class)
public class CookbookServletTest {
	HttpServletRequest request;     
    HttpServletResponse response;
    ServletContext context;
    
    CookbookServlet underTest;
    
    StringWriter stringWriter;
    PrintWriter writer;
    
    RecipeDatabase mockRecipeDB;
    UserDatabase mockUserDb;
	
	@Before
    public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);       
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
        underTest = spy(new CookbookServlet());
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        mockRecipeDB = mock(RecipeDatabase.class);
		try {
            Field instance = RecipeDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mockRecipeDB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
		mockUserDb = mock(UserDatabase.class);
        try {
            Field instance = UserDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mockUserDb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() throws Exception {
    	mockRecipeDB=null;
    	mockUserDb=null;
		try {
            Field instance = RecipeDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
            instance = UserDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private String recipeToJson(Recipe recipe) {
        Gson gson = new Gson();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRecipe(recipe);
        return gson.toJson(baseRequest);
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
        	underTest.processRequest(request, response);
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
            underTest.processRequest(request, response);
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
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ing1", 1, "cups"));
        ingredients.add(new Ingredient("ing2", 2, "cups"));
        ingredients.add(new Ingredient("ing3", 3, "cups"));
        recipe.setIngredients(ingredients);
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.addRecipe(any(Recipe.class))).thenReturn(true);

        try {
        	underTest.processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
	}
	
   /**
     * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
     */
    @Test
    public void testProcessAddExistingRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.addRecipe(any(Recipe.class))).thenReturn(false);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_CONFLICT, argument.getValue().intValue());
    }

    /**
     * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
     */
    @Test
    public void testProcessAddExceptionRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.addRecipe(any(Recipe.class))).thenThrow(new IllegalArgumentException());

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }

    
	/**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
	 * The recipe json is modified to cause an error
	 * @throws IOException
	 */
	@Test
	public void testProcessCorruptAddRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ing1", 1, "cups"));
        recipe.setIngredients(ingredients);
        String recipeJSON = recipeToJson(recipe).substring(5); //Corrupt the message //TODO test with blank string too
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));

        try {
        	underTest.processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(0)).addRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_BAD_REQUEST, argument.getValue().intValue());
	}
	
	/**
     * @throws IOException 
     */
    @Test
    public void testProcessEditRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("edit_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.updateRecipe(any(Recipe.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).updateRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessEditRequestFail() throws IOException {
        
        when(request.getParameter("action")).thenReturn("edit_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.updateRecipe(any(Recipe.class))).thenReturn(false);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).updateRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessEditRequestError() throws IOException {
        
        when(request.getParameter("action")).thenReturn("edit_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        String recipeJSON = recipeToJson(recipe);
        doReturn(recipeJSON).when(underTest).getBody(any(HttpServletRequest.class));
        
        when(mockRecipeDB.updateRecipe(any(Recipe.class))).thenThrow(new IllegalArgumentException());

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).updateRecipe(any(Recipe.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessSearchRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("search_recipes");
        
        User user = new User();
        VirtualRefrigerator mockFridge = mock(VirtualRefrigerator.class);
        user.setFridge(mockFridge);
        
        String[] filters = new String[] {"test"};
        
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(user);
        baseRequest.setFilters(filters);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        when(mockFridge.checkAllRecipes(ArgumentMatchers.<String>any())).thenReturn(
                Arrays.asList(new Recipe[] {recipe, recipe}));

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockFridge, times(1)).checkAllRecipes(ArgumentMatchers.<String>any());
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessSearchRequestException() throws IOException {
        
        when(request.getParameter("action")).thenReturn("search_recipes");
        
        User user = new User();
        VirtualRefrigerator mockFridge = mock(VirtualRefrigerator.class);
        user.setFridge(mockFridge);
        
        String[] filters = new String[] {"test"};
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(user);
        baseRequest.setFilters(filters);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockFridge.checkAllRecipes(ArgumentMatchers.<String>any())).thenThrow(new NullPointerException());

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockFridge, times(1)).checkAllRecipes(ArgumentMatchers.<String>any());
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessSearchRequestNoUser() throws IOException {
        
        when(request.getParameter("action")).thenReturn("search_recipes");
        
        String[] filters = new String[] {"test"};
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setFilters(filters);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRecommendRecipeRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("recommend_recipe");
        
        User user = new User();
        VirtualRefrigerator mockFridge = mock(VirtualRefrigerator.class);
        user.setFridge(mockFridge);
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(user);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        when(mockFridge.recommendRecipe(any(Cookbook.class))).thenReturn(recipe);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockFridge, times(1)).recommendRecipe(any(Cookbook.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRecommendRecipeRequestNoUser() throws IOException {
        
        when(request.getParameter("action")).thenReturn("recommend_recipe");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        BaseRequest baseRequest = new BaseRequest();

        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddHistoryRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_history");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddHistoryFailRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_history");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(false);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddHistoryNoUserRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_history");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddHistoryNoRecipeRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_history");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddFavoritesRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddFavoritesFailRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(false);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddFavoritesNoUserRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessAddFavoritesNoRecipeRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("add_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    //XXX
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRemoveFavoritesRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("remove_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        User user = new User();
        user.getCookbook().addToFavorites(recipe);
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(user);
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRemoveFavoritesFailRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("remove_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
        
        User user = new User();
        user.getCookbook().addToFavorites(recipe);
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(user);
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(false);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(1)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRemoveFavoritesNoUserRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("remove_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRecipe(recipe);
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_UNAUTHORIZED, argument.getValue().intValue());
    }
    
    /**
     * @throws IOException 
     */
    @Test
    public void testProcessRemoveFavoritesNoRecipeRequest() throws IOException {
        
        when(request.getParameter("action")).thenReturn("remove_favorites");
        
        Recipe recipe = new Recipe();
        recipe.setDesc("Test Description");
                
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUser(new User());
        
        doReturn(baseRequest).when(underTest).getBaseRequest(any(HttpServletRequest.class));
        
        when(mockUserDb.updateUser(any(User.class))).thenReturn(true);

        try {
            underTest.processRequest(request, response);
        } catch (ServletException e) {
            fail("Threw exception");
        }

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockUserDb, times(0)).updateUser(any(User.class));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_INTERNAL_ERROR, argument.getValue().intValue());
    }
    
    //XXX
	
	/**
	 * Test method for {@link CookbookServlet#processRequest(HttpServletRequest, HttpServletResponse)}.
     * @throws IOException 
	 */
	@Test
	public void testProcessGetRecipesRequest() throws IOException {
        
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
        
        when(mockRecipeDB.getAllRecipes()).thenReturn(recipes);

        try {
        	underTest.processRequest(request, response);
		} catch (ServletException e) {
			fail("Threw exception");
		}

        verify(request, atLeast(1)).getParameter("action"); // Verify action checked
        verify(mockRecipeDB, times(1)).getAllRecipes();
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(argument.capture());
        assertEquals(BaseServlet.STATUS_HTTP_OK, argument.getValue().intValue());
        //TODO verify recipes returned correctly
	}
	
}
