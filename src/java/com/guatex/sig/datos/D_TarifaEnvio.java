/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_TarifaEnvio;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class D_TarifaEnvio {
    
     Utils util = new Utils();

    public E_TarifaEnvio BuscarTipoEnvio(String CodigoTarifa, String CodigoEnvio) {
        if (util.limpiaStr(CodigoTarifa).isEmpty() || util.limpiaStr(CodigoEnvio).isEmpty()) {
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
                    tipoenvio.setCODIGO(util.limpiaStr(rs.getString("CODIGO")));
                    tipoenvio.setNOMBRE(util.limpiaStr(rs.getString("NOMBRE")));
                    tipoenvio.setPESOFIJO(util.limpiaStr(rs.getString("PESOFIJO")));
                    tipoenvio.setABREVIATURA(util.limpiaStr(rs.getString("ABREVIATURA")));
                }
                return tipoenvio;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
