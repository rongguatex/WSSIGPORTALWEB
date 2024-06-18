/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
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
     * Busca los datos de una guía que no haya sido impresa y tampoco entregada.
     *
     * @param noguia - número de guía a buscar
     * @return - objeto con código de respuesta y datos de la guía.
     */
    public E_RespuestaGuia obtenerDatosxGuiaNoImpresa(E_Credenciales datos) {
        datos.setNoguia(quitaNulo(datos.getNoguia()));
        if (!datos.getNoguia().isEmpty()) {
            List<E_Guia> datosGuia = new LinkedList<>();
            String query = " SELECT   "
                    + "    J.IDGUIA, J.NOGUIA, J.CODCOB, J.IDSERVICIO,  "
                    + "    CONVERT(VARCHAR(10), J.FECHA, 103) AS FECHA,  "
                    + "    J.CODREM, J.NOMREM, J.TELREM, J.DIRREM,  "
                    + "    J.CODDES, J.NOMDES, J.TELDES, J.DIRDES,  "
                    + "    J.PTOORI, J.PTODES, J.MNCPORI, J.MNCPDES,  "
                    + "    J.LLAVECLIENTE, J.DESCRENV, J.CONTACTO, J.EMAIL,  "
                    + "    J.PIEZAS, J.PESO, J.TIPTAR,   "
                    + "    J.COBEX, J.SEGURO, J.DECLARADO, J.COD_VALORACOBRAR,  "
                    + "    ISNULL(J.SEABREPAQUETE,'N') SEABREPAQUETE,  "
                    + "    J.CONTSEG, J.FECOPE, J.HORAOPE,  J.RECOGEOFICINA,  "
                    + "    J.CAMPO1, J.CAMPO2, J.CAMPO3, J.CAMPO4,  "
                    + "    J.CODORIGEN, J.CODDESTINO,   "
                    + "    J.OBSERVACIONES, J.OBSERVACIONESENTRE  "
                    + "FROM JGUIAS J   "
                    + "INNER JOIN FACCLIENTES FC ON J.CODCOB = FC.CODIGO  "
                    + "WHERE NOGUIA = ? "
                    + "AND FC.PADRE = ? "
                    + "AND ISNULL(J.IMPRESO, 'N') != 'P' "
                    + "AND NOT EXISTS ( "
                    + "    SELECT "
                    + "        NOGUIA "
                    + "    FROM GUIAS G "
                    + "    WHERE G.NOGUIA = J.NOGUIA  "
                    + ") ";

            try (Connection con = new Conexion().AbrirConexion();
                    PreparedStatement ps = con.prepareStatement(query)) {
                System.out.println(datos);
                ps.setString(1, datos.getNoguia());
                ps.setString(2, datos.getPadre());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        E_Guia guia = new E_Guia();
                        //datos generales de guía
                        guia.setIDGUIA(quitaNulo(rs.getString("IDGUIA")));
                        guia.setNOGUIA(quitaNulo(rs.getString("NOGUIA")));
                        guia.setCODCOB(quitaNulo(rs.getString("CODCOB")));
                        guia.setIDSERVICIO(quitaNulo(rs.getString("IDSERVICIO")));
                        guia.setFECHA(quitaNulo(rs.getString("FECHA")));
                        //datos de remitente
                        guia.setCODREM(quitaNulo(rs.getString("CODREM")));
                        guia.setNOMREM(quitaNulo(rs.getString("NOMREM")));
                        guia.setTELREM(quitaNulo(rs.getString("TELREM")));
                        guia.setDIRREM(quitaNulo(rs.getString("DIRREM")));
                        guia.setCONTACTO(quitaNulo(rs.getString("CONTACTO")));
                        guia.setOBSERVACIONES(quitaNulo(rs.getString("OBSERVACIONES")));
                        //datos de destinatario
                        guia.setCODDES(quitaNulo(rs.getString("CODDES")));
                        guia.setNOMDES(quitaNulo(rs.getString("NOMDES")));
                        guia.setTELDES(quitaNulo(rs.getString("TELDES")));
                        guia.setDIRDES(quitaNulo(rs.getString("DIRDES")));
                        guia.setOBSERVACIONESENTRE(quitaNulo(rs.getString("OBSERVACIONESENTRE")));
                        //otros datos
                        guia.setPTOORI(quitaNulo(rs.getString("PTOORI")));
                        guia.setPTODES(quitaNulo(rs.getString("PTODES")));
                        guia.setMNCPORI(quitaNulo(rs.getString("MNCPORI")));
                        guia.setMNCPDES(quitaNulo(rs.getString("MNCPDES")));
                        guia.setLLAVECLIENTE(quitaNulo(rs.getString("LLAVECLIENTE")));
                        guia.setDESCRENV(quitaNulo(rs.getString("DESCRENV")));
                        guia.setEMAIL(quitaNulo(rs.getString("EMAIL")));
                        guia.setPIEZAS(convertirAEntero(quitaNulo(rs.getString("PIEZAS"))).orElse(0));
                        guia.setPESO(quitaNulo(rs.getString("PESO")));
                        guia.setTIPTAR(quitaNulo(rs.getString("TIPTAR")));
                        guia.setCOBEX(quitaNulo(rs.getString("COBEX")));
                        guia.setSEGURO(quitaNulo(rs.getString("SEGURO")));
                        guia.setDECLARADO(quitaNulo(rs.getString("DECLARADO")));
                        guia.setCOD_VALORACOBRAR(quitaNulo(rs.getString("COD_VALORACOBRAR")));
                        guia.setSEABREPAQUETE(quitaNulo(rs.getString("SEABREPAQUETE")));
                        guia.setCONTSEG(quitaNulo(rs.getString("CONTSEG")));
                        guia.setFECOPE(quitaNulo(rs.getString("FECOPE")));
                        guia.setHORAOPE(quitaNulo(rs.getString("HORAOPE")));
                        guia.setRECOGEOFICINA(quitaNulo(rs.getString("RECOGEOFICINA")));
                        guia.setCAMPO1(obtenerCodigo(quitaNulo(rs.getString("CAMPO1"))));
                        guia.setCAMPO2(obtenerCodigo(quitaNulo(rs.getString("CAMPO2"))));
                        guia.setCAMPO3(obtenerCodigo(quitaNulo(rs.getString("CAMPO3"))));
                        guia.setCAMPO4(obtenerCodigo(quitaNulo(rs.getString("CAMPO4"))));
                        guia.setCODORIGEN(quitaNulo(rs.getString("CODORIGEN")));
                        guia.setCODDESTINO(quitaNulo(rs.getString("CODDESTINO")));
                        datosGuia.add(guia);
                    }
                }
                if (!datosGuia.isEmpty()) {
                    return new E_RespuestaGuia("200", datosGuia);
                } else {
                    return new E_RespuestaGuia("204", null);
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
                return new E_RespuestaGuia("500", null);
            }
        }
        return new E_RespuestaGuia("400", null);
    }

    public boolean eliminaGuia(String noguia) {
        noguia = quitaNulo(noguia);
        if (noguia.isEmpty()) {
            return false;
        }

        String query = "DELETE FROM JGUIAS WHERE NOGUIA = ? ";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, noguia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en eliminación de guía " + e.getLocalizedMessage());
        }

        return false;
    }

    /**
     * Este método sirve para obtener solamente el valor del campo1,2, 3 y 4. El
     * fin es quitar todo lo que venga antes del caracter "/" y obtener
     * solamente el código.
     *
     * @param campo
     * @return - código seteado en el campo 1, 2, 3, y 4.
     */
    public String obtenerCodigo(String campo) {
        return campo.substring(campo.indexOf("/") + 1, campo.length());
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
        if (var == null) {
            return "";
        } else {
            var = var.replaceAll("null", "").replaceAll("NULL", "");
            return var.trim();
        }
    }
}
