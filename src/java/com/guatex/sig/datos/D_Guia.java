/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_Facusuario;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Servicio;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                + " J.COMPLEMENTODIRDES, "
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
            ps.setString(1, util.quitaNulo(datos.getCODCOB()));
            ps.setString(2, util.quitaNulo(datos.getFECHA_INICIAL()));
            ps.setString(3, util.quitaNulo(datos.getFECHA_FINAL()));
            ps.setString(4, util.quitaNulo(datos.getIMPRESO()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Guia guia = new E_Guia();
                    guia.setNOGUIA(util.quitaNulo(rs.getString("NOGUIA")));
                    guia.setCONTACTO(util.quitaNulo(rs.getString("CONTACTO")));
                    guia.setNOMDES(util.quitaNulo(rs.getString("NOMDES")));
                    guia.setTELDES(util.quitaNulo(rs.getString("TELDES")));
                    guia.setDIRDES(util.quitaNulo(rs.getString("DIRDES")) + util.quitaNulo(rs.getString("COMPLEMENTODIRDES")));
                    guia.setCODCOB(util.quitaNulo(rs.getString("CODCOB")));
                    guia.setSEGURO(util.quitaNulo(rs.getString("SEGURO")));
                    guia.setDECLARADO(util.quitaNulo(rs.getString("DECLARADO")));
                    guia.setMNCPDES(util.quitaNulo(rs.getString("MNCPDES")));
                    guia.setFECHA(util.quitaNulo(rs.getString("FECHA")));
                    guia.setDESCRENV(util.quitaNulo(rs.getString("DESCRENV")));
                    guia.setSEABREPAQUETE(util.quitaNulo(rs.getString("SEABREPAQUETE")));
                    guia.setCONTSEG(util.quitaNulo(rs.getString("CONTSEG")));
                    guia.setCOD_VALORACOBRAR(util.quitaNulo(rs.getString("COD_VALORACOBRAR")));
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
     * @param datos
     * @return - objeto con código de respuesta y datos de la guía.
     */
    public E_RespuestaGuia obtenerDatosxGuiaNoImpresa(E_Credenciales datos) {
        datos.setNoguia(util.quitaNulo(datos.getNoguia()));
        if (!datos.getNoguia().isEmpty()) {
            List<E_Guia> datosGuia = new LinkedList<>();
            String query = " SELECT "
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
                    + "    J.OBSERVACIONES, J.OBSERVACIONESENTRE, J.IMPRESO, "
                    + "    ISNULL(NULLIF(FC.LPREPAGADA, ''), 'N') AS LPREPAGADA "
                    + "FROM JGUIAS J  "
                    + "INNER JOIN FACCLIENTES FC ON J.CODCOB = FC.CODIGO  "
                    + "WHERE NOGUIA = ? "
                    + "AND FC.PADRE = ? "
                    + "AND ISNULL(J.IMPRESO, 'N') NOT IN ('S', 'R') "
                    + "AND NOT EXISTS ( "
                    + "    SELECT "
                    + "        NOGUIA "
                    + "    FROM GUIAS G "
                    + "    WHERE G.NOGUIA = J.NOGUIA  "
                    + ") ";

            String sqlDetalle = "SELECT jd.LINEA, jd.PIEZAS, jd.TIPENV, jd.PESO, jd.TARIFA, te.NOMBRE AS DESCRIPCIONENVIO FROM JGUIASDETALLE jd "
                    + " INNER JOIN TRFENVIOS te "
                    + " ON te.CODIGO = jd.TIPENV "
                    + " WHERE NOGUIA = ? ";

            String operacion = "RESERVADO";

            try (Connection con = new Conexion().AbrirConexion();
                    PreparedStatement ps = con.prepareStatement(query)) {
                System.out.println(datos);
                ps.setString(1, datos.getNoguia());
                ps.setString(2, datos.getPadre());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        E_Guia guia = new E_Guia();
                        //datos generales de guía
                        guia.setIDGUIA(util.quitaNulo(rs.getString("IDGUIA")));
                        guia.setNOGUIA(util.quitaNulo(rs.getString("NOGUIA")));
                        guia.setCODCOB(util.quitaNulo(rs.getString("CODCOB")));
                        guia.setIDSERVICIO(util.quitaNulo(rs.getString("IDSERVICIO")));
                        guia.setFECHA(util.quitaNulo(rs.getString("FECHA")));
                        guia.setIMPRESO(util.quitaNulo(rs.getString("IMPRESO")));
                        System.out.println("---> Impreso: " + guia.getIMPRESO());
                        guia.setGUIAPREPAGO(util.quitaNulo(rs.getString("LPREPAGADA")));
                        //datos de remitente
                        guia.setCODREM(util.quitaNulo(rs.getString("CODREM")));
                        guia.setNOMREM(util.quitaNulo(rs.getString("NOMREM")));
                        guia.setTELREM(util.quitaNulo(rs.getString("TELREM")));
                        guia.setDIRREM(util.quitaNulo(rs.getString("DIRREM")));
                        guia.setCOMPLEMENTODIRREM(util.quitaNulo(rs.getString("COMPLEMENTODIRREM")));
                        guia.setCONTACTO(util.quitaNulo(rs.getString("CONTACTO")));
                        guia.setOBSERVACIONES(util.quitaNulo(rs.getString("OBSERVACIONES")));
                        //datos de destinatario
                        guia.setCODDES(util.quitaNulo(rs.getString("CODDES")));
                        guia.setNOMDES(util.quitaNulo(rs.getString("NOMDES")));
                        guia.setTELDES(util.quitaNulo(rs.getString("TELDES")));
                        guia.setDIRDES(util.quitaNulo(rs.getString("DIRDES")));
                        guia.setCOMPLEMENTODIRDES(util.quitaNulo(rs.getString("COMPLEMENTODIRDES")));
                        guia.setOBSERVACIONESENTRE(util.quitaNulo(rs.getString("OBSERVACIONESENTRE")));
                        //otros datos
                        guia.setPTOORI(util.quitaNulo(rs.getString("PTOORI")));
                        guia.setPTODES(util.quitaNulo(rs.getString("PTODES")));
                        guia.setMNCPORI(util.quitaNulo(rs.getString("MNCPORI")));
                        guia.setMNCPDES(util.quitaNulo(rs.getString("MNCPDES")));
                        guia.setLLAVECLIENTE(util.quitaNulo(rs.getString("LLAVECLIENTE")));
                        guia.setDESCRENV(util.quitaNulo(rs.getString("DESCRENV")));
                        guia.setEMAIL(util.quitaNulo(rs.getString("EMAIL")));
                        guia.setPIEZAS(Utils.convertirAEntero(util.quitaNulo(rs.getString("PIEZAS"))).orElse(0));
                        guia.setPESO(util.quitaNulo(rs.getString("PESO")));
                        guia.setTIPTAR(util.quitaNulo(rs.getString("TIPTAR")));
                        guia.setCOBEX(util.quitaNulo(rs.getString("COBEX")));
                        guia.setSEGURO(util.quitaNulo(rs.getString("SEGURO")));
                        guia.setDECLARADO(util.quitaNulo(rs.getString("DECLARADO")));
                        guia.setCOD_VALORACOBRAR(util.quitaNulo(rs.getString("COD_VALORACOBRAR")));
                        guia.setSEABREPAQUETE(util.quitaNulo(rs.getString("SEABREPAQUETE")));
                        guia.setCONTSEG(util.quitaNulo(rs.getString("CONTSEG")));
                        guia.setFECOPE(util.quitaNulo(rs.getString("FECOPE")));
                        guia.setHORAOPE(util.quitaNulo(rs.getString("HORAOPE")));
                        guia.setRECOGEOFICINA(util.quitaNulo(rs.getString("RECOGEOFICINA")));
                        guia.setCAMPO1(util.obtenerCodigo(util.quitaNulo(rs.getString("CAMPO1"))));
                        guia.setCAMPO2(util.obtenerCodigo(util.quitaNulo(rs.getString("CAMPO2"))));
                        guia.setCAMPO3(util.obtenerCodigo(util.quitaNulo(rs.getString("CAMPO3"))));
                        guia.setCAMPO4(util.obtenerCodigo(util.quitaNulo(rs.getString("CAMPO4"))));
                        guia.setCODORIGEN(util.quitaNulo(rs.getString("CODORIGEN")));
                        guia.setCODDESTINO(util.quitaNulo(rs.getString("CODDESTINO")));

                        boolean prepago = guia.getGUIAPREPAGO().equals("S");

                        if (prepago) {
                            sqlDetalle = "SELECT jd.LINEA, jd.PIEZAS, jd.TIPENV, jd.PESO, jd.TARIFA, "
                                    + " bpcd.ID AS IDBOLSONDETALLE, "
                                    + " ccp.ID_BOLSON, ccp.HABER_PIEZAS AS PIEZASRESERVADO, "
                                    + " te.NOMBRE AS DESCRIPCIONENVIO "
                                    + " FROM CUENTA_CORRIENTE_PREPAGO ccp "
                                    + " INNER JOIN JGUIASDETALLE jd "
                                    + " ON jd.NOGUIA = ccp.NOGUIA "
                                    + " INNER JOIN BOLSON_PREPAGO_CONTRATOS_DETALLE bpcd "
                                    + " ON bpcd.ID_BOLSON = ccp.ID_BOLSON "
                                    + " AND bpcd.TIPO_PIEZA = jd.TIPENV "
                                    + " INNER JOIN TRFENVIOS te "
                                    + " ON te.CODIGO = jd.TIPENV "
                                    + " WHERE ccp.NOGUIA = ? "
                                    + " AND ccp.OPERACION = ? ";
                        }
                        System.out.println("query a ejecutar: " + sqlDetalle);
                        try (PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {
                            psDet.setString(1, datos.getNoguia());
                            if (prepago) {
                                psDet.setString(2, operacion);
                            }
                            List<E_DetalleLinea> detalles = new ArrayList<E_DetalleLinea>();

                            try (ResultSet rsDet = psDet.executeQuery()) {
                                while (rsDet.next()) {
                                    E_DetalleLinea det = new E_DetalleLinea();
                                    det.setLINEA(util.quitaNulo(rsDet.getString("LINEA")));
                                    det.setPIEZAS(rsDet.getInt("PIEZAS"));
                                    det.setTIPOENVIO(util.quitaNulo(rsDet.getString("TIPENV")));
                                    det.setPESO(util.quitaNulo(rsDet.getString("PESO")));
                                    det.setTARIFA(util.quitaNulo(rsDet.getString("TARIFA")));
                                    det.setDESCRIPCIONENVIO(util.quitaNulo(rsDet.getString("DESCRIPCIONENVIO")));
                                    if (prepago) {
                                        det.setIDBOLSON(util.quitaNulo(rsDet.getString("ID_BOLSON")));
                                        det.setIDBOLSONDETALLE(util.quitaNulo(rsDet.getString("IDBOLSONDETALLE")));
                                        det.setPIEZASRESERVADO(util.quitaNulo(rsDet.getString("PIEZASRESERVADO")));
                                    }
                                    detalles.add(det);
                                }
                            }
                            guia.setDETALLE(detalles);
                        }
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
        noguia = util.quitaNulo(noguia);
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
            String impreso = "";
            String upQuery = "";
            boolean ejecutarUpdate = true;

            for (E_ImpresionSIG dato : datos) {
                if (dato.getNOGUIA() != null && !dato.getNOGUIA().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement("SELECT IDSERVICIO, IMPRESO FROM JGUIAS WHERE NOGUIA = ? ")) {
                        ps.setString(1, dato.getNOGUIA());
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                E_Servicio servicio = new E_Servicio();
                                servicio.setIDSERVICIO(util.quitaNulo(rs.getString("IDSERVICIO")));
                                impreso = util.quitaNulo(rs.getString("IMPRESO"));
                                resultado.add(servicio);
                            }
                        }
                    }
                }
            }

            if (impreso.isEmpty()) {
                System.out.println("No hay estado IMPRESO válido, no se ejecuta UPDATE");
                con.rollback();
                return null;
            }

            if (impreso.equalsIgnoreCase("D")) {
                upQuery = "UPDATE JGUIAS SET IMPRESO = 'R' WHERE NOGUIA = ?";
            } else if (impreso.equalsIgnoreCase("G")) {
                upQuery = "UPDATE JGUIAS SET IMPRESO = 'S' WHERE NOGUIA = ?";
            } else if (impreso.equalsIgnoreCase("R") || impreso.equalsIgnoreCase("S")) {
                ejecutarUpdate = false;
            } else {
                con.rollback();
                return null;
            }

            if (ejecutarUpdate) {
                System.out.println("Impreso - " + impreso + " - descargaRotulador");
                try (PreparedStatement psUpdate = con.prepareStatement(upQuery)) {
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
            } else {
                System.out.println("Impreso [" + impreso + "] se omite UPDATE descargaRotulador");
            }
            con.commit();
            return resultado;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String validaImpresion(List<E_ImpresionSIG> datos) {
        boolean isPrinted = false;
        String impreso = "";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement st = con.prepareStatement(" SELECT ISNULL(IMPRESO, 'N') IMPRESO FROM JGUIAS WHERE NOGUIA = ? ")) {
            for (E_ImpresionSIG dato : datos) {
                st.setString(1, dato.getNOGUIA());

                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        impreso = util.quitaNulo(rs.getString("IMPRESO"));
                        if (impreso.equalsIgnoreCase("S") || impreso.equalsIgnoreCase("R")) {
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

    public String validaRecoleccion(List<E_ImpresionSIG> datos) {
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
                        System.out.println("isDelivered " + isDelivered);
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

    public String validaRecoleccion(Connection con, List<E_ImpresionSIG> datos) {
        boolean isDelivered = false;

        try (PreparedStatement st = con.prepareStatement(" "
                + " SELECT J.NOGUIA "
                + " FROM JGUIAS J "
                + " WHERE J.NOGUIA = ? "
                + " AND NOT EXISTS("
                + "     SELECT G.NOGUIA "
                + "     FROM GUIAS G "
                + "     WHERE G.NOGUIA = ? ) ")) {
            for (E_ImpresionSIG dato : datos) {
                st.setString(1, dato.getNOGUIA());
                st.setString(2, dato.getNOGUIA());

                try (ResultSet rs = st.executeQuery()) {
                    if (!rs.isBeforeFirst()) {
                        System.out.println(rs.isBeforeFirst());
                        isDelivered = true;
                        System.out.println("isDelivered " + isDelivered);
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

    public E_Guia obtieneDatosGuia(Connection con, String noguia) {
        if (!util.quitaNulo(noguia).isEmpty()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT NOGUIA, CODCOB FROM JGUIAS WHERE NOGUIA = ? ")) {
                ps.setString(1, noguia);
                try (ResultSet rs = ps.executeQuery()) {
                    E_Guia guia = new E_Guia();
                    while (rs.next()) {
                        guia.setNOGUIA(util.quitaNulo(rs.getString("NOGUIA")));
                        guia.setCODCOB(util.quitaNulo(rs.getString("CODCOB")));
                    }
                    return guia;
                }
            } catch (Exception e) {
                Logger.getLogger(D_Guia.class.getName()).log(Level.SEVERE, "Error al obtener datos de la guía.", e);
            }
        }
        return null;
    }

    /**
     * Mod ESTEFANIECM
     *
     * @param credenciales
     * @param usuario
     * @return
     */
    public E_RespuestaGuia obtenerGuiasEliminar(E_Credenciales credenciales, E_Facusuario usuario) {

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(""
                        + " SELECT  J.NOGUIA, "
                        + " J.FECHA, "
                        + " J.NOMDES, "
                        + " J.DIRDES, "
                        + " J.COMPLEMENTODIRDES, "
                        + " J.DESCRENV, "
                        + " J.CODCOB, "
                        + " J.TELDES, "
                        + " J.CONTACTO, "
                        + " J.MNCPDES, "
                        + " ISNULL(NULLIF(F.LPREPAGADA, ''), 'N') LPREPAGADA "
                        + " FROM JGUIAS J "
                        + " INNER JOIN FACCLIENTES F ON J.CODCOB = F.CODIGO "
                        + " WHERE J.FECHA >= ? AND J.FECHA < DATEADD(day, 1, ?) "
                        + "	AND F.PADRE = ? "
                        + "	AND J.NOGUIA LIKE ? "
                        + "	AND NOT EXISTS (SELECT 1 FROM GUIAS GS WHERE GS.NOGUIA = J.NOGUIA) "
                        + " ORDER BY J.FECHA DESC ")) {
            ps.setString(1, credenciales.getFechaInicio());
            ps.setString(2, credenciales.getFechaFinal());
            ps.setString(3, usuario.getPADRE());
            ps.setString(4, usuario.getUEGUIAS() + "%");

            List<E_Guia> datosGuia = new ArrayList<>();

            Map<String, E_Guia> mapaGuias = new HashMap<>();
            List<String> guiasPrepago = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Guia guia = new E_Guia();
                    guia.setNOGUIA(util.quitaNulo(rs.getString("NOGUIA")));
                    guia.setFECHA(util.quitaNulo(rs.getString("FECHA")));
                    guia.setNOMDES(util.quitaNulo(rs.getString("NOMDES")));
                    guia.setDIRDES(util.quitaNulo(rs.getString("DIRDES")));
                    guia.setCOMPLEMENTODIRDES(util.quitaNulo(rs.getString("COMPLEMENTODIRDES")));
                    guia.setDESCRENV(util.quitaNulo(rs.getString("DESCRENV")));
                    guia.setCODCOB(util.quitaNulo(rs.getString("CODCOB")));
                    guia.setTELDES(util.quitaNulo(rs.getString("TELDES")));
                    guia.setCONTACTO(util.quitaNulo(rs.getString("CONTACTO")));
                    guia.setMNCPDES(util.quitaNulo(rs.getString("MNCPDES")));
                    String lp = util.quitaNulo(rs.getString("LPREPAGADA"));
                    System.out.println("lPrepagada? " + lp);
                    guia.setGUIAPREPAGO(lp);

                    if ("S".equals(lp)) {
                        guiasPrepago.add(guia.getNOGUIA());
                    }

                    mapaGuias.put(guia.getNOGUIA(), guia);

                }

                if (!guiasPrepago.isEmpty()) {
                    String inSql = String.join(",", Collections.nCopies(guiasPrepago.size(), "?"));

                    String sqlDetalle
                            = "SELECT NOGUIA, PIEZAS, TIPENV "
                            + "FROM JGUIASDETALLE "
                            + "WHERE NOGUIA IN (" + inSql + ") ";

                    try (PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {

                        int i = 1;
                        for (String guia : guiasPrepago) {
                            psDet.setString(i++, guia);
                        }

                        try (ResultSet rsDet = psDet.executeQuery()) {
                            while (rsDet.next()) {
                                E_DetalleLinea det = new E_DetalleLinea();
                                det.setPIEZAS(rsDet.getInt("PIEZAS"));
                                det.setTIPOENVIO(util.quitaNulo(rsDet.getString("TIPENV")));

                                String noguia = util.quitaNulo(rsDet.getString("NOGUIA"));
                                mapaGuias.get(noguia).getDETALLE().add(det);
                            }
                        }
                    }
                }

            }
            datosGuia.addAll(mapaGuias.values());

            if (datosGuia.isEmpty()) {
                return new E_RespuestaGuia("204");
            }

            return new E_RespuestaGuia("200", datosGuia);
        } catch (SQLException e) {
            Logger.getLogger(D_Guia.class.getName()).log(Level.SEVERE, "Error al obtener datos de guías ", e);
        }
        return new E_RespuestaGuia("500");
    }
}
