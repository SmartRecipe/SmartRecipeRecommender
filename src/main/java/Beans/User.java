/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author soup
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String username;
    private String email;
    private int userID;
    private VirtualRefrigerator fridge;
    private Cookbook cookbook;
    
    public User() { }
    
    public User(String name, String username, String email, int userID) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.userID = userID;
        fridge = VirtualRefrigerator.getInstance();
        cookbook = Cookbook.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public VirtualRefrigerator getFridge() {
        return fridge;
    }
    
    public void setFridge(VirtualRefrigerator fridge) {
        this.fridge = fridge;
    }
    
    public Cookbook getCookbook() {
        return cookbook;
    }
    
    public void setCookbook(Cookbook cookbook) {
        this.cookbook = cookbook;
    }
}