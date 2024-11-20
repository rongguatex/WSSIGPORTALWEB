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
            ps.setString(1, util.quitaNulo(usuario));
            try (ResultSet rs = ps.executeQuery()) {
                List<E_JUsuarioOpcion> listadoRes = new LinkedList<>();
                while (rs.next()) {
                    E_JUsuarioOpcion opc = new E_JUsuarioOpcion();
                    opc.setUSUARIO(util.quitaNulo(rs.getString("USUARIO")));
                    opc.setCODIGOOPCION(util.quitaNulo(rs.getString("CODIGOOPCION")));
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

    public double obtenerPesoMaximo() {
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement("SELECT VALOR FROM PARAMETROS WHERE SISTEMA = ? AND IDENTIFICADOR = ? ")) {
            ps.setString(1, "SIGWEB");
            ps.setString(2, "MAXPESO");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    double pesoMaximo = Utils.convertirADouble(util.quitaNulo(rs.getString("VALOR"))).orElse((double) 0);
                    return pesoMaximo;
                }
            }
        } catch (Exception e) {
        }
        return (double) 0;
    }
}
