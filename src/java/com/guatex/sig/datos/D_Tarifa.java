/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_TarifaCliente;
import com.guatex.sig.entidades.E_TarifaMuni;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class D_Tarifa {
    
    Utils util = new Utils();

    public E_TarifaCliente obtenerTarifaCliente(String codcob) {
        if (codcob.isEmpty()) {
            return null;
        }

        String query = "SELECT CODIGO, TARIFAUNICA, TARIFANORMAL, TARIFAEXTRA FROM FACCLIENTES WHERE CODIGO = ?";
        E_TarifaCliente tarifa = null;
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, codcob);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tarifa = new E_TarifaCliente();
                    tarifa.setCODIGO(util.limpiaStr(rs.getString("CODIGO")));
                    tarifa.setTARIFAUNICA(util.limpiaStr(rs.getString("TARIFAUNICA")));
                    tarifa.setTARIFANORMAL(util.limpiaStr(rs.getString("TARIFANORMAL")));
                    tarifa.setTARIFAEXTRA(util.limpiaStr(rs.getString("TARIFAEXTRA")));
                }
                return tarifa;
            }
        } catch (SQLException e) {
            System.out.println("ERROR: ocurrio un error en obtenerTarifaCliente.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    public E_TarifaMuni buscarTarifaMunicipio(E_Cliente cliente) {
        String query = "SELECT m.NOMBRE, m.PUNTODECOBERTURA, m.TIPODETARIFA, m.TOTALZONASEXTRA, t.COBERTURAEXTRA, m.CODIGO  "
                + "FROM TRFMUNICIPIOS as m  "
                + "left outer join TRFTARIFAS as t on isnull (m.TIPODETARIFA,'N')= t.CODIGO  "
                + "WHERE m.PUNTODECOBERTURA = ? AND m.NOMBRE = ?";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cliente.getPUNTO());
            ps.setString(2, cliente.getUBICACION());

            try (ResultSet rs = ps.executeQuery()) {
                E_TarifaMuni tarifa = null;
                while (rs.next()) {
                    tarifa = new E_TarifaMuni();
                    tarifa.setNOMBRE(util.limpiaStr(rs.getString("NOMBRE")));
                    tarifa.setPUNTODECOBERTURA(util.limpiaStr(rs.getString("PUNTODECOBERTURA")));
                    tarifa.setTIPODETARIFA(util.limpiaStr(rs.getString("TIPODETARIFA")));
                    tarifa.setTOTALZONASEXTRA(util.limpiaStr(rs.getString("TOTALZONASEXTRA")));
                    tarifa.setCOBERTURAEXTRA(util.limpiaStr(rs.getString("COBERTURAEXTRA")));
                    tarifa.setCODIGOCOBERTURA(util.limpiaStr(rs.getString("CODIGO")));
                }
                return tarifa;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
