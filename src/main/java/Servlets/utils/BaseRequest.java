package Servlets.utils;

import Beans.User;
import Beans.Recipe;
import java.util.ArrayList;
import java.io.Serializable;

public class BaseRequest implements Serializable {
    private User user;

    private Recipe recipe;

    private String filters[];

    public void BaseRequest() {
        this.user = new User();
        this.recipe = new Recipe();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getFilters() {
        return this.filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}   