/**
 * 
 */
package Beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ryanpotocnik
 *
 */
public class NutritionInfoTest {
    
    NutritionInfo underTest;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        underTest = new NutritionInfo();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getServingsPerContainer()} and {@link Beans.NutritionInfo#setServingsPerContainer(double)}.
     */
    @Test
    public void testServingsPerContainer() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setServingsPerContainer(val);
        assertEquals(val, underTest.getServingsPerContainer(), 0);
        
        val = 0;
        underTest.setServingsPerContainer(val);
        assertEquals(val, underTest.getServingsPerContainer(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getServingSize()} {@link Beans.NutritionInfo#setServingSize(double)}.
     */
    @Test
    public void testServingSize() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setServingSize(val);
        assertEquals(val, underTest.getServingSize(), 0);
        
        val = 0;
        underTest.setServingSize(val);
        assertEquals(val, underTest.getServingSize(), 0);
    }


    /**
     * Test method for {@link Beans.NutritionInfo#getCalories()} and {@link Beans.NutritionInfo#setCalories(double)}.
     */
    @Test
    public void testCalories() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setCalories(val);
        assertEquals(val, underTest.getCalories(), 0);
        
        val = 0;
        underTest.setCalories(val);
        assertEquals(val, underTest.getCalories(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getTotalFat()} and {@link Beans.NutritionInfo#setTotalFat(double)}.
     */
    @Test
    public void testTotalFat() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setTotalFat(val);
        assertEquals(val, underTest.getTotalFat(), 0);
        
        val = 0;
        underTest.setTotalFat(val);
        assertEquals(val, underTest.getTotalFat(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getSaturatedFat()} and {@link Beans.NutritionInfo#setSaturatedFat(double)}.
     */
    @Test
    public void testSaturatedFat() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setSaturatedFat(val);
        assertEquals(val, underTest.getSaturatedFat(), 0);
        
        val = 0;
        underTest.setSaturatedFat(val);
        assertEquals(val, underTest.getSaturatedFat(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getTransFat()} and {@link Beans.NutritionInfo#setTransFat(double)}.
     */
    @Test
    public void testTransFat() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setTransFat(val);
        assertEquals(val, underTest.getTransFat(), 0);
        
        val = 0;
        underTest.setTransFat(val);
        assertEquals(val, underTest.getTransFat(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getCarbs()} and {@link Beans.NutritionInfo#setCarbs(double)}.
     */
    @Test
    public void testCarbs() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setCarbs(val);
        assertEquals(val, underTest.getCarbs(), 0);
        
        val = 0;
        underTest.setCarbs(val);
        assertEquals(val, underTest.getCarbs(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getFiber()} and {@link Beans.NutritionInfo#setFiber(double)}.
     */
    @Test
    public void testFiber() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setFiber(val);
        assertEquals(val, underTest.getFiber(), 0);
        
        val = 0;
        underTest.setFiber(val);
        assertEquals(val, underTest.getFiber(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getSugar()} and {@link Beans.NutritionInfo#setSugar(double)}.
     */
    @Test
    public void testSugar() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setSugar(val);
        assertEquals(val, underTest.getSugar(), 0);
        
        val = 0;
        underTest.setSugar(val);
        assertEquals(val, underTest.getSugar(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getAddedSugar()}.
     */
    @Test
    public void testAddedSugar() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setAddedSugar(val);
        assertEquals(val, underTest.getAddedSugar(), 0);
        
        val = 0;
        underTest.setAddedSugar(val);
        assertEquals(val, underTest.getAddedSugar(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getProtein()}.
     */
    @Test
    public void testProtein() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setProtein(val);
        assertEquals(val, underTest.getProtein(), 0);
        
        val = 0;
        underTest.setProtein(val);
        assertEquals(val, underTest.getProtein(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getCholesterol()}.
     */
    @Test
    public void testCholesterol() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setCholesterol(val);
        assertEquals(val, underTest.getCholesterol(), 0);
        
        val = 0;
        underTest.setCholesterol(val);
        assertEquals(val, underTest.getCholesterol(), 0);
    }

    /**
     * Test method for {@link Beans.NutritionInfo#getSodium()}.
     */
    @Test
    public void testSodium() {
        double val = Math.random()*Double.MAX_VALUE;
        underTest.setSodium(val);
        assertEquals(val, underTest.getSodium(), 0);
        
        val = 0;
        underTest.setSodium(val);
        assertEquals(val, underTest.getSodium(), 0);
    }
    
    @Test
    public void testExtras() {
        assertTrue(underTest.getExtras() != null);
        
        underTest.putExtra("test", 15.6);
        assertEquals(15.6, underTest.getExtra("test", 3.0), 0);
        assertEquals(3.0, underTest.getExtra("Test", 3.0), 0);
        assertEquals(3.0, underTest.getExtra("not-test", 3.0), 0);
        assertEquals(5.0, underTest.getExtra(null, 5.0), 0);
        assertEquals(1, underTest.getExtras().size());
        
        underTest.putExtra(null, 100);
        assertEquals(1, underTest.getExtras().size());
    }
    
    @Test
    public void testConstructor() {
        NutritionInfo testConstructor = new NutritionInfo(1, 2, 3, 4, 5, 6, 7, 8, 9, 10
                , 11, 12, 13);
        
        assertEquals(10, testConstructor.getAddedSugar(), 0);
        assertTrue(underTest.getExtras() != null);
    }


}
