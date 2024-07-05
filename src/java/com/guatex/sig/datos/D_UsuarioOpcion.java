/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_JUsuarioOpcion;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class D_UsuarioOpcion {

    Utils util = new Utils();

    public List<E_JUsuarioOpcion> obtenerOpciones(String usuario) {
        String query = "SELECT USUARIO, CODIGOOPCION FROM JUSUARIOSOPCION WHERE USUARIO = ? ";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, util.limpiaStr(usuario));
            try (ResultSet rs = ps.executeQuery()) {
                List<E_JUsuarioOpcion> listadoRes = new LinkedList<>();
                while (rs.next()) {
                    E_JUsuarioOpcion opc = new E_JUsuarioOpcion();
                    opc.setUSUARIO(util.limpiaStr(rs.getString("USUARIO")));
                    opc.setCODIGOOPCION(util.limpiaStr(rs.getString("CODIGOOPCION")));
                    listadoRes.add(opc);
                }
                return listadoRes;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrio un error en obtenerOpciones: " + e);
            return null;
        }
    }
}
