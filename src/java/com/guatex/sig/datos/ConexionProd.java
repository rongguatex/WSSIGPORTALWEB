/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PJUNIOR-3
 */
public class ConexionProd {
    private static final String url = "jdbc:sqlserver://192.168.14.224";
    private static final String usuario = "operaciones";
    private static final String pswrd = "gtxgtx01";

    public Connection AbrirConexion() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, usuario, pswrd);
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
