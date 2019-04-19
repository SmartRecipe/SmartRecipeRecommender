/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import Databases.RecipeDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author soup
 */
public class VirtualRefrigerator implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Ingredient> ingredients; //List of all ingredients in user's fridge
    
    public VirtualRefrigerator() {
        ingredients = new ArrayList<>();
    }
    
    public VirtualRefrigerator(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    /**
     * Recommends a recipe based on the user's recipe history. Essentially scans the user's history
     * to see which flavor tag appears the most often, then compiles a list of all recipes with the
     * corresponding tag and picks one at random.
     * @param cookbook The user's cookbook; used to retrieve the user's recipe history.
     * @return The recommended recipe.
     */
    public Recipe recommendRecipe(Cookbook cookbook) {
        List<Recipe> validRecipes = checkAllRecipes(false);
        
        String filter;
        
        HashMap<String, Integer> filterFreq = new HashMap<>();
        List<String> allTags = new ArrayList<>();
        
        for (Recipe recipe : cookbook.getHistory()) {
            allTags.addAll(recipe.getFlavorTags());
        }
        
        for (String tag : allTags) {
            if (filterFreq.get(tag.toLowerCase()) == null) {
                filterFreq.put(tag, 1);
            } else {
                // Increment the frequency count
                filterFreq.put(tag, filterFreq.get(tag.toLowerCase()) + 1);
            }
        }
        
        List<Recipe> candidates = new ArrayList<>();
        if (filterFreq.isEmpty()) {
            // Pick from all recipes if no filters were found
            candidates.addAll(validRecipes);
        } else {
            // Find the most popular tag and match recipes containing that flavor
            filter = Collections.max(filterFreq.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            
            for (Recipe recipe : validRecipes) {
                if (recipe.getFlavorTags().contains(filter))
                    candidates.add(recipe);
            }
        }
        
        Random rand = new Random(System.currentTimeMillis());
        
        if (candidates.isEmpty()) 
            return validRecipes.isEmpty() ? null : validRecipes.get(rand.nextInt(validRecipes.size()));
        else
            return candidates.get(rand.nextInt(candidates.size()));
    }
    
    /**
     * Checks a given recipe to see if the user has all necessary ingredients to make it.
     * @param recipe The recipe being checked.
     * @param oneMore A flag to determine if the recipes should be checked with a leniency of one missing
     * ingredient.
     * @return True if the user can make the recipe, false if not.
     */
    public boolean checkRecipe(Recipe recipe, boolean oneMore) {
        if (recipe == null || ingredients.isEmpty())
            return false;
        
        //I've looked at this like a million different ways and this is the solution I came up with.
        //It's late and I'm tired. Don't judge me.
        
        boolean haveIngredient;
        boolean missingOne = false;
        
        for (Ingredient needed : recipe.getIngredients()) {
            haveIngredient = false;
            
            for (Ingredient owned : ingredients) {
                if (owned != null && owned.getName().equalsIgnoreCase(needed.getName()) && owned.hasEnough(needed)) {
                    haveIngredient = true;
                    break;
                }
            }
            
            if (!haveIngredient && !oneMore)
                return false;
            else if (!haveIngredient && oneMore) {
                if (missingOne)
                    return false;
                else
                    missingOne = true;
            }
        }
        
        return true;
    }
    
    /**
     * Checks a given recipe to see if the user has all necessary ingredients to make it. Overloaded
     * method with Ingredient argument for prioritizing ingredients.
     * @param priority The ingredient being prioritized.
     * @param recipe The recipe being checked.
     * @param oneMore A flag to determine if the recipes should be checked with a leniency of one missing
     * ingredient.
     * @return True if the user can make the recipe, false if not.
     */
    public boolean checkRecipe(Recipe recipe, Ingredient priority, boolean oneMore) {
        if (recipe == null || ingredients.isEmpty())
            return false;
        
        boolean foundPriority = false;
        
        for (Ingredient ing : recipe.getIngredients()) {
            if (ing.getName() == priority.getName()) {
                foundPriority = true;
                break;
            }
        }
        
        if (!foundPriority)
            return false;
        
        //I've looked at this like a million different ways and this is the solution I came up with.
        //It's late and I'm tired. Don't judge me.
        
        boolean haveIngredient;
        boolean missingOne = false;
        
        for (Ingredient needed : recipe.getIngredients()) {
            haveIngredient = false;
            
            for (Ingredient owned : ingredients) {
                if (owned != null && owned.getName().equalsIgnoreCase(needed.getName()) && owned.hasEnough(needed)) {
                    haveIngredient = true;
                    break;
                }
            }
            
            if (!haveIngredient && !oneMore)
                return false;
            else if (!haveIngredient && oneMore) {
                if (missingOne)
                    return false;
                else
                    missingOne = true;
            }
        }
        
        return true;
    }
    
    /**
     * Checks all recipes in database to determine which ones the user can make and returns a list of
     * the valid recipes.
     * @param oneMore A flag to determine if the recipes should be checked with a leniency of one missing
     * ingredient.
     * @param filters An array of the filters used to narrow down the returned recipes to a specific
     * flavor profile.
     * @return An ArrayList of the recipes the user can make with the ingredients in their fridge.
     */
    public List<Recipe> checkAllRecipes(boolean oneMore, String... filters) {
        ArrayList<Recipe> validRecipes = new ArrayList<>();
        List<Recipe> allRecipes = RecipeDatabase.getInstance().getAllRecipes();
        
        //Don't @ me, I'm already ashamed of this.
        
        //First, check to make sure that filters isn't null/empty
        if (filters != null && filters.length > 0 && !filters[0].equalsIgnoreCase("")) {
            Iterator<Recipe> itr = allRecipes.iterator(); //Create an iterator for the master list of recipes so we can run through it without a ConcurrentModification exception
            int tags; //We'll need an int variable to keep track of the number of tags the recipe has in common with the filter array
            
            //Iterate through the master list
            while (itr.hasNext()) {
                Recipe recipe = (Recipe) itr.next(); //Throw the current list item into a Recipe object so we can get access to its flavor tags
                List<String> flavorTags = recipe.getFlavorTags(); //Get the recipe's tags and store them in a list
                tags = 0; //Set tags to 0
                
                //Compare each flavor tag in the recipe with the provided filters
                for (String flavorTag : flavorTags) {
                    for (int i = 0; i < filters.length; i++) {
                        //If the flavor tag matches the filter tag, increment tags
                        if (flavorTag.equalsIgnoreCase(filters[i])) {
                            tags++;
                            break;
                        }
                    }
                }
                
                //If, after comparing each flavor tag to each filter tag, the tags variable is still less than the length of the filters array, then the recipe
                //doesn't meet the filter specifications and should be removed
                if (tags < filters.length)
                    itr.remove();
            }
        }
        
        for (Recipe recipe : allRecipes) {
            if (checkRecipe(recipe, oneMore))
                validRecipes.add(recipe);
        }
        
        return validRecipes;
    }
    
    /**
     * Checks all recipes in database to determine which ones the user can make and returns a list of
     * the valid recipes. Overloaded method with ingredient argument for prioritizing ingredients.
     * @param priority The ingredient to be prioritized.
     * @param oneMore A flag to determine if the recipes should be checked with a leniency of one missing
     * ingredient.
     * @param filters An array of the filters used to narrow down the returned recipes to a specific
     * flavor profile.
     * @return An ArrayList of the recipes the user can make with the ingredients in their fridge.
     */
    public List<Recipe> checkAllRecipes(Ingredient priority, boolean oneMore, String... filters) {
        ArrayList<Recipe> validRecipes = new ArrayList<>();
        List<Recipe> allRecipes = RecipeDatabase.getInstance().getAllRecipes();
        
        //Don't @ me, I'm already ashamed of this.
        
        //First, check to make sure that filters isn't null/empty
        if (filters != null && filters.length > 0) {
            Iterator<Recipe> itr = allRecipes.iterator(); //Create an iterator for the master list of recipes so we can run through it without a ConcurrentModification exception
            int tags; //We'll need an int variable to keep track of the number of tags the recipe has in common with the filter array
            
            //Iterate through the master list
            while (itr.hasNext()) {
                Recipe recipe = (Recipe) itr.next(); //Throw the current list item into a Recipe object so we can get access to its flavor tags
                List<String> flavorTags = recipe.getFlavorTags(); //Get the recipe's tags and store them in a list
                tags = 0; //Set tags to 0
                
                //Compare each flavor tag in the recipe with the provided filters
                for (String flavorTag : flavorTags) {
                    for (int i = 0; i < filters.length; i++) {
                        //If the flavor tag matches the filter tag, increment tags
                        if (flavorTag.equalsIgnoreCase(filters[i])) {
                            tags++;
                            break;
                        }
                    }
                }
                
                //If, after comparing each flavor tag to each filter tag, the tags variable is still less than the length of the filters array, then the recipe
                //doesn't meet the filter specifications and should be removed
                if (tags < filters.length)
                    itr.remove();
            }
        }
        
        for (Recipe recipe : allRecipes) {
            if (checkRecipe(recipe, priority, oneMore))
                validRecipes.add(recipe);
        }
        
        return validRecipes;
    }
    
    /**
     * Adds an ingredient to the refrigerator/replenishes existing ingredient.
     * @param ingredient The ingredient being added to the fridge.
     * @return Boolean stating the success of the operation.
     */
    public boolean addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return false;
        }
        // Call method and break out quantity from ingredient
        return addIngredient(ingredient, ingredient.getQuantity());
    }
    
    /**
     * Adds an ingredient to the refrigerator/replenishes existing ingredient. Overloaded version that
     * allows for the desired quantity to be added/replenished to be included as an argument.
     * @param ingredient The ingredient being added to the fridge.
     * @param quantity The amount of the ingredient being added to the fridge.
     * @return Boolean stating the success of the operation.
     */
    public boolean addIngredient(Ingredient ingredient, double quantity) {
        if (ingredient == null) {
            return false;
        }
        //If there's an instance of the ingredient already in the fridge, then just replenish it with the given quantity.
        for (Ingredient ing : ingredients) {
            if (ing.getName().equalsIgnoreCase(ingredient.getName())) {
                return ing.replenish(quantity);
            }
        }
        
        //If the ingredient isn't found in the fridge, add it manually.
        ingredient.setQuantity(quantity);
        ingredients.add(ingredient);
        return true;
    }
    
    /**
     * Removes a given amount of an ingredient from the refrigerator.
     * @param ingredient The ingredient being removed.
     * @return Boolean stating the success of the operation.
     */
    public boolean useIngredient(Ingredient ingredient) {
        if (ingredient == null || ingredient.getName() == null)
            return false;
        
        //Search the fridge for the given ingredient and remove the given quantity.
        for (Ingredient ing : ingredients) {
            if (ing != null && ing.getName().equalsIgnoreCase(ingredient.getName())) {
                boolean success = ing.use(ingredient.getQuantity());
                
                //If the ingredient has been completely used up, remove it from the fridge.
                if (ing.getQuantity() <= 0)
                    ingredients.remove(ing);
                
                return success;
            }
        }
        
        return false; //Ingredient not found
    }
    
    /**
     * Removes a given amount of an ingredient from the refrigerator. Overloaded version that allows
     * for the desired quantity to be removed to be included as an argument.
     * @param ingredient The ingredient being removed from the fridge.
     * @param quantity The amount of the ingredient being removed from the fridge.
     * @return Boolean stating the success of the operation.
     */
    public boolean useIngredient(Ingredient ingredient, double quantity) {
        if (ingredient == null || ingredients.isEmpty())
            return false;
        
        //Search the fridge for the given ingredient and remove the given quantity.
        for (Ingredient ing : ingredients) {
            if (ing != null && ing.getName().equalsIgnoreCase(ingredient.getName())) {
                boolean success = ing.use(quantity);
                
                //If the ingredient has been completely used up, remove it from the fridge.
                if (ing.getQuantity() <= 0)
                    ingredients.remove(ing);
                
                return success;
            }
        }
        
        return false; //Ingredient not found
    }
    
    /**
     * Removes a given ingredient entirely from the refrigerator.
     * @param ingredient The ingredient being removed from the fridge.
     * @return Boolean stating the success of the operation.
     */
    public boolean useAll(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }
    
    /**
     * Retrieves the given ingredient from the refrigerator.
     * @param name The name of the ingredient being retrieved.
     * @return An Ingredient object representing the ingredient retrieved (a null value means the ingredient
     * wasn't found).
     */
    public Ingredient getIngredient(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient != null && ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient; //Success!
            }
        }
        
        return null; //Ingredient not found
    }
    
    public List<Ingredient> getIngredientsList() {
        return ingredients;
    }
    
    public void setIngredientsList(List<Ingredient> ingredients) {
        if (ingredients == null) return;
        this.ingredients = ingredients;
    }
}
