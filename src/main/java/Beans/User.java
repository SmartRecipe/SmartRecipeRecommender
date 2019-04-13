/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.UUID;
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
    private transient String password;
    private UUID userID;
    private String salt;
    private VirtualRefrigerator fridge;
    private Cookbook cookbook;
    
    public User() {
        this.name = "";
        this.username = "";
        this.email = "";
        this.password = "";
        this.userID = UUID.randomUUID();
        this.salt = "";
        fridge = new VirtualRefrigerator();
        cookbook = new Cookbook();
    }
    
    public User(String name, String username, String email, UUID userID, String salt) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.userID = userID;
        this.salt = salt;
        fridge = new VirtualRefrigerator();
        cookbook = new Cookbook();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) return;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) return;
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        if (password == null) return;
        this.password = password;
    }
    
    public UUID getUserID() {
        return userID;
    }
    
    public void setUserID(UUID userID) {
        if (userID == null) return;
        this.userID = userID;
    }
    
    public VirtualRefrigerator getFridge() {
        return fridge;
    }
    
    public void setFridge(VirtualRefrigerator fridge) {
        if (fridge == null) return;
        this.fridge = fridge;
    }
    
    public Cookbook getCookbook() {
        return cookbook;
    }
    
    public void setCookbook(Cookbook cookbook) {
        if (cookbook == null) return;
        this.cookbook = cookbook;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
}