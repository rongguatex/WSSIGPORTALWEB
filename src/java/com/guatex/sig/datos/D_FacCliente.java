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
}
