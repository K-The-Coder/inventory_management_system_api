/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.inventory_management_system.dao;

import com.web.inventory_management_system.model.User;
import com.web.inventory_management_system.util.*;
import java.sql.*;

/**
 *
 * @author kekem
 */
public class UserDAO {
    //authenticate user
    public User authenticate(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            if (result.next()){
                
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setRole(result.getString("role"));
                    
                return user;
                
            }
            return null;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        
    }
    
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            
            pstmt.executeUpdate();
            
            return true;
        }
        
        catch (SQLIntegrityConstraintViolationException sqlEx){
            throw new RuntimeException(sqlEx);
        }
        
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
