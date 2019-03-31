package Databases;

import java.util.logging.Level;
import java.util.logging.Logger;

import Beans.VirtualRefrigerator;

import com.google.gson.Gson;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public abstract class BaseDatabase {
	
	protected static Gson gson = new Gson();
	protected static Session ssh;
	
	protected MongoClient mongo;
	protected MongoDatabase database;
	
	protected boolean setupConnection(String dbName) {
		java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        try {
			jsch.addIdentity("/home/soup/.ssh/id_rsa");
			ssh = jsch.getSession("ubuntu", "35.153.73.71", 22);
	        ssh.setConfig(config);
	        ssh.connect();
	        ssh.setPortForwardingL(6666, "35.153.73.71", 27017);
		} catch (JSchException e) {
			Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
        
        mongo = new MongoClient("localhost", 6666);
        database = mongo.getDatabase(dbName);
        return true;
	}
	
	protected void closeConnection() {
		mongo.close();
		 try {
             ssh.delPortForwardingL(6666);
         } catch (JSchException ex) {
             Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
         }
         ssh.disconnect();
	}

}
