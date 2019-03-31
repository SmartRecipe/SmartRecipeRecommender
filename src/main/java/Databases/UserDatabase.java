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
public class UserDatabase {
    private static Gson gson = new Gson();
    private static Session ssh;
    
    public static void addUser(User user) {
        String userJSON = gson.toJson(user);
        
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity("ubuntu", DBUtils.ENV_SSH_KEY.getBytes(), null, null);
            
            ssh = null;
            ssh = jsch.getSession("ubuntu", DBUtils.ENV_DB_ADDRESS, DBUtils.SSH_PORT);
            ssh.setConfig(config);
            ssh.connect();
            ssh.setPortForwardingL(6666, DBUtils.ENV_DB_ADDRESS, DBUtils.ENV_DB_PORT);
            
            MongoClient mongo = new MongoClient("localhost", 6666);
            MongoDatabase database = mongo.getDatabase(DBUtils.ENV_DB_NAME);
            MongoCollection<Document> users = database.getCollection("users");
            
            users.insertOne(Document.parse(userJSON));
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
    
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity("ubuntu", DBUtils.ENV_SSH_KEY.getBytes(), null, null);
            
            ssh = null;
            ssh = jsch.getSession("ubuntu", DBUtils.ENV_DB_ADDRESS, DBUtils.SSH_PORT);
            ssh.setConfig(config);
            ssh.connect();
            ssh.setPortForwardingL(6666, DBUtils.ENV_DB_ADDRESS, DBUtils.ENV_DB_PORT);
            
            MongoClient mongo = new MongoClient("localhost", 6666);
            MongoDatabase database = mongo.getDatabase( DBUtils.ENV_DB_NAME);
            MongoCollection<Document> userCol = database.getCollection("users");
            MongoCursor<Document> cursor;
            
            
            cursor = userCol.find().iterator();
            try {
                while (cursor.hasNext())
                    users.add(gson.fromJson(cursor.next().toJson(), User.class));
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
        
        return users;
    }
    
    public static User getUser(String email) {
        List<User> users = getAllUsers();
        
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }
        
        return null;
    }
    
    public static User login(String email, String password) {
        //Note: This is a temporary implementation of the login functionality, and so it's naturally insecure. It will be updated later with security measures including salts and hashes.
        
        User user = getUser(email);
        
        if (user.getPassword().equalsIgnoreCase(password))
            return user;
        else
            return null;
    }
}