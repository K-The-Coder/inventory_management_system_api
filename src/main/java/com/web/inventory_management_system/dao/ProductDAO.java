/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.inventory_management_system.dao;

import com.web.inventory_management_system.model.Product;
import com.web.inventory_management_system.util.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author kekem
 */
public class ProductDAO {

    //Create
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, description, price, created_by, updated_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getCreatedBy());
            pstmt.setInt(5, product.getUpdatedBy());
            
            pstmt.executeUpdate();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    //Read
    public List<Product> getAllProducts(){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery()){
            
            while(result.next()){
                Product product = new Product(
                      result.getInt("id"),
                result.getString("product_name"),
                result.getString("description"),
                    result.getDouble("price"),
                 result.getInt("created_by"),
                 result.getInt("updated_by"),
                 result.getObject("created_at", LocalDateTime.class),
                 result.getObject("updated_at", LocalDateTime.class)
                );

                productList.add(product);
            }
            
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        
        return productList;
    }
    
    //Update
    public void updateProduct(Product product){
        String sql = "UPDATE products SET product_name=?, description=?, price=?, updated_by=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getUpdatedBy());
            pstmt.setInt(5, product.getId());
            
            pstmt.executeUpdate();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    //Delete
    public void deleteProduct(int id){
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
