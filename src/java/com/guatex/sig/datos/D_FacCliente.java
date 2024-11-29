/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_FacCliente;
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

    Utils util = new Utils();

    public E_FacCliente obtenerFacCliente(String padre, String codcob) {
        String query = ""
                + " SELECT	FC.TARIFANORMAL,  "
                + "		FC.TARIFAEXTRA,  "
                + "		FC.TARIFAUNICA,  "
                + "		FC.UNIFICACLI, "
                + "		FC.LCOD,  "
                + "		ISNULL(FC.COD_MINMONTO,0) COD_MINMONTO, "
                + "		ISNULL(FC.COD_MAXMONTO, 0) COD_MAXMONTO,  "
                + "		FC.SEABREPAQUETE "
                + " FROM FACCLIENTES FC "
                + " WHERE CODIGO = ? AND PADRE = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, util.quitaNulo(codcob));
            ps.setString(2, util.quitaNulo(padre));

            try (ResultSet rs = ps.executeQuery()) {
                E_FacCliente cliente = new E_FacCliente();
                while (rs.next()) {
                    cliente.setCODIGO(codcob);
                    cliente.setPADRE(padre);
                    cliente.setTARIFANORMAL(util.quitaNulo(rs.getString("TARIFANORMAL")));
                    cliente.setTARIFAEXTRA(util.quitaNulo(rs.getString("TARIFAEXTRA")));
                    cliente.setTARIFAUNICA(util.quitaNulo(rs.getString("TARIFAUNICA")));
                    cliente.setLCOD(Utils.validaCampo(util.quitaNulo(rs.getString("LCOD"))));
                    cliente.setCOD_MINMONTO(rs.getDouble("COD_MINMONTO"));
                    cliente.setCOD_MAXMONTO(rs.getDouble("COD_MAXMONTO"));
                    cliente.setSEABREPAQUETE(Utils.validaCampo(util.quitaNulo(rs.getString("SEABREPAQUETE"))));
                    cliente.setUNIFICACLI(Utils.validaCampo(util.quitaNulo(rs.getString("UNIFICACLI"))));
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
                    System.out.println("ingresa " + util.quitaNulo(rs.getString("CODIGO")));
                    return util.quitaNulo(rs.getString("CODIGO"));
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
                        return util.quitaNulo(rs.getString("PADRE"));
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
                    respuesta.add(util.quitaNulo(rs.getString("CODIGO")));
                }
                return respuesta;
            }
        } catch (Exception e) {
            Logger.getLogger(D_FacCliente.class.getName()).log(Level.SEVERE, "Error al obtener listado de codcobs.");
        }
        return respuesta;
    }
}
