/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import Databases.RecipeDatabase;
import java.util.ArrayList;

/**
 *
 * @author soup
 */
public class VirtualRefrigerator {
    private ArrayList<Ingredient> ingredients; //List of all ingredients in user's fridge
    private ArrayList<Recipe> cookbook; //List of user's favorite recipes
    private static VirtualRefrigerator instance;
    
    private VirtualRefrigerator() {
        ingredients = new ArrayList<>();
        cookbook = new ArrayList<>();
    }
    
    public static VirtualRefrigerator getInstance() {
        if (instance == null)
            instance = new VirtualRefrigerator();
        
        return instance;
    }
    
    /**
     * Checks a given recipe to see if the user has all necessary ingredients to make it.
     * @param recipe The recipe being checked.
     * @return True if the user can make, false if not.
     */
    public boolean checkRecipe(Recipe recipe) {
        for (Ingredient needed : recipe.getIngredients()) {
            for (Ingredient owned : ingredients) {
                if (needed.getName().equals(owned.getName())) {
                    if (owned.getQuantity() < needed.getQuantity())
                        return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Checks all recipes in database to determine which ones the user can make and returns a list of
     * the valid recipes.
     * @return An ArrayList of the recipes the user can make with the ingredients in their fridge.
     */
    public ArrayList<Recipe> checkAllRecipes() {
        ArrayList<Recipe> validRecipes = new ArrayList<>();
        
        for (Recipe recipe : RecipeDatabase.getAllRecipes()) {
            if (checkRecipe(recipe))
                validRecipes.add(recipe);
        }
        
        return validRecipes;
    }
    
    /**
     * Adds an ingredient to the refrigerator/replenishes existing ingredient.
     * @param name The name of the ingredient being added.
     * @param quantity How much of the ingredient is being added.
     * @return Boolean stating the success of the operation.
     */
    public boolean addIngredient(String name, float quantity) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                ingredient.replenish(quantity);
                return true; //Success!
            }
        }
        
        return false; //Ingredient not found
    }
    
    /**
     * Removes a given amount of an ingredient from the refrigerator. If quantity is set to -1, simply
     * removes all of the ingredient owned.
     * @param name The name of the ingredient being removed.
     * @param quantity How much of the ingredient is being removed.
     * @return Boolean stating the success of the operation.
     */
    public boolean removeIngredient(String name, float quantity) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                if (ingredient.use(quantity))
                    return true; //Success!
                else
                    return false; //Don't have enough
            }
        }
        
        return false; //Ingredient not found
    }
    
    /**
     * Retrieves the given ingredient from the refrigerator.
     * @param name The name of the ingredient being retrieved.
     * @return An Ingredient object representing the ingredient retrieved (a null value means the ingredient
     * wasn't found).
     */
    public Ingredient getIngredient(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient; //Success!
            }
        }
        
        return null; //Ingredient not found
    }
    
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    public ArrayList<Recipe> getCookbook() {
        return cookbook;
    }
    
    public void setRecipes(ArrayList<Recipe> cookbook) {
        this.cookbook = cookbook;
    }
}
