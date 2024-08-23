/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Facusuario;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RGALICIA
 */
public class D_Facusuarios {

    Utils util = new Utils();

    public boolean validaUsuario(Connection con, E_Credenciales credenciales, E_ImpresionSIG dato) {

        String ueguia = dato.getNOGUIA().substring(0, 3);
        System.out.println(ueguia);

        try (PreparedStatement ps = con.prepareStatement(""
                + " SELECT USUARIO "
                + " FROM FACUSUARIOS "
                + " WHERE UEGUIAS = ? "
                + "     AND PADRE = ("
                + "             SELECT PADRE "
                + "             FROM FACCLIENTES "
                + "             WHERE CODIGO = ? )")) {
            ps.setString(1, ueguia);
            ps.setString(2, dato.getCODCOB());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return util.quitaNulo(rs.getString("USUARIO")).equalsIgnoreCase(credenciales.getUsuario());
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(D_Facusuarios.class.getName()).log(Level.SEVERE, "Error al validar usuario ", e);
        }
        return false;
    }

    public E_Facusuario obtenerDatosUsuario(String usuario) {
        if (usuario != null && !usuario.isEmpty()) {
            try (Connection con = new Conexion().AbrirConexion();
                    PreparedStatement ps = con.prepareStatement(""
                            + " SELECT   UEGUIAS, "
                            + "                 PADRE "
                            + " FROM FACUSUARIOS "
                            + " WHERE USUARIO = ? ")) {
                ps.setString(1, usuario);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new E_Facusuario(util.quitaNulo(rs.getString("UEGUIAS")), util.quitaNulo(rs.getString("PADRE")));
                    }
                }
            } catch (SQLException e) {
                Logger.getLogger(D_Facusuarios.class.getName()).log(Level.SEVERE, "Error al obtener datos de usuario ", e);
            }
        }
        return null;
    }
}
