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
import java.util.LinkedList;
import java.util.List;
import com.guatex.sig.utils.Pair;

/**
 *
 * @author RGALICIA
 */
public class D_TarifaEnvio {

    Utils util = new Utils();

    public List<Pair<String, String>> obtenerTiposEnvio(E_FacCliente faccliente) {
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(""
                        + " SELECT  "
                        + "    TE.CODIGO, "
                        + "    ISNULL(TE.PESOFIJO, 0) AS PESOFIJO "
                        + "FROM  "
                        + "    TRFENVIOS TE "
                        + "WHERE  "
                        + "    TE.CODIGO IN ( "
                        + "        SELECT DISTINCT TF.CODIGOENVIO  "
                        + "        FROM TRFTARIFARIO TF "
                        + "        WHERE TF.CODIGOTARIFA IN (?, ?, ?) "
                        + "    ) "
                        + "ORDER BY CODIGO ASC; ")) { //1. tarifa normal, 2. tarifa extra, 3. tarifa única.
            ps.setString(1, faccliente.getTARIFANORMAL());
            ps.setString(2, faccliente.getTARIFAEXTRA());
            ps.setString(3, faccliente.getTARIFAUNICA());
            List<Pair<String, String>> resultado = new LinkedList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pair<String, String> pair = new Pair<>(util.quitaNulo(rs.getString("CODIGO")), util.quitaNulo(rs.getString("PESOFIJO")));
                    resultado.add(pair);
                }
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener listado de tipos de envío - " + e.getMessage());
        }
        return null;
    }
}
