/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web.inventory_management_system.servlet;

import com.web.inventory_management_system.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author kekem
 */
@WebServlet(name = "CheckSessionServlet", urlPatterns = {"/checksession"})
public class CheckSessionServlet extends HttpServlet {
    
    private final ResponseUtil json = new ResponseUtil();

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
        Map<String, Object> responseData = new HashMap<>();
        json.setupResponseHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
        
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("role") != null){
            String userRole = (String) session.getAttribute("role");
            
            responseData.put("authenticated", true);
            responseData.put("role", userRole);
            json.sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
        }
        else{
            responseData.put("authenticated", false);
            json.sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
        }
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
