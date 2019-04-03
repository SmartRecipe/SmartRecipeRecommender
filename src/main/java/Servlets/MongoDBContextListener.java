package Servlets;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;

import Databases.MongoConnection;

@WebListener
public class MongoDBContextListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent sce) {
        MongoConnection conn = MongoConnection.getInstance();
        conn.init();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        MongoConnection conn = MongoConnection.getInstance();
        conn.close();
    }
}