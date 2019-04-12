/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databases;

import Beans.User;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

/**
 *
 * @author soup
 */
public class UserDatabase {
	
	private static UserDatabase instance = null;
	private MongoConnection conn = null;
    	
	private UserDatabase(){
	    conn = MongoConnection.getInstance();
	}
	
	public static UserDatabase getInstance() {
		if (instance == null) {
			instance = new UserDatabase();
		}
		return instance;
	}
	
	
    public boolean addUser(User user) {
        if (checkDuplicate(user.getEmail()))
            return false;
        
        Gson gson = new Gson();
        String userJSON = gson.toJson(user);
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
            MongoCollection<Document> users = database.getCollection("users");
            users.insertOne(Document.parse(userJSON));
        }
        
        return true;
    }
    
    public List<User> getAllUsers() {
        Gson gson = new Gson();
        List<User> users = new ArrayList<>();
        
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
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
        
        if (user != null && user.getPassword().equals(password))
            return user;
        else
            return null;
    }
    
    /**
     * Checks to see if a User account associated with the given email already exits in the database.
     * @param email The email account of the user in question
     * @return True if the email already exists in the database, false if not. 
     */
    private boolean checkDuplicate(String email) {
        return getUser(email) != null;
    }
}