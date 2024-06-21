/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_ValoresCOD;
import com.guatex.sig.utils.Utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class D_ValoresCOD {
    
    public E_ValoresCOD ObtengoValoresCOD(String CODCOB) {
        String query = "SELECT ISNULL(COD_MINMONTO,0) COD_MINMONTO, ISNULL(COD_MAXMONTO, 0) COD_MAXMONTO, ISNULL(COD_MAXGUIAS,0) COD_MAXGUIAS "
                + " FROM FACCLIENTES "
                + " WHERE CODIGO = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, CODCOB);
            try (ResultSet rs = ps.executeQuery()) {
                E_ValoresCOD datos = new E_ValoresCOD();
                while (rs.next()) {
                    datos.setGuiasCODMax(rs.getInt("COD_MAXGUIAS"));
                    datos.setValorCODMax(rs.getDouble("COD_MAXMONTO"));
                    datos.setValorCODMin(rs.getDouble("COD_MINMONTO"));
                }
                return datos;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
