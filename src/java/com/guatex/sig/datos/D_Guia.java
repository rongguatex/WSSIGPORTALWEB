/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Servicio;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import com.guatex.sig.utils.ConvertidorXML;
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
public class D_Guia {

    Utils util = new Utils();

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
                + " CONVERT(VARCHAR(15), J.FECHA, 105) AS FECHA, "
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
            ps.setString(1, util.limpiaStr(datos.getCODCOB()));
            ps.setString(2, util.limpiaStr(datos.getFECHA_INICIAL()));
            ps.setString(3, util.limpiaStr(datos.getFECHA_FINAL()));
            ps.setString(4, util.limpiaStr(datos.getIMPRESO()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Guia guia = new E_Guia();
                    guia.setNOGUIA(util.limpiaStr(rs.getString("NOGUIA")));
                    guia.setCONTACTO(util.limpiaStr(rs.getString("CONTACTO")));
                    guia.setNOMDES(util.limpiaStr(rs.getString("NOMDES")));
                    guia.setTELDES(util.limpiaStr(rs.getString("TELDES")));
                    guia.setDIRDES(util.limpiaStr(rs.getString("DIRDES")));
                    guia.setCODCOB(util.limpiaStr(rs.getString("CODCOB")));
                    guia.setSEGURO(util.limpiaStr(rs.getString("SEGURO")));
                    guia.setDECLARADO(util.limpiaStr(rs.getString("DECLARADO")));
                    guia.setMNCPDES(util.limpiaStr(rs.getString("MNCPDES")));
                    guia.setFECHA(util.limpiaStr(rs.getString("FECHA")));
                    guia.setDESCRENV(util.limpiaStr(rs.getString("DESCRENV")));
                    guia.setSEABREPAQUETE(util.limpiaStr(rs.getString("SEABREPAQUETE")));
                    guia.setCONTSEG(util.limpiaStr(rs.getString("CONTSEG")));
                    guia.setCOD_VALORACOBRAR(util.limpiaStr(rs.getString("COD_VALORACOBRAR")));
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
        datos.setNoguia(util.limpiaStr(datos.getNoguia()));
        if (!datos.getNoguia().isEmpty()) {
            List<E_Guia> datosGuia = new LinkedList<>();
            String query = " SELECT   "
                    + "    J.IDGUIA, J.NOGUIA, J.CODCOB, J.IDSERVICIO,  "
                    + "    CONVERT(VARCHAR(10), J.FECHA, 103) AS FECHA,  "
                    + "    J.CODREM, J.NOMREM, J.TELREM, J.DIRREM,  J.COMPLEMENTODIRREM, "
                    + "    J.CODDES, J.NOMDES, J.TELDES, J.DIRDES,  J.COMPLEMENTODIRDES, "
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
                    + "AND ISNULL(J.IMPRESO, 'N') != 'S' "
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
                        guia.setIDGUIA(util.limpiaStr(rs.getString("IDGUIA")));
                        guia.setNOGUIA(util.limpiaStr(rs.getString("NOGUIA")));
                        guia.setCODCOB(util.limpiaStr(rs.getString("CODCOB")));
                        guia.setIDSERVICIO(util.limpiaStr(rs.getString("IDSERVICIO")));
                        guia.setFECHA(util.limpiaStr(rs.getString("FECHA")));
                        //datos de remitente
                        guia.setCODREM(util.limpiaStr(rs.getString("CODREM")));
                        guia.setNOMREM(util.limpiaStr(rs.getString("NOMREM")));
                        guia.setTELREM(util.limpiaStr(rs.getString("TELREM")));
                        guia.setDIRREM(util.limpiaStr(rs.getString("DIRREM")));
                        guia.setCOMPLEMENTODIRREM(util.limpiaStr(rs.getString("COMPLEMENTODIRREM")));
                        guia.setCONTACTO(util.limpiaStr(rs.getString("CONTACTO")));
                        guia.setOBSERVACIONES(util.limpiaStr(rs.getString("OBSERVACIONES")));
                        //datos de destinatario
                        guia.setCODDES(util.limpiaStr(rs.getString("CODDES")));
                        guia.setNOMDES(util.limpiaStr(rs.getString("NOMDES")));
                        guia.setTELDES(util.limpiaStr(rs.getString("TELDES")));
                        guia.setDIRDES(util.limpiaStr(rs.getString("DIRDES")));
                        guia.setCOMPLEMENTODIRDES(util.limpiaStr(rs.getString("COMPLEMENTODIRDES")));
                        guia.setOBSERVACIONESENTRE(util.limpiaStr(rs.getString("OBSERVACIONESENTRE")));
                        //otros datos
                        guia.setPTOORI(util.limpiaStr(rs.getString("PTOORI")));
                        guia.setPTODES(util.limpiaStr(rs.getString("PTODES")));
                        guia.setMNCPORI(util.limpiaStr(rs.getString("MNCPORI")));
                        guia.setMNCPDES(util.limpiaStr(rs.getString("MNCPDES")));
                        guia.setLLAVECLIENTE(util.limpiaStr(rs.getString("LLAVECLIENTE")));
                        guia.setDESCRENV(util.limpiaStr(rs.getString("DESCRENV")));
                        guia.setEMAIL(util.limpiaStr(rs.getString("EMAIL")));
                        guia.setPIEZAS(util.convertirAEntero(util.limpiaStr(rs.getString("PIEZAS"))).orElse(0));
                        guia.setPESO(util.limpiaStr(rs.getString("PESO")));
                        guia.setTIPTAR(util.limpiaStr(rs.getString("TIPTAR")));
                        guia.setCOBEX(util.limpiaStr(rs.getString("COBEX")));
                        guia.setSEGURO(util.limpiaStr(rs.getString("SEGURO")));
                        guia.setDECLARADO(util.limpiaStr(rs.getString("DECLARADO")));
                        guia.setCOD_VALORACOBRAR(util.limpiaStr(rs.getString("COD_VALORACOBRAR")));
                        guia.setSEABREPAQUETE(util.limpiaStr(rs.getString("SEABREPAQUETE")));
                        guia.setCONTSEG(util.limpiaStr(rs.getString("CONTSEG")));
                        guia.setFECOPE(util.limpiaStr(rs.getString("FECOPE")));
                        guia.setHORAOPE(util.limpiaStr(rs.getString("HORAOPE")));
                        guia.setRECOGEOFICINA(util.limpiaStr(rs.getString("RECOGEOFICINA")));
                        guia.setCAMPO1(util.obtenerCodigo(util.limpiaStr(rs.getString("CAMPO1"))));
                        guia.setCAMPO2(util.obtenerCodigo(util.limpiaStr(rs.getString("CAMPO2"))));
                        guia.setCAMPO3(util.obtenerCodigo(util.limpiaStr(rs.getString("CAMPO3"))));
                        guia.setCAMPO4(util.obtenerCodigo(util.limpiaStr(rs.getString("CAMPO4"))));
                        guia.setCODORIGEN(util.limpiaStr(rs.getString("CODORIGEN")));
                        guia.setCODDESTINO(util.limpiaStr(rs.getString("CODDESTINO")));
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
        noguia = util.limpiaStr(noguia);
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

    public List<E_Servicio> obtenerIdServicio(List<E_ImpresionSIG> datos) {

        if (datos.isEmpty()) {
            return null;
        }

        List<E_Servicio> resultado = new LinkedList<>();

        try (Connection con = new Conexion().AbrirConexion()) {
            con.setAutoCommit(false);
            for (E_ImpresionSIG dato : datos) {
                if (dato.getNOGUIA() != null || !dato.getNOGUIA().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement("SELECT IDSERVICIO FROM JGUIAS WHERE NOGUIA = ? ")) {
                        ps.setString(1, dato.getNOGUIA());
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                E_Servicio servicio = new E_Servicio();
                                servicio.setIDSERVICIO(util.limpiaStr(rs.getString("IDSERVICIO")));
                                resultado.add(servicio);
                            }
                        }
                    }
                }
            }

            try (PreparedStatement psUpdate = con.prepareStatement("UPDATE JGUIAS SET IMPRESO = 'S' WHERE NOGUIA = ?")) {
                for (E_ImpresionSIG dato : datos) {
                    psUpdate.setString(1, dato.getNOGUIA());
                    psUpdate.addBatch();
                }

                int[] updateResults = psUpdate.executeBatch();

                for (int arr : updateResults) {
                    if (updateResults[arr - 1] == PreparedStatement.EXECUTE_FAILED || updateResults[arr - 1] <= 0) {
                        con.rollback();
                        System.out.println("Error en batch de UPDATE, se realiza rollback");
                        return null;
                    }
                }
                con.commit();
                return resultado;
            } catch (SQLException sqlException) {
                System.out.println("ocurrio un error e ingreso al sqlException");
                sqlException.printStackTrace(System.err);
                if (con != null) {
                    try {
                        System.out.println("Se realiza el rollback");
                        con.rollback();
                    } catch (SQLException rollbackException) {
                        System.out.println("ocurrio un error e ingreso al rollbackException");
                        rollbackException.printStackTrace(System.err);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verificaDescarga(List<E_ImpresionSIG> datos) {
        boolean isPrinted = false;

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement st = con.prepareStatement(" SELECT ISNULL(IMPRESO, 'N') IMPRESO FROM JGUIAS WHERE NOGUIA = ? ")) {
            for (E_ImpresionSIG dato : datos) {
                st.setString(1, dato.getNOGUIA());

                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        if (util.limpiaStr(rs.getString("IMPRESO")).equalsIgnoreCase("S")) {
                            isPrinted = true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return "500";
        }

        if (isPrinted) {
            return "998";
        }
        return "200";
    }

    public String verificaDescargaReimpresion(List<E_ImpresionSIG> datos) {
        boolean isDelivered = false;

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement st = con.prepareStatement(" SELECT J.NOGUIA FROM JGUIAS J WHERE J.NOGUIA = ? AND NOT EXISTS(SELECT G.NOGUIA FROM GUIAS G WHERE G.NOGUIA = ? ) ")) {
            for (E_ImpresionSIG dato : datos) {
                st.setString(1, dato.getNOGUIA());
                st.setString(2, dato.getNOGUIA());

                try (ResultSet rs = st.executeQuery()) {
                    if (!rs.isBeforeFirst()) {
                        System.out.println(rs.isBeforeFirst());
                        isDelivered = true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return "500";
        }

        if (isDelivered) {
            return "999";
        }
        return "200";
    }
}
