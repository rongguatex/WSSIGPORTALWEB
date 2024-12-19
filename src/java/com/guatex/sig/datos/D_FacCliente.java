/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_Facusuario;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author RGALICIA
 */
public class D_FacCliente {

    public E_FacCliente obtenerFacCliente(E_Credenciales credenciales) {
        E_FacCliente cliente = obtenerParamsUsuario(credenciales);

        String query = ""
                + " SELECT  FC.PADRE, "
                + "                FC.TARIFANORMAL,  "
                + "	FC.TARIFAEXTRA,  "
                + "	FC.TARIFAUNICA,  "
                + "	FC.LCOD,  "
                + "	ISNULL(FC.COD_MINMONTO,0) COD_MINMONTO, "
                + "	ISNULL(FC.COD_MAXMONTO, 0) COD_MAXMONTO,  "
                + "	FC.SEABREPAQUETE "
                + " FROM FACCLIENTES FC "
                + " WHERE CODIGO = ? AND PADRE = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, Utils.quitaNulo(credenciales.getCodigo()));
            ps.setString(2, Utils.quitaNulo(cliente.getCODPADRE()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cliente.setCODIGO(credenciales.getCodcob());
                    cliente.setTARIFANORMAL(Utils.quitaNulo(rs.getString("TARIFANORMAL")));
                    cliente.setTARIFAEXTRA(Utils.quitaNulo(rs.getString("TARIFAEXTRA")));
                    cliente.setTARIFAUNICA(Utils.quitaNulo(rs.getString("TARIFAUNICA")));
                    cliente.setLCOD(Utils.validaCampo(Utils.quitaNulo(rs.getString("LCOD"))));
                    cliente.setCOD_MINMONTO(rs.getDouble("COD_MINMONTO"));
                    cliente.setCOD_MAXMONTO(rs.getDouble("COD_MAXMONTO"));
                    cliente.setSEABREPAQUETE(Utils.validaCampo(Utils.quitaNulo(rs.getString("SEABREPAQUETE"))));
                    cliente.setMAXPESO(new D_UsuarioOpcion().obtenerPesoMaximo());
                }
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String consultaCodcob(Connection con, String padre, String noguia) {
        try (PreparedStatement ps = con.prepareStatement(""
                + "SELECT F.CODIGO  "
                + "FROM FACCLIENTES F  "
                + "WHERE F.PADRE = ?  "
                + "AND CODIGO IN (SELECT J.CODCOB "
                + "                             FROM JGUIAS J "
                + "                             WHERE J.NOGUIA = ? )")) {
            ps.setString(1, padre);
            ps.setString(2, noguia);
            System.out.println("padre: " + padre);
            System.out.println("noguia: " + noguia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ingresa " + Utils.quitaNulo(rs.getString("CODIGO")));
                    return Utils.quitaNulo(rs.getString("CODIGO"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(D_FacCliente.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public String consultaPadre(Connection con, String codcob) {
        if (con != null) {
            try (PreparedStatement ps = con.prepareStatement("SELECT PADRE FROM FACCLIENTES WHERE CODIGO = ? ")) {
                ps.setString(1, codcob);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Utils.quitaNulo(rs.getString("PADRE"));
                    }
                }
            } catch (SQLException e) {
                Logger.getLogger(D_FacCliente.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public List<String> consultaListadoCodcobs(String padre) {
        List<String> respuesta = new LinkedList<>();
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement("SELECT F.CODIGO FROM FACCLIENTES F WHERE F.PADRE = ? ")) {
            ps.setString(1, padre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    respuesta.add(Utils.quitaNulo(rs.getString("CODIGO")));
                }
                return respuesta;
            }
        } catch (Exception e) {
            Logger.getLogger(D_FacCliente.class.getName()).log(Level.SEVERE, "Error al obtener listado de codcobs.");
        }
        return respuesta;
    }

    public E_FacCliente obtenerParamsUsuario(E_Credenciales datos) {
        E_Facusuario usuario = new E_Facusuario(datos);
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(""
                        + "SELECT "
                        + "     FU.PADRE, "
                        + "     FC.UNIFICACLI  "
                        + "FROM FACUSUARIOS FU "
                        + "INNER JOIN FACCLIENTES FC ON FC.CODIGO = FU.CODCOB "
                        + "WHERE FU.USUARIO = ? ")) {
            ps.setString(1, usuario.getUsuarioCompuesto());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    E_FacCliente respuesta = new E_FacCliente();
                    respuesta.setCODPADRE(Utils.quitaNulo(rs.getString("PADRE")));
                    respuesta.setUNIFICACLI(Utils.validaCampo(Utils.quitaNulo(rs.getString("UNIFICACLI"))));
                    return respuesta;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(D_FacCliente.class.getName()).log(Level.SEVERE, "Error: ", e);
        }
        return null;
    }
}
