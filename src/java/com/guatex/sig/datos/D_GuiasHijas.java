/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class D_GuiasHijas {
    
    Utils util = new Utils();

    public boolean eliminaHijas(String noguia) {
        String query = "DELETE JGUIASHIJAS WHERE HNOGUIA = ? ";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, util.limpiaStr(noguia));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ocurrio un error " + e.getLocalizedMessage());
            return false;
        }
    }
}
