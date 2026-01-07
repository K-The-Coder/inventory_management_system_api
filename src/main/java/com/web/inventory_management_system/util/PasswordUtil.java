/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.inventory_management_system.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author kekem
 */
public class PasswordUtil {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static boolean verifyPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
