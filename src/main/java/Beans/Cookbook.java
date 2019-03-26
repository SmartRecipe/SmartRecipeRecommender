/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author soup
 */
public class Cookbook {
    private static Cookbook instance;
    private List<Recipe> history; //List of all the recipes the user has made before.
    private List<Recipe> favorites; //List of all the recipes the user has marked as a favorite.
    
    private Cookbook() {
        history = new ArrayList<>();
        favorites = new ArrayList<>();
    }
    
    public Cookbook getInstance() {
        if (instance == null)
            instance = new Cookbook();
        
        return instance;
    }

    public List<Recipe> getHistory() {
        return history;
    }

    public void setHistory(List<Recipe> history) {
        this.history = history;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }
}
