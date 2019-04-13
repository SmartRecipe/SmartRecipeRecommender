package Beans;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junitparams.*;

import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class CookbookTest {
	
	Cookbook underTest;

    @Before
    public void setUp() throws Exception {
    	underTest = new Cookbook();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Parameters(method = "parametersAddToFavorites")
    public void testAddToFavorites(List<Recipe> favorites, Recipe newFavorite, boolean expectedReturn) {
    	underTest.setFavorites(favorites);
    	boolean success = underTest.addToFavorites(newFavorite);
        assertEquals(expectedReturn, success);
    }
    
    @Test
    @Parameters(method = "parametersRemoveFromFavorites")
    public void testRemoveFromFavorites(List<Recipe> favorites, Recipe newFavorite, boolean expectedReturn) {
        int origLength = favorites.size();
        underTest.setFavorites(favorites);
        boolean success = underTest.removeFromFavorites(newFavorite);
        assertEquals(expectedReturn, success);
        if (expectedReturn) {
            assertEquals(origLength-1, underTest.getFavorites().size());
        } else {
            assertEquals(origLength, underTest.getFavorites().size());
        }
    }

    @Test
    @Parameters(method = "parametersAddToHistory")
    public void testAddToHistory(List<Recipe> history, Recipe newRecipe, boolean expectedReturn) {
        underTest.setHistory(history);
        boolean success = underTest.addToHistory(newRecipe);
        assertEquals(expectedReturn, success);
    }
    
    @Test
    public void testSetHistory() {
        ArrayList<Recipe> history = new ArrayList<>();
        history.add(new Recipe());
        history.add(new Recipe());
        history.get(0).setName("test 1");
        history.get(1).setName("test 2");
        underTest.setHistory(history);
        List<Recipe> retHistory = underTest.getHistory();
        assertNotNull(retHistory);
        assertEquals(history.size(), retHistory.size());
        for (int i=0; i<history.size(); i++) {
            assertEquals(history.get(i), retHistory.get(i));
        }
        
        underTest.setHistory(null);
        assertNotNull(underTest.getHistory());
    }
    
    @Test
    public void testSetFavorites() {
        ArrayList<Recipe> favorites = new ArrayList<>();
        favorites.add(new Recipe());
        favorites.add(new Recipe());
        favorites.get(0).setName("test 1");
        favorites.get(1).setName("test 2");
        underTest.setFavorites(favorites);
        List<Recipe> retFavorites = underTest.getFavorites();
        assertNotNull(retFavorites);
        assertEquals(favorites.size(), retFavorites.size());
        for (int i=0; i<favorites.size(); i++) {
            assertEquals(favorites.get(i), retFavorites.get(i));
        }
        
        underTest.setFavorites(null);
        assertNotNull(underTest.getFavorites());
    }
    
    
    @SuppressWarnings("unused")
	private Object[] parametersAddToFavorites() {
        return new Object[] { 
            new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(16), true },
            new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(10), false },
            new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), null, false }
        };
    }
    
    @SuppressWarnings("unused")
	private Object[] parametersAddToHistory() {
        return new Object[] {
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(16), true },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(10), true },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), null, false }
            };
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersRemoveFromFavorites() {
        return new Object[] { 
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(16), false },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(10), true },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), null, false }
        };
    }
    
    @SuppressWarnings("unused")
    private Object[] parametersRemoveFromHistory() {
        return new Object[] { 
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(16), false },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), VirtualRefigeratorTest.createRecipe(10), true },
                new Object[] { VirtualRefigeratorTest.createRecipeList(5, 15), null, false }
            };
    }
}