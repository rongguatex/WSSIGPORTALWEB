/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_FacCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class D_FacCliente {

    public E_FacCliente obtenerFacCliente(String padre, String codcob) {
        String query = " SELECT  "
                + "	LCOD, LCONTADOFRECUENTE, LCREDITO, LXCOBRAR, LPREPAGADA,  "
                + "	TARIFANORMAL, TARIFAEXTRA, TARIFAUNICA, TARIFAESPECIAL, "
                + "	UNIFICACLI, IMPRIMEGUIAS, MUESTRASEGURO, MUESTRARECOOFI  "
                + " FROM FACCLIENTES  "
                + " WHERE CODIGO = ? AND PADRE = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, quitaNulo(codcob));
            ps.setString(2, quitaNulo(padre));

            try (ResultSet rs = ps.executeQuery()) {
                E_FacCliente cliente = new E_FacCliente();
                while (rs.next()) {
                    cliente.setCODIGO(codcob);
                    cliente.setPADRE(padre);
                    cliente.setLCOD(quitaNulo(rs.getString("LCOD")));
                    cliente.setLCONTADO(quitaNulo(rs.getString("LCONTADOFRECUENTE")));
                    cliente.setLCREDITO(quitaNulo(rs.getString("LCREDITO")));
                    cliente.setLXCOBRAR(quitaNulo(rs.getString("LXCOBRAR")));
                    cliente.setLPREPAGO(quitaNulo(rs.getString("LPREPAGADA")));
                    cliente.setTARIFANORMAL(quitaNulo(rs.getString("TARIFANORMAL")));
                    cliente.setTARIFAEXTRA(quitaNulo(rs.getString("TARIFAEXTRA")));
                    cliente.setTARIFAUNICA(quitaNulo(rs.getString("TARIFAUNICA")));
                    cliente.setTARIFAESPECIAL(quitaNulo(rs.getString("TARIFAESPECIAL")));
                    cliente.setUNIFICACLI(quitaNulo(rs.getString("UNIFICACLI")));
                    cliente.setIMPRIMEGUIAS(quitaNulo(rs.getString("IMPRIMEGUIAS")));
                    cliente.setMUESTRASEGURO(quitaNulo(rs.getString("MUESTRASEGURO")));
                    cliente.setMUESTRARECOOFI(quitaNulo(rs.getString("MUESTRARECOOFI")));
                }
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }
}
