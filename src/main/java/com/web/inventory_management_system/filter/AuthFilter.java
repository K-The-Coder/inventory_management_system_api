/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.web.inventory_management_system.filter;

import com.web.inventory_management_system.util.ResponseUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author kekem
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/api/*"})
public class AuthFilter implements Filter {
    
    private final ResponseUtil json = new ResponseUtil();
    private final Map responseData = new HashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean isLoggedIn = (session != null && session.getAttribute("role") != null);
        
        System.out.println("Filter intercepted request to: " + httpRequest.getRequestURI());
        
        if (isLoggedIn){
            chain.doFilter(request, response);
        }
        else{
            json.setupResponseHeaders(httpResponse);
            responseData.put("error", "Unauthorized Access");
            json.sendJsonResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, responseData);
        }
        
    }
}
