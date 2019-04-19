package Servlets.utils;

import Beans.Ingredient;
import Beans.User;
import Beans.Recipe;
import java.io.Serializable;

public class BaseRequest implements Serializable {
    private User user;

    private Recipe recipe;
    
    private Ingredient ingredient;

    private String filters[];

    public void BaseRequest() {
        this.user = new User();
        this.recipe = new Recipe();
        this.ingredient = new Ingredient();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}   