/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web.inventory_management_system.util;

import com.google.gson.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 *
 * @author kekem
 */
public class ResponseUtil {
    private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) -> 
            new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> 
            LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    .create();
    
    public void sendJsonResponse(HttpServletResponse response, int statusCode, Map<String, Object> data) throws IOException{
        response.setStatus(statusCode);
        try (PrintWriter out = response.getWriter()){
            out.write(gson.toJson(data));
        }
    }
    
    public void setupResponseHeaders(HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
