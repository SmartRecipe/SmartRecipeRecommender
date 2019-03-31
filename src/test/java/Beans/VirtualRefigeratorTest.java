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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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
	private ArrayList<Ingredient> createIngredientList(int numIngredients) {
		ArrayList<Ingredient> baseIngredients = new ArrayList<>();
		for (int i=0; i<numIngredients; i++) {
			baseIngredients.add(new Ingredient("ing"+i, i+1));
		}
		return baseIngredients;
	}
	
	private Recipe createRecipe(int numIngredients) {
		Recipe r = new Recipe();
		r.setIngredients(createIngredientList(numIngredients));
		return r;
	}

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#checkRecipe(Beans.Recipe)}.
	 */
	@Test
	@Parameters(method = "parametersCheckRecipe")
	public void testCheckRecipe(Recipe dbRecipe, ArrayList<Ingredient> userIngredients, boolean expectedValue ) {
		VirtualRefrigerator underTest = VirtualRefrigerator.getInstance();
		underTest.setIngredientsList(userIngredients);
		assertEquals(expectedValue, underTest.checkRecipe(dbRecipe));
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersCheckRecipe() {
		Recipe r1  = createRecipe(10);
		r1.getIngredients().get(5).setQuantity(10);
		
		Recipe r2  = createRecipe(10);
		r2.getIngredients().get(8).setQuantity(2);
		
		Recipe r3  = createRecipe(10);
		r3.getIngredients().get(8).setName("name changed");
		
		Recipe r4  = createRecipe(10);
		r4.getIngredients().get(6).setName(null);
		
		Recipe r5  = createRecipe(10);
		r5.getIngredients().get(6).setName(r5.getIngredients().get(6).getName().toUpperCase());
		
        return new Object[] { 
            new Object[] { createRecipe(10), createIngredientList(10), true },
            new Object[] { createRecipe(10), createIngredientList(11), true },
            new Object[] { createRecipe(10), createIngredientList(9), false }, // User is missing an ingredient
            new Object[] { r1, createIngredientList(10), false }, //Not enough quantity
            new Object[] { r2, createIngredientList(10), true }, //More than enough quantity
            new Object[] { r3, createIngredientList(10), false }, //Missing ingredient name
            new Object[] { r4, createIngredientList(10), false }, //Null ingredient name
            new Object[] { r5, createIngredientList(10), false } //Ingredient name changed case
            };
    }
	

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#checkAllRecipes()}.
	 */
	@Test
	@Parameters(method = "parametersCheckAllRecipes")
	public void testCheckAllRecipes(ArrayList<Recipe> dbRecipes, ArrayList<Ingredient> userIngredients, int expectedRecipes) {
		VirtualRefrigerator underTest = VirtualRefrigerator.getInstance();
		underTest.setIngredientsList(userIngredients);
		when(mockDB.getAllRecipes()).thenReturn(dbRecipes);
		ArrayList<Recipe> retVal = underTest.checkAllRecipes();
		assertNotNull(retVal);
		assertEquals("Recipes: "+dbRecipes.size()+", ingredients: "+userIngredients.size(), expectedRecipes, retVal.size());
		if (dbRecipes != null) {
			verify(mockDB, times(dbRecipes.size())).getAllRecipes();
		}
	}
	
	private ArrayList<Recipe> createRecipeList(int lowIngNum, int highIngNum) {
		ArrayList<Recipe> recipeList = new ArrayList<>();
		for (int i=lowIngNum; i<=highIngNum; i++) {
			recipeList.add(createRecipe(i));
		}
		return recipeList;
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersCheckAllRecipes() {
		
        return new Object[] { 
            new Object[] { createRecipeList(5, 15), createIngredientList(10), 6 },
            new Object[] { createRecipeList(10, 15), createIngredientList(10), 1 },
            new Object[] { createRecipeList(10, 15), createIngredientList(2), 0 }
            };
    }

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String, double)}.
	 */
	@Test
	public void testAddNewIngredient() {
		VirtualRefrigerator underTest = VirtualRefrigerator.getInstance();
		ArrayList<Ingredient> testList = new ArrayList<>();
		testList.add(new Ingredient("ing1", 1));
		testList.add(new Ingredient("ing2", 1));
		underTest.setIngredientsList(testList);
		
		assertEquals(2, underTest.getIngredientsList().size());
		
		Ingredient testIngredient = new Ingredient("testIngredeint", 5);
		underTest.addIngredient(testIngredient);
		assertEquals(3, underTest.getIngredientsList().size());	
		assertTrue(testList.contains(testIngredient));
	}
	
	/**
	 * Test method for {@link Beans.VirtualRefrigerator#addIngredient(java.lang.String, double)}.
	 */
	@Test
	public void testAddExistingIngredient() {
		VirtualRefrigerator underTest = VirtualRefrigerator.getInstance();
		ArrayList<Ingredient> testList = new ArrayList<>();
		testList.add(new Ingredient("ing1", 1));
		testList.add(new Ingredient("ing2", 1));
		underTest.setIngredientsList(testList);
		
		assertEquals(2, underTest.getIngredientsList().size());
		
		underTest.addIngredient(testList.get(0), 100);
		assertEquals(2, underTest.getIngredientsList().size());
		assertEquals(101, testList.get(0).getQuantity(), 0);
	}
	
	@Test
	@Parameters(method = "parametersUseAllIngredient")
	public void testUseAllIngredient(List<Ingredient> ingredientList, Ingredient ingredient, boolean expectedReturn) {
		VirtualRefrigerator virtualRefrigerator = Beans.VirtualRefrigerator.getInstance();
		int origLength = ingredientList.size();
		virtualRefrigerator.setIngredientsList(ingredientList);
        boolean returnVal = virtualRefrigerator.useAll(ingredient);
        List<Ingredient> currentIngredients = virtualRefrigerator.getIngredientsList();
        assertNotNull(virtualRefrigerator);
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
		VirtualRefrigerator virtualRefrigerator = Beans.VirtualRefrigerator.getInstance();
		virtualRefrigerator.setIngredientsList(ingredientList);
        boolean returnVal = virtualRefrigerator.useIngredient(ingredient);

        List<Beans.Ingredient> currentIngredients = virtualRefrigerator.getIngredientsList();
        assertNotNull(virtualRefrigerator);
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
		VirtualRefrigerator virtualRefrigerator = Beans.VirtualRefrigerator.getInstance();
		virtualRefrigerator.setIngredientsList(ingredientList);
        boolean returnVal = virtualRefrigerator.useIngredient(ingredient, quantity);

        List<Beans.Ingredient> currentIngredients = virtualRefrigerator.getIngredientsList();
        assertNotNull(virtualRefrigerator);
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
		VirtualRefrigerator virtualRefrigerator = Beans.VirtualRefrigerator.getInstance();
		virtualRefrigerator.setIngredientsList(ingredientList);
        Ingredient returnVal = virtualRefrigerator.getIngredient(name);
        List<Ingredient> currentIngredients = virtualRefrigerator.getIngredientsList();
        assertNotNull(virtualRefrigerator);
        assertEquals("Unable to find: "+name, expectedIngredient, returnVal);
        assertNotNull(currentIngredients);
	}
	
	/**
	 * Test method for {@link Beans.VirtualRefrigerator#setIngredients(java.util.ArrayList)}.
	 */
	@Test
	public void testSetIngredients() {
		VirtualRefrigerator underTest = VirtualRefrigerator.getInstance();
		ArrayList<Ingredient> testList = new ArrayList<>();
		testList.add(new Ingredient("ing1", 1));
		testList.add(new Ingredient("ing2", 1));
		
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
		Ingredient ing1 = new Ingredient("Test1", 10);
		ingredients1.add(ing1);
		
		ArrayList<Ingredient> ingredients2 = new ArrayList<>();
		Ingredient ing2 = new Ingredient("Test2", 10);
		ingredients2.add(ing2);
		
		ArrayList<Ingredient> ingredients3 = new ArrayList<>();
		Ingredient ing3 = new Ingredient("Test3", 8);
		ingredients3.add(ing3);
		
		ArrayList<Ingredient> ingredients4 = new ArrayList<>();
		Ingredient ing4 = new Ingredient("Test4", 8);
		ingredients4.add(ing4);
		
		ArrayList<Ingredient> ingredients5 = new ArrayList<>();
		Ingredient ing5 = new Ingredient("Test5", 10);
		ingredients5.add(new Ingredient("a", 10));
		ingredients5.add(new Ingredient("b", 10));
		ingredients5.add(new Ingredient("c", 10));
		ingredients5.add(ing5);
		
		ArrayList<Ingredient> ingredients6 = new ArrayList<>();
		Ingredient ing6 = new Ingredient("Test6", 10);
		ingredients6.add(new Ingredient("a", 10));
		ingredients6.add(ing6);
		ingredients6.add(new Ingredient("b", 10));
		ingredients6.add(new Ingredient("c", 10));
		
		
		return new Object[] { 
            new Object[] { ingredients1, ing1, 5, true, 5 },
            new Object[] { ingredients2, ing2, 8, true, 2 },
            new Object[] { ingredients3, ing3, 15, false, 8 },
            new Object[] { ingredients4, ing4, -1, false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2)}), null, 0, false, 8 },
            new Object[] { null, new Ingredient("test", 2), 0, false, 5 },
            new Object[] { Arrays.asList(new Ingredient[] {null}), new Ingredient("test", 2), 0, false, 5 },
            new Object[] { ingredients5, ing5, 4, true, 6 },
            new Object[] { new ArrayList<Ingredient>(), new Ingredient(null, 5), 4, false, 6 },
            new Object[] { ingredients6, ing6, 10, true, 0 }
            };
    }
	
	@SuppressWarnings("unused")
	private Object[] parametersUseIngredient() {
		ArrayList<Ingredient> ingredients1 = new ArrayList<>();
		Ingredient ing1 = new Ingredient("Test1", 10);
		ingredients1.add(ing1);
		
		ArrayList<Ingredient> ingredients2 = new ArrayList<>();
		Ingredient ing2 = new Ingredient("Test2", 10);
		ingredients2.add(ing2);
		
		ArrayList<Ingredient> ingredients3 = new ArrayList<>();
		Ingredient ing3 = new Ingredient("Test3", 8);
		ingredients3.add(ing3);
		
		ArrayList<Ingredient> ingredients4 = new ArrayList<>();
		Ingredient ing4 = new Ingredient("Test4", 8);
		ingredients4.add(ing4);
		
		ArrayList<Ingredient> ingredients5 = new ArrayList<>();
		Ingredient ing5 = new Ingredient("Test5", 10);
		ingredients5.add(new Ingredient("a", 10));
		ingredients5.add(new Ingredient("b", 10));
		ingredients5.add(new Ingredient("c", 10));
		ingredients5.add(ing5);
		
		ArrayList<Ingredient> ingredients6 = new ArrayList<>();
		Ingredient ing6 = new Ingredient("Test6", 10);
		ingredients6.add(new Ingredient("a", 10));
		ingredients6.add(ing6);
		ingredients6.add(new Ingredient("b", 10));
		ingredients6.add(new Ingredient("c", 10));
		
		
		return new Object[] { 
            new Object[] { ingredients1, new Ingredient(ing1.getName(), 5), true, 5 },
            new Object[] { ingredients2, new Ingredient(ing2.getName(), 8), true, 2 },
            new Object[] { ingredients3, new Ingredient(ing3.getName(), 15), false, 8 },
            new Object[] { ingredients4, new Ingredient(ing4.getName(), -1), false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2)}), new Ingredient(null, 0), false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2)}), null, false, 8 },
            new Object[] { Arrays.asList(new Ingredient[] {new Ingredient("test", 2)}), new Ingredient("a", 5), false, 5 },
            new Object[] { Arrays.asList(new Ingredient[] {null}), new Ingredient("test", 2), false, 5 },
            new Object[] { ingredients5, new Ingredient(ing5.getName(), 4), true, 6 },
            new Object[] { ingredients6, new Ingredient(ing6.getName(), 10), true, 0 }
            };
    }
	
	@SuppressWarnings("unused")
	private Object[] parametersUseAllIngredient() {
		ArrayList<Ingredient> ingredients1 = new ArrayList<>();
		Ingredient ing1 = new Ingredient("Test", 10);
		ingredients1.add(ing1);
		
		ArrayList<Ingredient> nullIngredients = new ArrayList<>();
		nullIngredients.add(null);
		
		ArrayList<Ingredient> ingredients5 = new ArrayList<>();
		Ingredient ing5 = new Ingredient("Test", 10);
		ingredients5.add(new Ingredient("a", 10));
		ingredients5.add(new Ingredient("b", 10));
		ingredients5.add(new Ingredient("c", 10));
		ingredients5.add(ing5);
		
		
        return new Object[] { 
            new Object[] { ingredients1, ing1, true },
            new Object[] { new ArrayList<Ingredient>(), null, false },
            new Object[] { ingredients5, ing5, true },
            new Object[] { new ArrayList<Ingredient>(), new Ingredient("test", 5), false }
            };
    }
	
	@SuppressWarnings("unused")
	private Object[] parametersGetIngredient() {
		ArrayList<Ingredient> ingredients1 = new ArrayList<>();
		Ingredient ing1 = new Ingredient("Test", 10);
		ingredients1.add(ing1);
		
		ArrayList<Ingredient> nullIngredients = new ArrayList<>();
		nullIngredients.add(null);
		
		ArrayList<Ingredient> ingredients5 = new ArrayList<>();
		Ingredient ing5 = new Ingredient("Test", 10);
		ingredients5.add(new Ingredient("a", 10));
		ingredients5.add(new Ingredient("b", 10));
		ingredients5.add(new Ingredient("c", 10));
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
