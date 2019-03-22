/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databases;

import com.google.gson.Gson;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Beans.Recipe;
import Beans.VirtualRefrigerator;

/**
 *
 * @author soup
 */
public class RecipeDatabase {
    private static Gson gson = new Gson();
    private static Session ssh;
    
    public static void addRecipe(Recipe recipe) {
        String recipeJSON = gson.toJson(recipe);
        
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity("/home/soup/.ssh/id_rsa");
            
            ssh = null;
            ssh = jsch.getSession("ubuntu", "35.153.73.71", 22);
            ssh.setConfig(config);
            ssh.connect();
            ssh.setPortForwardingL(6666, "35.153.73.71", 27017);
            
            MongoClient mongo = new MongoClient("localhost", 6666);
            MongoDatabase database = mongo.getDatabase("smartrecipedb");
            MongoCollection<Document> recipes = database.getCollection("recipes");
            
            recipes.insertOne(Document.parse(recipeJSON));
        } catch (JSchException ex) {
            Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ssh.delPortForwardingL(6666);
            } catch (JSchException ex) {
                Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            ssh.disconnect();
        }
    }
    
    public static ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity("/home/soup/.ssh/id_rsa");
            
            ssh = null;
            ssh = jsch.getSession("ubuntu", "35.153.73.71", 22);
            ssh.setConfig(config);
            ssh.connect();
            ssh.setPortForwardingL(6666, "35.153.73.71", 27017);
            
            MongoClient mongo = new MongoClient("localhost", 6666);
            MongoDatabase database = mongo.getDatabase("smartrecipedb");
            MongoCollection<Document> recipeCol = database.getCollection("recipes");
            MongoCursor<Document> cursor;
            
            
            cursor = recipeCol.find().iterator();
            try {
                while (cursor.hasNext())
                    recipes.add(gson.fromJson(cursor.next().toJson(), Recipe.class));
            } finally {
                cursor.close();
            }
            
        } catch (JSchException ex) {
            Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ssh.delPortForwardingL(6666);
            } catch (JSchException ex) {
                Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            ssh.disconnect();
        }
        
        return recipes;
    }
    
    public static Recipe getRecipe(String name) {
        ArrayList<Recipe> recipes = getAllRecipes();
        
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        
        return null;
    }
}
