/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author soup
 */
public class Cookbook implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Recipe> history; //List of all the recipes the user has made before.
    private List<Recipe> favorites; //List of all the recipes the user has marked as a favorite.
    
    public Cookbook() {
        history = new ArrayList<>();
        favorites = new ArrayList<>();
    }
    
    public Cookbook(List<Recipe> history, List<Recipe> favorites) {
        this.history = history;
        this.favorites = favorites;
    }
    
    /**
     * Adds a given recipe to the user's history of previously made recipes.
     * @param argRecipe The recipe being added.
     * @return Boolean describing the success or failure of the operation.
     */
    public boolean addToHistory(Recipe argRecipe) {
        if (argRecipe == null)
            return false;
        return history.add(argRecipe); //Add the new recipe to the user's history.
    }
    
    /**
     * Adds a given recipe to the user's list of favorite recipes.
     * @param argRecipe The recipe being added.
     * @return Boolean describing success or failure of the operation.
     */
    public boolean addToFavorites(Recipe argRecipe) {
        if (argRecipe == null || argRecipe.getName() == null)
            return false;
        //Make sure the recipe doesn't already exist within the user's favorites.
        for (Recipe recipe : favorites) {
            if (recipe.getName().equalsIgnoreCase(argRecipe.getName()))
                return false; //Recipe already favorited.
        }
        
        favorites.add(argRecipe); //Add the new recipe to the user's favorites.
        return true; //Success!
    }
    
    /**
     * Removes a given recipe from the user's list of favorite recipes.
     * @param argRecipe The recipe being removed.
     * @return Boolean describing success or failure of the operation.
     */
    public boolean removeFromFavorites(Recipe argRecipe) {
        if (argRecipe == null)
            return false;
        //Search the user's favorites for the given recipe and remove it.
        for (Recipe recipe : favorites) {
            if (recipe.getName().equalsIgnoreCase(argRecipe.getName())) {
                favorites.remove(recipe); //Remove the recipe.
                return true; //Success!
            }
        }
        
        return false; //Recipe not found.
    }

    public List<Recipe> getHistory() {
        return history;
    }

    public void setHistory(List<Recipe> history) {
        if (history == null) return;
        this.history = history;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        if (favorites == null) return;
        this.favorites = favorites;
    }
}
