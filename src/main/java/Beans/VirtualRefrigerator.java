/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import Databases.RecipeDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * Checks a given recipe to see if the user has all necessary ingredients to make it.
     * @param recipe The recipe being checked.
     * @return True if the user can make the recipe, false if not.
     */
    public boolean checkRecipe(Recipe recipe) {
        if (recipe == null || ingredients.isEmpty())
            return false;
        
        //I've looked at this like a million different ways and this is the solution I came up with.
        //It's late and I'm tired. Don't judge me.
        
        boolean haveIngredient;
        
        for (Ingredient needed : recipe.getIngredients()) {                
            haveIngredient = false;
            
            for (Ingredient owned : ingredients) {
                if (owned != null && owned.getName().equalsIgnoreCase(needed.getName()) && owned.hasEnough(needed)) {
                    haveIngredient = true;
                    break;
                }
            }
            
            if (!haveIngredient)
                return false;
        }
        
        return true;
    }
    
    /**
     * Checks all recipes in database to determine which ones the user can make and returns a list of
     * the valid recipes.
     * @param filters An array of the filters 
     * @return An ArrayList of the recipes the user can make with the ingredients in their fridge.
     */
    public ArrayList<Recipe> checkAllRecipes(String... filters) {
        ArrayList<Recipe> validRecipes = new ArrayList<>();
        
        for (Recipe recipe : RecipeDatabase.getInstance().getAllRecipes()) {
            if (checkRecipe(recipe))
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
//If there's an instance of the ingredient already in the fridge, then just replenish it with the given quantity.
for (Ingredient ing : ingredients) {
    if (ing.getName().equalsIgnoreCase(ingredient.getName())) {
        return ing.replenish(ingredient.getQuantity());
    }
}

//If the ingredient isn't found in the fridge, add it manually.
ingredients.add(ingredient);
return true;
    }
    
    /**
     * Adds an ingredient to the refrigerator/replenishes existing ingredient. Overloaded version that
     * allows for the desired quantity to be added/replenished to be included as an argument.
     * @param ingredient The ingredient being added to the fridge.
     * @param quantity The amount of the ingredient being added to the fridge.
     * @return Boolean stating the success of the operation.
     */
    public boolean addIngredient(Ingredient ingredient, double quantity) {
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
        if (ingredient == null || ingredients.isEmpty())
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
