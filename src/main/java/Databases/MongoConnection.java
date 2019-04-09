package Databases;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    
    private static final Logger logger = Logger.getLogger(MongoConnection.class.getName());
    
    private static MongoConnection instance = new MongoConnection();
    
    private MongoClient mongo = null;
    private Session ssh = null;
    
    public static final int SSH_PORT = 22;
    public static final int SSH_FORWARD_PORT = 6666;
    
    public static final String ENV_SSH_KEY = System.getenv("SSH_KEY");
    public static final int ENV_DB_PORT;
    static {
        int port;
        try {
            port = Integer.parseInt(System.getenv("DB_PORT"));
        } catch (NumberFormatException e) {
            port = 27017;
        }
        ENV_DB_PORT = port;
    }
    
    public static final String ENV_DB_NAME = System.getenv("DB_NAME") == null ? "smartrecipedb"
            : System.getenv("DB_NAME");
    public static final String ENV_DB_ADDRESS = System.getenv("DB_ADDRESS");
    
    public MongoClient getMongo() throws RuntimeException {
        if (mongo == null) {
            logger.log(Level.FINE, "Starting Mongo");
            
            mongo = new MongoClient("localhost", SSH_FORWARD_PORT);
        }
        
        return mongo;
    }
    
    public MongoDatabase getDatabase() {
        return getDatabase(ENV_DB_NAME);
    }
    
    public MongoDatabase getDatabase(String dbName) {
        if (mongo == null || dbName == null || dbName.isEmpty()) {
            logger.log(Level.SEVERE, "getDatabase called incorrectly");
            return null;
        }
        
        return mongo.getDatabase(dbName);
    }
    
    public Session getSsh() {
        if (ssh == null && ENV_SSH_KEY != null) {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            try {
                jsch.addIdentity("private key", ENV_SSH_KEY.getBytes(), null, null);
                
                ssh = null;
                ssh = jsch.getSession("ubuntu", ENV_DB_ADDRESS, SSH_PORT);
                ssh.setConfig(config);
                ssh.connect();
                ssh.setPortForwardingL(SSH_FORWARD_PORT, ENV_DB_ADDRESS, ENV_DB_PORT);
                logger.info("SSH Forwarding successfully set");
            } catch (JSchException e) {
                logger.log(Level.SEVERE, "Error occurred while setting up ssh forwarding", e);
            }
        }
        return ssh;
    }
    
    public void init() {
        getSsh();
        getMongo();
    }
    
    public void close() {
        logger.info("Closing MongoDB connection");
        if (mongo != null) {
            try {
                mongo.close();
                logger.log(Level.FINE, "Nulling the connection dependency objects");
                mongo = null;
            } catch (Exception e) {
                logger.log(Level.WARNING,
                        String.format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
            }
        } else {
            logger.log(Level.WARNING, "mongo object was null, wouldn't close connection");
        }
        if (ssh != null) {
            try {
                ssh.delPortForwardingL(SSH_FORWARD_PORT);
            } catch (JSchException ex) {
                logger.log(Level.SEVERE, "Error occured while deleting ssh port fowarding", ex);
            }
            ssh.disconnect();
            ssh = null;
        }
    }
    
    private MongoConnection() {
    }
    
    public static MongoConnection getInstance() {
        return instance;
    }
    
}
