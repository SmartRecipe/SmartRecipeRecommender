/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import Beans.User;

/**
 *
 * @author Soup
 */
public class PasswordHash {
    
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException{
        String hashAndSalt = password+salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(hashAndSalt.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray){
            int v = b & 0xff;
            if (v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    
    private static String getSalt(){
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
    public static String[] hashAndSaltPassword(String password) throws NoSuchAlgorithmException{
        String salt = getSalt();
        if (password == null) password = "";
        return new String[]{hashPassword(password, salt), salt};
    }
    
    /**
     * Update the user object directly
     * @param user User to hash the password and update
     */
    public static void hashAndSaltPassword(User user){
        try {
            String[] hashInfo = hashAndSaltPassword(user.getPassword());
            user.setPassword(hashInfo[0]);
            user.setSalt(hashInfo[1]);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password");
            e.printStackTrace();
        }
    }
}