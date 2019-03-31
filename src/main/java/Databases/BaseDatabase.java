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

    public static final int SSH_PORT = 22;
    public static final int SSH_FORWARD_PORT = 6666;

    public static final String ENV_SSH_KEY = System.getenv("SSH_KEY");
    
    public static final int ENV_DB_PORT = Integer.parseInt(System.getenv("DB_PORT"));
    public static final String ENV_DB_NAME = System.getenv("DB_NAME");
    public static final String ENV_DB_ADDRESS = System.getenv("DB_ADDRESS");
	
	protected boolean setupConnection(String dbName) {
		java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        try {
			jsch.addIdentity("ubuntu", BaseDatabase.ENV_SSH_KEY.getBytes(), null, null);
			ssh = jsch.getSession("ubuntu", BaseDatabase.ENV_DB_ADDRESS, BaseDatabase.SSH_PORT);
	        ssh.setConfig(config);
	        ssh.connect();
	        ssh.setPortForwardingL(BaseDatabase.SSH_FORWARD_PORT, BaseDatabase.ENV_DB_ADDRESS, BaseDatabase.ENV_DB_PORT);
		} catch (JSchException e) {
			Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
        
        mongo = new MongoClient("localhost", BaseDatabase.SSH_FORWARD_PORT);
        database = mongo.getDatabase(dbName);
        return true;
	}
	
	protected void closeConnection() {
		mongo.close();
		 try {
             ssh.delPortForwardingL(BaseDatabase.SSH_FORWARD_PORT);
         } catch (JSchException ex) {
             Logger.getLogger(VirtualRefrigerator.class.getName()).log(Level.SEVERE, null, ex);
         }
         ssh.disconnect();
	}

}
