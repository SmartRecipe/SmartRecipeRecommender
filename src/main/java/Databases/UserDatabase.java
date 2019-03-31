/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databases;

import Beans.User;
import Beans.VirtualRefrigerator;

import com.google.gson.Gson;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

/**
 *
 * @author soup
 */
public class UserDatabase extends BaseDatabase {
	
	private static UserDatabase instance = null;
    	
	private UserDatabase(){}
	
	public static UserDatabase getInstance() {
		if (instance == null) {
			instance = new UserDatabase();
		}
		return instance;
	}
	
	
    public void addUser(User user) {
        String userJSON = gson.toJson(user);
        
        if (setupConnection(BaseDatabase.ENV_DB_NAME)) {
            MongoCollection<Document> users = database.getCollection("users");
            users.insertOne(Document.parse(userJSON));
        }
        closeConnection();
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        
        if (setupConnection(BaseDatabase.ENV_DB_NAME)) {
            MongoCollection<Document> userCol = database.getCollection("users");
            MongoCursor<Document> cursor;
            cursor = userCol.find().iterator();
            try {
                while (cursor.hasNext())
                    users.add(gson.fromJson(cursor.next().toJson(), User.class));
            } finally {
                cursor.close();
            }
            
        } 
        closeConnection();
        
        return users;
    }
    
    public User getUser(String email) {
        List<User> users = getAllUsers();
        
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }
        
        return null;
    }
    
    public User login(String email, String password) {
        //Note: This is a temporary implementation of the login functionality, and so it's naturally insecure. It will be updated later with security measures including salts and hashes.
        
        User user = getUser(email);
        
        if (user.getPassword().equalsIgnoreCase(password))
            return user;
        else
            return null;
    }
}