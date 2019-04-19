package Databases;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junitparams.*;

import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import Beans.User;
import Databases.UserDatabase;

@RunWith(JUnitParamsRunner.class)
public class UserDatabaseTest {
    
    UserDatabase underTest;
    
    
    static MongoClient testMongoClient;
    static MongoDatabase testDb;
    Gson gson = new Gson();
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testMongoClient = new MongoClient("127.0.0.1");
        MongoConnection fakeConn = mock(MongoConnection.class);
        testDb = spy(testMongoClient.getDatabase("testdb"));
        doReturn(testDb).when(fakeConn).getDatabase();
        try {
            Field instance = MongoConnection.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, fakeConn);
        } catch (Exception e) {
            System.out.println("setup error: "+e);
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        try {
            Field instance = MongoConnection.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
            System.out.println("Closing test");
            if (testMongoClient != null) testMongoClient.close();
            testMongoClient = null;
        } catch (Exception e) {
            System.out.println("teardown error: "+e);
        }
    }
    
    @Before
    public void setUp() throws Exception {
        System.out.println("Setting up test");
        // Clear out existing data
        System.out.println("Clearing database before test");
        for (String name : testMongoClient.getDatabase("testdb").listCollectionNames()) {
            System.out.println("Clearing collection: "+name);
            testMongoClient.getDatabase("testdb").getCollection(name).deleteMany(new BasicDBObject());
        }
        underTest = spy(UserDatabase.getInstance());
    }
    
    @After
    public void tearDown() throws Exception {
        Field instance = UserDatabase.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public final void testAddUserSameEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("testpw");
        boolean firstAdd = underTest.addUser(user);
        boolean secondAdd = underTest.addUser(user);
        assertTrue(firstAdd);
        assertFalse(secondAdd);
    }

    @Test
    public final void testUpdateUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("tester");
        user.setUsername(user.getName()+"Username");
        user.setPassword("testpw");
        assertTrue(underTest.addUser(user));
        
        User user2 = new User();
        user2.setEmail(user.getEmail());
        user2.setName("test user");
        user2.setUsername(user2.getName()+"Username");
        user2.setPassword("password");
        assertTrue(underTest.updateUser(user2));
        
        user2.setEmail("aadf");
        assertFalse(underTest.updateUser(user2));
        
        User user3 = underTest.getUser(user.getEmail());
        assertNotNull(user3);
        assertEquals(user2.getName(), user3.getName());
        assertEquals(user2.getUsername(), user3.getUsername());
        assertEquals(user2.getUserID(), user3.getUserID());
        assertNotEquals(user2.getPassword(), user3.getPassword()); // password should be changed
        assertNotEquals("", user3.getSalt());
    }

    //@Test
    public final void testGetAllUsers() {
        //fail("Not yet implemented");
    }

    //@Test
    public final void testGetUser() {
        //fail("Not yet implemented");
    }

    @Test
    public final void testLogin() {
        String password = "TestPassword123";
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("tester");
        user.setUsername(user.getName()+"Username");
        user.setPassword(password);
        assertTrue(underTest.addUser(user));
        
        User retUser = underTest.login(user.getEmail(), password);
        assertNotNull(retUser);
        assertEquals(user.getUserID(), retUser.getUserID());
    }

}
