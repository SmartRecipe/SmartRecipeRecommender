/**
 * 
 */
package Beans;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * @author ryan.potocnik
 *
 */
@RunWith(JUnitParamsRunner.class)
public class VirtualRefigeratorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#checkRecipe(Beans.Recipe)}.
	 */
	@Test
	public void testCheckRecipe() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#checkAllRecipes()}.
	 */
	@Test
	public void testCheckAllRecipes() {
		fail("Not yet implemented");
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
		underTest.addIngredient(testIngredient.getName(), testIngredient.getQuantity());
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
		
		underTest.addIngredient(testList.get(0).getName(), 100);
		assertEquals(2, underTest.getIngredientsList().size());
		assertEquals(101, testList.get(0).getQuantity(), 0);
	}

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#useIngredient(java.lang.String, double)}.
	 */
	@Test
	public void testUseIngredient() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#getIngredient(java.lang.String)}.
	 */
	@Test
	public void testGetIngredient() {
		fail("Not yet implemented");
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

	/**
	 * Test method for {@link Beans.VirtualRefrigerator#setRecipes(java.util.ArrayList)}.
	 */
	@Test
	public void testSetRecipes() {
		fail("Not yet implemented");
	}

}
