/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = " SELECT  "
                + "	LCOD, LCONTADOFRECUENTE, LCREDITO, LXCOBRAR, LPREPAGADA,  "
                + "	TARIFANORMAL, TARIFAEXTRA, TARIFAUNICA, TARIFAESPECIAL, "
                + "	UNIFICACLI, IMPRIMEGUIAS, MUESTRASEGURO, MUESTRARECOOFI  "
                + " FROM FACCLIENTES  "
                + " WHERE CODIGO = ? AND PADRE = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, util.limpiaStr(codcob));
            ps.setString(2, util.limpiaStr(padre));

            try (ResultSet rs = ps.executeQuery()) {
                E_FacCliente cliente = new E_FacCliente();
                while (rs.next()) {
                    cliente.setCODIGO(codcob);
                    cliente.setPADRE(padre);
                    cliente.setLCOD(util.limpiaStr(rs.getString("LCOD")));
                    cliente.setLCONTADO(util.limpiaStr(rs.getString("LCONTADOFRECUENTE")));
                    cliente.setLCREDITO(util.limpiaStr(rs.getString("LCREDITO")));
                    cliente.setLXCOBRAR(util.limpiaStr(rs.getString("LXCOBRAR")));
                    cliente.setLPREPAGO(util.limpiaStr(rs.getString("LPREPAGADA")));
                    cliente.setTARIFANORMAL(util.limpiaStr(rs.getString("TARIFANORMAL")));
                    cliente.setTARIFAEXTRA(util.limpiaStr(rs.getString("TARIFAEXTRA")));
                    cliente.setTARIFAUNICA(util.limpiaStr(rs.getString("TARIFAUNICA")));
                    cliente.setTARIFAESPECIAL(util.limpiaStr(rs.getString("TARIFAESPECIAL")));
                    cliente.setUNIFICACLI(util.limpiaStr(rs.getString("UNIFICACLI")));
                    cliente.setIMPRIMEGUIAS(util.limpiaStr(rs.getString("IMPRIMEGUIAS")));
                    cliente.setMUESTRASEGURO(util.limpiaStr(rs.getString("MUESTRASEGURO")));
                    cliente.setMUESTRARECOOFI(util.limpiaStr(rs.getString("MUESTRARECOOFI")));
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
}
