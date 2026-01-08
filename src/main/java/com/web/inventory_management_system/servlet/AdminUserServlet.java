/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web.inventory_management_system.servlet;

import com.web.inventory_management_system.dao.UserDAO;
import com.web.inventory_management_system.model.User;
import com.web.inventory_management_system.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author kekem
 */
@WebServlet(name = "AdminUserServlet", urlPatterns = {"/admin/users"})
public class AdminUserServlet extends HttpServlet {

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
       ResponseUtil json = new ResponseUtil();
       UserDAO userDAO = new UserDAO();
       json.setupResponseHeaders(response);
       Map<String, Object> responseData = new HashMap<>();
       
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       String role = request.getParameter("role");
       
       HttpSession session = request.getSession();
       if (session == null || !"Admin".equals(session.getAttribute("role"))){
           responseData.put("error", "Forbidden");
           responseData.put("authenticated", false);
           json.sendJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, responseData);
           return;
       }
       
       String hashedPassword = PasswordUtil.hashPassword(password);
       
       User user = new User(0, username, hashedPassword, role);
       
       if (!userDAO.createUser(user)){
           responseData.put("error", "Username already exists");
           responseData.put("authenticated", false);
           json.sendJsonResponse(response, HttpServletResponse.SC_CONFLICT, responseData);
           return;
       }
       
       responseData.put("message", "User created");
       responseData.put("authenticated", true);
       json.sendJsonResponse(response, HttpServletResponse.SC_CREATED, responseData);
      
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
