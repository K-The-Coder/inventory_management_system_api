/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web.inventory_management_system.servlet;

import com.web.inventory_management_system.dao.UserDAO;
import com.web.inventory_management_system.model.User;
import com.web.inventory_management_system.util.PasswordUtil;
import com.web.inventory_management_system.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kekem
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private final ResponseUtil json = new ResponseUtil();
    private final UserDAO userDAO = new UserDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        json.setupResponseHeaders(response);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Map<String, Object> responseData = new HashMap<>();
        
        if (username == null || password == null){
            responseData.put("error", "Username and password required");
            responseData.put("authenticated", false);
            json.sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, responseData);
            return;
        }
        
        User user = userDAO.authenticate(username);
        
        if (user == null || !PasswordUtil.verifyPassword(password, user.getPassword())){
            responseData.put("error", "Invalid credentials");
            responseData.put("authenticated", false);
            json.sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, responseData);
            return;
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());
        
        responseData.put("message", "Login successful");
        responseData.put("authenticated", true);
        responseData.put("role", user.getRole());
        json.sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
