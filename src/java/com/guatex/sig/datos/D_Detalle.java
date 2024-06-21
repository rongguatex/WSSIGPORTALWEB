/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidadesRespuesta.E_RespuestaDetalle;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author RGALICIA
 */
public class D_Detalle {

    Utils util = new Utils();
    
    /**
     * Obtiene listado de detalles como piezas, peso y tarifa de una guia
     *
     * @param noguia
     * @return
     */
    public E_RespuestaDetalle buscarDetalleGuia(String noguia) {
        List<E_DetalleLinea> detalle = new LinkedList<>();
        if (!util.limpiaStr(noguia).isEmpty()) {
            String query = "SELECT "
                    + "	JGD.LINEA AS LINEA, "
                    + "	JGD.PIEZAS AS PIEZAS, "
                    + "	(SELECT NOMBRE FROM TRFENVIOS WHERE CODIGO = JGD.TIPENV) AS TIPOENVIO, "
                    + "	JGD.PESO AS PESO, "
                    + "	JGD.TARIFA AS TARIFA "
                    + " FROM JGUIASDETALLE JGD "
                    + " WHERE JGD.NOGUIA = ? ";
            try (Connection con = new Conexion().AbrirConexion();
                    PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, util.limpiaStr(noguia));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        E_DetalleLinea linea = new E_DetalleLinea();
                        linea.setLINEA(util.limpiaStr(rs.getString("LINEA")));
                        linea.setPIEZAS(convertirAEntero(util.limpiaStr(rs.getString("PIEZAS"))).orElse(0));
                        linea.setTIPOENVIO(util.limpiaStr(rs.getString("TIPOENVIO")));
                        linea.setPESO(util.limpiaStr(rs.getString("PESO")));
                        linea.setTARIFA(util.limpiaStr(rs.getString("TARIFA")));
                        detalle.add(linea);
                    }

                    if (!detalle.isEmpty()) {
                        return new E_RespuestaDetalle("200", detalle);
                    } else {
                        return new E_RespuestaDetalle("204", detalle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
                return new E_RespuestaDetalle("500", detalle);
            }
        } else {
            return new E_RespuestaDetalle("204", detalle);
        }
    }

    /**
     * Convierte cualquier valor tipo string a entero, al ingresar un valor nulo
     * o un valor no válido devuelve un Opcional vacío.
     *
     * @param valor
     * @return valor convertido en tipo entero ó un Opcional vacío de no ser un
     * valor válido.
     */
    public Optional<Integer> convertirAEntero(String valor) {
        return Optional.ofNullable(valor)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                });
    }

    public boolean EliminarGuiasDetalle(String noguia) {
        noguia = util.limpiaStr(noguia);
        String query = "DELETE FROM JGUIASDETALLE WHERE NOGUIA = ?";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, noguia);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
