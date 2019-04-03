/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databases;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import Beans.Recipe;
import java.util.List;

/**
 *
 * @author soup
 */
public class RecipeDatabase {
    	
	private static RecipeDatabase instance = null;
    private MongoConnection conn = null;
		
	private RecipeDatabase() {
	    conn  = MongoConnection.getInstance();
	}
	
	public static RecipeDatabase getInstance() {
		if (instance == null) {
			instance = new RecipeDatabase();
		}
		return instance;
	}
	
    public void addRecipe(Recipe recipe) {
        Gson gson = new Gson();
        String recipeJSON = gson.toJson(recipe);
        
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
        	MongoCollection<Document> recipes = database.getCollection("recipes");
            recipes.insertOne(Document.parse(recipeJSON));
        }
    }
    
    public List<Recipe> getAllRecipes() {
        Gson gson = new Gson();
        List<Recipe> recipes = new ArrayList<>();
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
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
