/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmh.uploadfiletool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chanson
 */
public class ConnectionFactory {
    
    private static final String URL = "jdbc:mysql://localhost/files_db";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Database not connected", e);
        }
    }
    
}
