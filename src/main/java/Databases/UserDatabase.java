/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Databases;

import Beans.User;
import Servlets.LoginServlet;
import Servlets.utils.PasswordHash;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.result.UpdateResult;
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
        if (user == null || checkDuplicate(user.getEmail()))
            return false;
        
        PasswordHash.hashAndSaltPassword(user);
        user.setUserID(UUID.randomUUID());
        
        Gson gson = new Gson();
        String userJSON = gson.toJson(user);
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
            MongoCollection<Document> users = database.getCollection("users");
            users.insertOne(Document.parse(userJSON));
        }
        
        return true;
    }
    
    /**
     * Updates a stored user object in the database such that the original document is entirely replaced
     * by the new one.
     * @param user The user object being updated
     * @return True if successful, false if otherwise
     */
    public boolean updateUser(User user) {
        if (user == null)
            return false;
        
        // PasswordHash.hashAndSaltPassword(user);
        
        Gson gson = new Gson();
        String userJSON = gson.toJson(user);
        MongoDatabase database = conn.getDatabase();
        if (database != null) {
            MongoCollection<Document> users = database.getCollection("users");
            UpdateResult ret = users.replaceOne(Document.parse("{ \"email\" : \"" + user.getEmail() + "\" }"), Document.parse(userJSON));
            return ret.getMatchedCount() > 0;
        }
        return false;
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
                while (cursor.hasNext()) {
                    users.add(gson.fromJson(cursor.next().toJson(), User.class));
                }
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
        User user = getUser(email);
        
        if (user == null) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.INFO, "Unable to find user: "+email);
            return null;
        }

        try {
            if (PasswordHash.hashPassword(password, user.getSalt()).equals(user.getPassword()))
                return user;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
