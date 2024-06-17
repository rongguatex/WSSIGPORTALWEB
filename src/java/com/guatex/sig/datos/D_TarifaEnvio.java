/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_TarifaEnvio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class D_TarifaEnvio {

    public E_TarifaEnvio BuscarTipoEnvio(String CodigoTarifa, String CodigoEnvio) {
        if (quitaNulo(CodigoTarifa).isEmpty() || quitaNulo(CodigoEnvio).isEmpty()) {
             return null;
        }
        
        String query = "SELECT CODIGO, NOMBRE, ISNULL(PESOFIJO,0) PESOFIJO, ABREVIATURA FROM TRFENVIOS "
                + "WHERE CODIGO IN (SELECT CODIGOENVIO FROM TRFTARIFARIO "
                + "WHERE CODIGOTARIFA = ? AND CODIGO = ? "
                + "GROUP BY CODIGOENVIO)";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, CodigoTarifa);
            ps.setString(2, CodigoEnvio);

            try (ResultSet rs = ps.executeQuery()) {
                E_TarifaEnvio tipoenvio = null;
                while (rs.next()) {
                    tipoenvio = new E_TarifaEnvio();
                    tipoenvio.setCODIGO(quitaNulo(rs.getString("CODIGO")));
                    tipoenvio.setNOMBRE(quitaNulo(rs.getString("NOMBRE")));
                    tipoenvio.setPESOFIJO(quitaNulo(rs.getString("PESOFIJO")));
                    tipoenvio.setABREVIATURA(quitaNulo(rs.getString("ABREVIATURA")));
                }
                return tipoenvio;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }
}
