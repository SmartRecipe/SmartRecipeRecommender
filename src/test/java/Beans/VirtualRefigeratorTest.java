 /**
  *
  */
package Beans;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import Databases.RecipeDatabase;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * @author ryan.potocnik
 *
 */
@RunWith(JUnitParamsRunner.class)
public class VirtualRefigeratorTest {
    
    RecipeDatabase mockDB;
    VirtualRefrigerator underTest;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        underTest = new VirtualRefrigerator();
        mockDB = mock(RecipeDatabase.class);
        try {
            Field instance = RecipeDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mockDB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @throws java.lang.Exception
     */
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
     * Create arraylist of ingredients with length specified.
     * Ingredient name will be ing<index> and quantity will be <index+1>
     * @param numIngredients
     * @return
     */
    public static ArrayList<Ingredient> createIngredientList(int numIngredients) {
        ArrayList<Ingredient> baseIngredients = new ArrayList<>();
        for (int i=0; i<numIngredients; i++) {
            baseIngredients.add(new Ingredient("ing"+i, i+1, "cups"));
        }
        return baseIngredients;
    }
    
    public static Recipe createRecipe(int numIngredients) {
        Recipe r = new Recipe();
        r.setName("Recipe "+numIngredients);
        r.setIngredients(createIngredientList(numIngredients));
        return r;
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#checkRecipe(Beans.Recipe, boolean)}.
     */
    @Test
    @Parameters(method = "parametersCheckRecipe")
    public void testCheckRecipe(Recipe dbRecipe, ArrayList<Ingredient> userIngredients, boolean expectedValue ) {
        underTest.setIngredientsList(userIngredients);
        assertEquals(expectedValue, underTest.checkRecipe(dbRecipe, false));
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersCheckRecipe() {
        ArrayList<Ingredient> ingList1 = createIngredientList(10);
        ingList1.add(0, null);
        Recipe r1  = createRecipe(10);
        r1.getIngredients().get(5).setQuantity(10);
        
        Recipe r2  = createRecipe(10);
        r2.getIngredients().get(8).setQuantity(2);
        
        Recipe r3  = createRecipe(10);
        r3.getIngredients().get(8).setName("name changed");
        
        Recipe r4  = createRecipe(10);
        r4.getIngredients().get(6).setName(null);
        
        return new Object[] {
            new Object[] { createRecipe(10), createIngredientList(10), true },
            new Object[] { createRecipe(10), createIngredientList(11), true },
            new Object[] { createRecipe(10), createIngredientList(9), false }, // User is missing an ingredient
            new Object[] { r1, ingList1, false }, // Not enough quantity
            new Object[] { r2, createIngredientList(10), true }, // More than enough quantity
            new Object[] { r3, createIngredientList(10), false }, // Missing ingredient name
            new Object[] { r4, createIngredientList(10), false }, // Null ingredient name
            new Object[] { null, createIngredientList(10), false }, // null ingredient
            new Object[] { createRecipe(10), createIngredientList(0), false } // No ingredients
        };
    }
	

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#checkAllRecipes()}.
	 */
	@Test
	@Parameters(method = "parametersCheckAllRecipes")
	public void testCheckAllRecipes(ArrayList<Recipe> dbRecipes, ArrayList<Ingredient> userIngredients, String[] filters, int expectedRecipes) {
	    underTest.setIngredientsList(userIngredients);
		when(mockDB.getAllRecipes()).thenReturn(dbRecipes);
		List<Recipe> retVal = underTest.checkAllRecipes(false, filters);
		assertNotNull(retVal);
		verify(mockDB, times(1)).getAllRecipes();
		assertEquals("Recipes: "+dbRecipes.size()+", ingredients: "+userIngredients.size(), expectedRecipes, retVal.size());
	}
	
	public static ArrayList<Recipe> createRecipeList(int lowIngNum, int highIngNum) {
		ArrayList<Recipe> recipeList = new ArrayList<>();
		for (int i=lowIngNum; i<=highIngNum; i++) {
			recipeList.add(createRecipe(i));
		}
		return recipeList;
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersCheckAllRecipes() {
	    ArrayList<Recipe> r1 = createRecipeList(5, 15);
	    r1.get(0).setFlavorTags(Arrays.asList(new String[] {"test1", "test2", "TEST3"}));
	    r1.get(2).setFlavorTags(Arrays.asList(new String[] {"test1", "test3"}));
	    r1.get(3).setFlavorTags(Arrays.asList(new String[] {"test1", "missing"}));
		
        return new Object[] { 
            new Object[] { createRecipeList(5, 15), createIngredientList(10), null, 6 },
            new Object[] { r1, createIngredientList(10), new String[] {"test1", "TEST3"}, 2 },
            new Object[] { createRecipeList(10, 15), createIngredientList(10), new String[] {}, 1 },
            new Object[] { createRecipeList(10, 15), createIngredientList(2), null, 0 }
        };
    }
	
	/**
     * Test method for {@link Beans.VirtualRefrigerator#recommendRecipe(Cookbook)}.
     */
    @Test
    @Parameters(method = "parametersRecommendRecipe")
    public void testRecommendRecipe(Cookbook cookbook, List<String> possibleRecipeNames, boolean useRecipes) {
        VirtualRefrigerator spiedTest = spy(underTest);
        ArrayList<Recipe> dbRecipes = null;
        if (useRecipes) {
            dbRecipes = createRecipeList(1, 5);
            dbRecipes.get(0).setFlavorTags(Arrays.asList(new String[] {"test1", "test2", "TEST3"}));
            dbRecipes.get(1).setFlavorTags(Arrays.asList(new String[] {"test1", "test3"}));
            dbRecipes.get(2).setFlavorTags(Arrays.asList(new String[] {"test1", "missing"}));
            dbRecipes.get(3).setFlavorTags(Arrays.asList(new String[] {"missing"}));
            dbRecipes.get(4).setFlavorTags(Arrays.asList(new String[] {"apple", "test3"}));
        } else {
           dbRecipes = new ArrayList<>(); 
        }
        
        doReturn(dbRecipes).when(spiedTest).checkAllRecipes(anyBoolean(), ArgumentMatchers.<String>any());
        
        Recipe retVal = spiedTest.recommendRecipe(cookbook);
        
        if (possibleRecipeNames.size() == 0) {
            assertNull(retVal);
        } else {
            assertNotNull(retVal);
            boolean found = false;
            for (String recipeName : possibleRecipeNames) {
                if (recipeName.equals(retVal.getName())) {
                    found = true;
                    break;
                }
            }
            assertTrue("Unexpected recipe: "+retVal.getName(),found);
        }
        
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersRecommendRecipe() {
        
        Cookbook c1 = new Cookbook();
        ArrayList<Recipe> h1 = createRecipeList(1, 2);
        h1.get(0).setFlavorTags(Arrays.asList(new String[] {"apple"}));
        h1.get(1).setFlavorTags(Arrays.asList(new String[] {"apple","test1"}));
        c1.setHistory(h1);
        
        Cookbook c2 = new Cookbook();
        ArrayList<Recipe> h2 = createRecipeList(1, 2);
        h2.get(0).setFlavorTags(Arrays.asList(new String[] {"test1"}));
        h2.get(1).setFlavorTags(Arrays.asList(new String[] {"test3","test1"}));
        c2.setHistory(h2);
        
        
        return new Object[] {
            new Object[] { c2, new ArrayList<>(), false },
            new Object[] { new Cookbook(), Arrays.asList("Recipe 1", "Recipe 2", "Recipe 3", "Recipe 4", "Recipe 5"), true },
            new Object[] { c1, Arrays.asList("Recipe 5"), true },
            new Object[] { c2, Arrays.asList("Recipe 1", "Recipe 2", "Recipe 3"), true }
        };
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String, double)}.
     */
    @Test
    public void testAddNewIngredient() {
        ArrayList<Ingredient> testList = new ArrayList<>();
        testList.add(new Ingredient("ing1", 1, "cups"));
        testList.add(new Ingredient("ing2", 1, "cups"));
        underTest.setIngredientsList(testList);
        
        assertEquals(2, underTest.getIngredientsList().size());
        
        Ingredient testIngredient = new Ingredient("testIngredient", 5, "cups");
        assertTrue(underTest.addIngredient(testIngredient));
        assertEquals(3, underTest.getIngredientsList().size());
        assertTrue(testList.contains(testIngredient));
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String, double)}.
     */
    @Test
    public void testAddExistingIngredientWithQuantity() {
        ArrayList<Ingredient> testList = new ArrayList<>();
        testList.add(new Ingredient("ing1", 1, "cups"));
        testList.add(new Ingredient("ing2", 1, "cups"));
        underTest.setIngredientsList(testList);
        
        assertEquals(2, underTest.getIngredientsList().size());
        
        Ingredient ing = new Ingredient(testList.get(0).getName(), 100, "cups"); // TODO test different unit
        
        assertTrue(underTest.addIngredient(ing, 100));
        assertEquals(2, underTest.getIngredientsList().size());
        assertEquals(101, testList.get(0).getQuantity(), 0);
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String)}.
     */
    @Test
    public void testAddExistingIngredient() {
        List<Ingredient> testList = new ArrayList<>();
        testList.add(new Ingredient("ing1", 1, "cups"));
        testList.add(new Ingredient("ing2", 1, "cups"));
        underTest = new VirtualRefrigerator(testList);
        
        assertEquals(2, underTest.getIngredientsList().size());
        
        Ingredient ing = new Ingredient(testList.get(0).getName(), 50, "cups"); // TODO test different unit
        
        assertTrue(underTest.addIngredient(ing));
        assertEquals(2, underTest.getIngredientsList().size());
        assertEquals(51, testList.get(0).getQuantity(), 0);
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String)}.
     */
    @Test
    public void testAddIngredientWithNull() {
        ArrayList<Ingredient> testList = new ArrayList<>();
        testList.add(new Ingredient("ing1", 1, "cups"));
        testList.add(new Ingredient("ing2", 1, "cups"));
        underTest.setIngredientsList(testList);
        
        assertEquals(2, underTest.getIngredientsList().size());
        
        assertFalse(underTest.addIngredient(null));
        assertEquals(2, underTest.getIngredientsList().size());
        assertEquals(1, testList.get(0).getQuantity(), 0);
        
        assertFalse(underTest.addIngredient(null, 50));
        assertEquals(2, underTest.getIngredientsList().size());
        assertEquals(1, testList.get(0).getQuantity(), 0);
    }
    
    @Test
    @Parameters(method = "parametersUseAllIngredient")
    public void testUseAllIngredient(List<Ingredient> ingredientList, Ingredient ingredient, boolean expectedReturn) {
        int origLength = ingredientList.size();
        underTest.setIngredientsList(ingredientList);
        boolean returnVal = underTest.useAll(ingredient);
        List<Ingredient> currentIngredients = underTest.getIngredientsList();
        assertNotNull(underTest);
        assertEquals(expectedReturn, returnVal);
        assertNotNull(currentIngredients);
        if (expectedReturn) {
            assertEquals(origLength-1, currentIngredients.size());
        }
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#useIngredient(Ingredient)}.
     */
    @Test
    @Parameters(method = "parametersUseIngredient")
    public void testUseIngredient(List<Ingredient> ingredientList, Ingredient ingredient,
            boolean expectedReturn, double expectedQuantity) {
        underTest.setIngredientsList(ingredientList);
        boolean returnVal = underTest.useIngredient(ingredient);
        
        List<Beans.Ingredient> currentIngredients = underTest.getIngredientsList();
        assertNotNull(underTest);
        assertEquals(expectedReturn, returnVal);
        assertNotNull(currentIngredients);
        if (ingredient != null && currentIngredients.contains(ingredient)) {
            assertEquals(ingredient.getQuantity(), expectedQuantity, 0.0);
        }
        if (expectedReturn == true && expectedQuantity == 0) {
            assertFalse("Ingredient not removed", currentIngredients.contains(ingredient));
        }
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#useIngredient(Ingredient, double)}.
     */
    @Test
    @Parameters(method = "parametersUseIngredientQuantity")
    public void testUseIngredientQuantity(List<Ingredient> ingredientList, Ingredient ingredient, int quantity,
            boolean expectedReturn, double expectedQuantity) {
        underTest.setIngredientsList(ingredientList);
        boolean returnVal = underTest.useIngredient(ingredient, quantity);
        
        List<Beans.Ingredient> currentIngredients = underTest.getIngredientsList();
        assertNotNull(underTest);
        assertEquals(expectedReturn, returnVal);
        assertNotNull(currentIngredients);
        if (ingredient != null && currentIngredients.contains(ingredient)) {
            assertEquals(ingredient.getQuantity(), expectedQuantity, 0.0);
        }
        if (expectedReturn == true && expectedQuantity == 0) {
            assertFalse("Ingredient not removed", currentIngredients.contains(ingredient));
        }
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#getIngredient(java.lang.String)}.
     */
    @Test
    @Parameters(method = "parametersGetIngredient")
    public void testGetIngredient(List<Ingredient> ingredientList, String name, Ingredient expectedIngredient) {
        underTest.setIngredientsList(ingredientList);
        Ingredient returnVal = underTest.getIngredient(name);
        List<Ingredient> currentIngredients = underTest.getIngredientsList();
        assertNotNull(underTest);
        assertEquals("Unable to find: "+name, expectedIngredient, returnVal);
        assertNotNull(currentIngredients);
    }
    
    /**
     * Test method for {@link Beans.VirtualRefrigerator#setIngredients(java.util.ArrayList)}.
     */
    @Test
    public void testSetIngredients() {
        ArrayList<Ingredient> testList = new ArrayList<>();
        testList.add(new Ingredient("ing1", 1, "cups"));
        testList.add(new Ingredient("ing2", 1, "cups"));
        
        //Check normal array
        underTest.setIngredientsList(testList);
        assertEquals(2, underTest.getIngredientsList().size());
        
        //Check not setting null
        underTest.setIngredientsList(null);
        assertEquals(2, underTest.getIngredientsList().size());
        
        //Check empty array list
        testList = new ArrayList<>();
        underTest.setIngredientsList(testList);
        assertEquals(0, underTest.getIngredientsList().size());
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersUseIngredientQuantity() {
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient ing1 = new Ingredient("Test1", 10, "cups");
        ingredients1.add(ing1);
        
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        Ingredient ing2 = new Ingredient("Test2", 10, "cups");
        ingredients2.add(ing2);
        
        ArrayList<Ingredient> ingredients3 = new ArrayList<>();
        Ingredient ing3 = new Ingredient("Test3", 8, "cups");
        ingredients3.add(ing3);
        
        ArrayList<Ingredient> ingredients4 = new ArrayList<>();
        Ingredient ing4 = new Ingredient("Test4", 8, "cups");
        ingredients4.add(ing4);
        
        ArrayList<Ingredient> ingredients5 = new ArrayList<>();
        Ingredient ing5 = new Ingredient("Test5", 10, "cups");
        ingredients5.add(new Ingredient("a", 10, "cups"));
        ingredients5.add(new Ingredient("b", 10, "cups"));
        ingredients5.add(new Ingredient("c", 10, "cups"));
        ingredients5.add(ing5);
        
        ArrayList<Ingredient> ingredients6 = new ArrayList<>();
        Ingredient ing6 = new Ingredient("Test6", 10, "cups");
        ingredients6.add(new Ingredient("a", 10, "cups"));
        ingredients6.add(ing6);
        ingredients6.add(new Ingredient("b", 10, "cups"));
        ingredients6.add(new Ingredient("c", 10, "cups"));
        
        
        return new Object[] {
            new Object[] { ingredients1, ing1, 5, true, 5 },
            new Object[] { ingredients2, ing2, 8, true, 2 },
            new Object[] { ingredients3, ing3, 15, false, 8 },
            new Object[] { ingredients4, ing4, -1, false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2, "cups")}), null, 0, false, 8 },
            new Object[] { null, new Ingredient("test", 2, "cups"), 0, false, 5 },
            new Object[] { Arrays.asList(new Ingredient[] {null}), new Ingredient("test", 2, "cups"), 0, false, 5 },
            new Object[] { ingredients5, ing5, 4, true, 6 },
            new Object[] { new ArrayList<Ingredient>(), new Ingredient(null, 5, "cups"), 4, false, 6 },
            new Object[] { ingredients6, ing6, 10, true, 0 }
        };
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersUseIngredient() {
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient ing1 = new Ingredient("Test1", 10, "cups");
        ingredients1.add(ing1);
        
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        Ingredient ing2 = new Ingredient("Test2", 10, "cups");
        ingredients2.add(ing2);
        
        ArrayList<Ingredient> ingredients3 = new ArrayList<>();
        Ingredient ing3 = new Ingredient("Test3", 8, "cups");
        ingredients3.add(ing3);
        
        ArrayList<Ingredient> ingredients4 = new ArrayList<>();
        Ingredient ing4 = new Ingredient("Test4", 8, "cups");
        ingredients4.add(ing4);
        
        ArrayList<Ingredient> ingredients5 = new ArrayList<>();
        Ingredient ing5 = new Ingredient("Test5", 10, "cups");
        ingredients5.add(new Ingredient("a", 10, "cups"));
        ingredients5.add(new Ingredient("b", 10, "cups"));
        ingredients5.add(new Ingredient("c", 10, "cups"));
        ingredients5.add(ing5);
        
        ArrayList<Ingredient> ingredients6 = new ArrayList<>();
        Ingredient ing6 = new Ingredient("Test6", 10, "cups");
        ingredients6.add(new Ingredient("a", 10, "cups"));
        ingredients6.add(ing6);
        ingredients6.add(new Ingredient("b", 10, "cups"));
        ingredients6.add(new Ingredient("c", 10, "cups"));
        
        
        return new Object[] {
            new Object[] { ingredients1, new Ingredient(ing1.getName(), 5, "cups"), true, 5 },
            new Object[] { ingredients2, new Ingredient(ing2.getName(), 8, "cups"), true, 2 },
            new Object[] { ingredients3, new Ingredient(ing3.getName(), 15, "cups"), false, 8 },
            new Object[] { ingredients4, new Ingredient(ing4.getName(), -1, "cups"), false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2, "cups")}), new Ingredient(null, 0, "cups"), false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2, "cups")}), null, false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2, "cups")}), new Ingredient("a", 5, "cups"), false, 5 },
            new Object[] { Arrays.asList(new Ingredient[] {null}), new Ingredient("test", 2, "cups"), false, 5 },
            new Object[] { ingredients5, new Ingredient(ing5.getName(), 4, "cups"), true, 6 },
            new Object[] { ingredients6, new Ingredient(ing6.getName(), 10, "cups"), true, 0 }
        };
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersUseAllIngredient() {
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient ing1 = new Ingredient("Test", 10, "cups");
        ingredients1.add(ing1);
        
        ArrayList<Ingredient> nullIngredients = new ArrayList<>();
        nullIngredients.add(null);
        
        ArrayList<Ingredient> ingredients5 = new ArrayList<>();
        Ingredient ing5 = new Ingredient("Test", 10, "cups");
        ingredients5.add(new Ingredient("a", 10, "cups"));
        ingredients5.add(new Ingredient("b", 10, "cups"));
        ingredients5.add(new Ingredient("c", 10, "cups"));
        ingredients5.add(ing5);
        
        
        return new Object[] {
            new Object[] { ingredients1, ing1, true },
            new Object[] { new ArrayList<Ingredient>(), null, false },
            new Object[] { ingredients5, ing5, true },
            new Object[] { new ArrayList<Ingredient>(), new Ingredient("test", 5, "cups"), false }
        };
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersGetIngredient() {
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient ing1 = new Ingredient("Test", 10, "cups");
        ingredients1.add(ing1);
        
        ArrayList<Ingredient> nullIngredients = new ArrayList<>();
        nullIngredients.add(null);
        
        ArrayList<Ingredient> ingredients5 = new ArrayList<>();
        Ingredient ing5 = new Ingredient("Test", 10, "cups");
        ingredients5.add(new Ingredient("a", 10, "cups"));
        ingredients5.add(new Ingredient("b", 10, "cups"));
        ingredients5.add(new Ingredient("c", 10, "cups"));
        ingredients5.add(ing5);
        
        
        return new Object[] {
            new Object[] { ingredients1, ing1.getName(), ing1 },
            new Object[] { ingredients5, ing5.getName(), ing5 },
            new Object[] { nullIngredients, ing1, null },
            new Object[] { ingredients5, null, null },
            new Object[] { ingredients5, "X", null }
        };
    }
    
}
