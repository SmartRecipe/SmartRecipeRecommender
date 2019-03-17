package Beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junitparams.*;

import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class IngredientTest {
	
	Ingredient underTest;

    @Before
    public void setUp() throws Exception {
    	underTest = new Ingredient("test", 0, null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Parameters(method = "parametersTestReplenish")
    public void testReplenish(double startQuantity,double useQuantity, double expectedValue, boolean expectedReturn) {
    	underTest.setQuantity(startQuantity);
    	boolean success = underTest.replenish(useQuantity);
        assertEquals("Start: "+startQuantity+", Used: "+useQuantity, expectedValue, underTest.getQuantity(), 0.01);
        assertEquals("Start: "+startQuantity+", Used: "+useQuantity, expectedReturn, success);
    }

    @Test
    @Parameters(method = "parametersTestUse")
    public void testUse(double startQuantity,double useQuantity, double expectedValue, boolean expectedReturn) {
    	underTest.setQuantity(startQuantity);
    	boolean success = underTest.use(useQuantity);
        assertEquals("Start: "+startQuantity+", Used: "+useQuantity, expectedValue, underTest.getQuantity(), 0.01);
        assertEquals("Start: "+startQuantity+", Used: "+useQuantity, expectedReturn, success);
    }
    
    @Test
    public void setName() {
    	fail("Not yet implemented");
    }

    @Test
    public void setQuantity() {
    	fail("Not yet implemented");
    }

    @Test
    public void setNutVal() {
    	fail("Not yet implemented");
    }
    
    @SuppressWarnings("unused")
	private Object[] parametersTestReplenish() {
        return new Object[] { 
            new Object[] { 50, -1, 50, false }, 
            new Object[] { 50, 10, 60, true }, 
            new Object[] { 10, 0, 10, true },
            new Object[] { Double.MAX_VALUE, 8, Double.MAX_VALUE, true } 
        };
    }
    
    @SuppressWarnings("unused")
	private Object[] parametersTestUse() {
        return new Object[] { 
            new Object[] { 50, -1, 50, false }, 
            new Object[] { 50, 10, 40, true }, 
            new Object[] { 10, 10, 0, true },
            new Object[] { 5, 10, 5, false },
            new Object[] { 10, -10, 10, false },
            new Object[] { Double.MIN_VALUE, 8, Double.MIN_VALUE, false }, 
            new Object[] { Double.MIN_VALUE, -8, Double.MIN_VALUE, false } 
        };
    }
}