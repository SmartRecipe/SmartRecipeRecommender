/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databases;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import Beans.Recipe;
import java.util.List;

/**
 *
 * @author soup
 */
public class RecipeDatabase extends BaseDatabase {
	
	private static RecipeDatabase instance = null;
	
	private static final String DB_NAME = "smartrecipedb";
	
	private RecipeDatabase() {
	}
	
	public static RecipeDatabase getInstance() {
		if (instance == null) {
			instance = new RecipeDatabase();
		}
		return instance;
	}
	
    public void addRecipe(Recipe recipe) {
        String recipeJSON = gson.toJson(recipe);
        
        if (setupConnection(DB_NAME)) {
        	MongoCollection<Document> recipes = database.getCollection("recipes");
            recipes.insertOne(Document.parse(recipeJSON));
        }
        closeConnection();
    }
    
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        if(setupConnection(DB_NAME)) {
        	MongoCollection<Document> recipeCol = database.getCollection("recipes");
            MongoCursor<Document> cursor;
            cursor = recipeCol.find().iterator();
            try {
                while (cursor.hasNext())
                    recipes.add(gson.fromJson(cursor.next().toJson(), Recipe.class));
            } finally {
                cursor.close();
            }
        }
        closeConnection();
        
        return recipes;
    }
    
    public Recipe getRecipe(String name) {
        List<Recipe> recipes = getAllRecipes();
        
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        
        return null;
    }
}
