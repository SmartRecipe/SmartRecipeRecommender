/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

/**
 *
 * @author soup
 */
public class VirtualRefrigerator {
    //Should pull list of ingredients from database and place them into an ArrayList through which the front-end can access them.
    
    private ArrayList<Ingredient> ingredients; //Master list of all ingredients in the system; ingredient is not considered in user's refrigerator if quantity is 0.
    
    public VirtualRefrigerator(/* User object goes here */) {
        //Query database for user's refrigerator data and populate fields accordingly
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
     * @return Boolean stating the success of the operation
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
    
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
