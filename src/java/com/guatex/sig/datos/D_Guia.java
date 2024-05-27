/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author RGALICIA
 */
public class D_Guia {

    /**
     * Busca las guías (campo IMPRESO = P | G) en un rango de fechas, que esten
     * en la tabla JGUIAS pero que NO que hayan sido recolectadas (noguia NO
     * existan en tabla GUIAS)
     *
     * tipo P = guías impresas | G = guías guardadas.
     *
     * @param datos - filtros: codcob, fechaInicial, fechaFinal
     * @return
     */
    public E_RespuestaGuia BuscarRangoFechaJGuiasNoImpresas(E_Guia datos) {
        List<E_Guia> listadoGuias = new LinkedList<>();

        String query = " SELECT "
                + " J.NOGUIA AS NOGUIA,  "
                + " J.CONTACTO,  "
                + " J.NOMDES,  "
                + " J.TELDES, "
                + " J.DIRDES, "
                + " J.CODCOB,  "
                + " J.SEGURO,  "
                + " J.DECLARADO, "
                + " J.MNCPDES,  "
                + " J.FECHA, "
                + " J.DESCRENV, "
                + " J.SEABREPAQUETE, "
                + " J.CONTSEG, "
                + " J.COD_VALORACOBRAR "
                + " FROM JGUIAS J "
                + " LEFT JOIN GUIAS G ON J.NOGUIA = G.NOGUIA "
                + " WHERE J.CODCOB = ? "
                + " AND CAST(J.FECHA AS DATE) BETWEEN CAST(? AS DATE) AND CAST(?  AS DATE) "
                + " AND ISNULL(J.IMPRESO, 'N') = ? "
                + " AND NOT EXISTS ( "
                + "    SELECT  "
                + "         NOGUIA  "
                + "    FROM GUIAS GS  "
                + "    WHERE GS.NOGUIA = J.NOGUIA "
                + " ) "
                + " ORDER BY J.FECHA DESC ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, quitaNulo(datos.getCODCOB()));
            ps.setString(2, quitaNulo(datos.getFECHA_INICIAL()));
            ps.setString(3, quitaNulo(datos.getFECHA_FINAL()));
            ps.setString(4, quitaNulo(datos.getIMPRESO()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Guia guia = new E_Guia();
                    guia.setNOGUIA(quitaNulo(rs.getString("NOGUIA")));
                    guia.setCONTACTO(quitaNulo(rs.getString("CONTACTO")));
                    guia.setNOMDES(quitaNulo(rs.getString("NOMDES")));
                    guia.setTELDES(quitaNulo(rs.getString("TELDES")));
                    guia.setDIRDES(quitaNulo(rs.getString("DIRDES")));
                    guia.setCODCOB(quitaNulo(rs.getString("CODCOB")));
                    guia.setSEGURO(quitaNulo(rs.getString("SEGURO")));
                    guia.setDECLARADO(quitaNulo(rs.getString("DECLARADO")));
                    guia.setMNCPDES(quitaNulo(rs.getString("MNCPDES")));
                    guia.setFECHA(quitaNulo(rs.getString("FECHA")));
                    guia.setDESCRENV(quitaNulo(rs.getString("DESCRENV")));
                    guia.setSEABREPAQUETE(quitaNulo(rs.getString("SEABREPAQUETE")));
                    guia.setCONTSEG(quitaNulo(rs.getString("CONTSEG")));
                    guia.setCOD_VALORACOBRAR(quitaNulo(rs.getString("COD_VALORACOBRAR")));
                    listadoGuias.add(guia);
                }
                if (!listadoGuias.isEmpty()) {
                    return new E_RespuestaGuia("200", listadoGuias);
                } else {
                    return new E_RespuestaGuia("204", listadoGuias);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return new E_RespuestaGuia("500", listadoGuias);
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

    /**
     * Quita el valor nulo
     *
     * @param var
     * @return devuelve un valor vacío
     */
    private String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }
}
