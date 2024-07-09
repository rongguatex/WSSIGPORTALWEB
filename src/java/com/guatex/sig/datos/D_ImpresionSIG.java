/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.utils.ConvertidorXML;
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
public class D_ImpresionSIG {

    Utils util = new Utils();

    /**
     * Método que inserta en SIG_IMPRESION y actualiza en JGUIAS el estado de
     * IMPRESO. Estado N en SIG_IMPRESION = guía no ha sido impresa. Impreso P
     * en JGUIAS =
     *
     * @param datos
     * @return
     */
    public String insertaImpresionSIG(List<E_ImpresionSIG> datos) {
        if (datos != null && datos.size() > 0) {
            try (Connection con = new Conexion().AbrirConexion()) {
                con.setAutoCommit(false);

                boolean isDelivered = false;

                try (PreparedStatement st = con.prepareStatement(" SELECT ISNULL(IMPRESO, 'N') IMPRESO FROM JGUIAS WHERE NOGUIA = ? ")) {
                    for (E_ImpresionSIG dato : datos) {
                        st.setString(1, dato.getNOGUIA());

                        try (ResultSet rs = st.executeQuery()) {
                            while (rs.next()) {
                                if (util.limpiaStr(rs.getString("IMPRESO")).equalsIgnoreCase("S")) {
                                    isDelivered = true;
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.err);
                    return new ConvertidorXML().InternalServerError();
                }

                if (isDelivered) {
                    return new ConvertidorXML().IsDerivered();
                }

                try (PreparedStatement insertPS = con.prepareStatement("INSERT INTO SIG_IMPRESION (NOGUIA,  ESTADO,  CODIGO, USUARIO) VALUES (?,'N',?,?) ");
                        PreparedStatement updatePS = con.prepareStatement("UPDATE JGUIAS SET IMPRESO = 'S' WHERE NOGUIA = ?")) {

                    //ciclo para prepatar batch para inserts
                    for (E_ImpresionSIG dato : datos) {
                        insertPS.setString(1, dato.getNOGUIA());
                        insertPS.setString(2, dato.getCODCOB());
                        insertPS.setString(3, dato.getUSUARIO());
                        insertPS.addBatch();
                    }

                    //ciclo para prepatar batch para update
                    for (E_ImpresionSIG dato : datos) {
                        updatePS.setString(1, dato.getNOGUIA());
                        updatePS.addBatch();
                    }

                    int[] insertResults = insertPS.executeBatch();
                    int[] updateResults = updatePS.executeBatch();

                    for (int arr : insertResults) {
                        if (insertResults[arr - 1] == PreparedStatement.EXECUTE_FAILED || insertResults[arr - 1] <= 0) {
                            con.rollback();
                            System.out.println("Error en batch de INSERT, se realiza rollback");
                            return new ConvertidorXML().BadRequest();
                        }
                    }

                    for (int arr : updateResults) {
                        if (insertResults[arr - 1] == PreparedStatement.EXECUTE_FAILED || updateResults[arr - 1] <= 0) {
                            con.rollback();
                            System.out.println("Error en batch de UPDATE, se realiza rollback");
                            return new ConvertidorXML().BadRequest();
                        }
                    }

                    con.commit();
                    return new ConvertidorXML().OK();

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
                System.out.println("ocurrio un error e ingresa al exception");
                e.printStackTrace(System.err);
            }
        }
        return new ConvertidorXML().InternalServerError();
    }

    public String insertaReimpresion(List<E_ImpresionSIG> datos) {
        if (datos != null && datos.size() > 0) {
            try (Connection con = new Conexion().AbrirConexion()) {
                con.setAutoCommit(false);
                
                boolean isDelivered = false;

                try (PreparedStatement st = con.prepareStatement(" SELECT ISNULL(IMPRESO, 'N') IMPRESO FROM JGUIAS WHERE NOGUIA = ? ")) {
                    for (E_ImpresionSIG dato : datos) {
                        st.setString(1, dato.getNOGUIA());

                        try (ResultSet rs = st.executeQuery()) {
                            while (rs.next()) {
                                if (util.limpiaStr(rs.getString("IMPRESO")).equalsIgnoreCase("S")) {
                                    isDelivered = true;
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.err);
                    return new ConvertidorXML().InternalServerError();
                }

                if (isDelivered) {
                    return new ConvertidorXML().IsDerivered();
                }

                try (PreparedStatement insertPS
                        = con.prepareStatement("INSERT INTO SIG_IMPRESION (NOGUIA,  ESTADO,  CODIGO, USUARIO) VALUES (?,'N',?,?) ")) {

                    //ciclo para prepatar batch para inserts
                    for (E_ImpresionSIG dato : datos) {
                        insertPS.setString(1, dato.getNOGUIA());
                        insertPS.setString(2, dato.getCODCOB());
                        insertPS.setString(3, dato.getUSUARIO());
                        insertPS.addBatch();
                    }

                    int[] insertResults = insertPS.executeBatch();

                    for (int arr : insertResults) {
                        if (insertResults[arr - 1] == PreparedStatement.EXECUTE_FAILED || insertResults[arr - 1] <= 0) {
                            con.rollback();
                            System.out.println("Error en batch de INSERT, se realiza rollback");
                            return new ConvertidorXML().BadRequest();
                        }
                    }

                    con.commit();
                    return new ConvertidorXML().OK();

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
                System.out.println("ocurrio un error e ingresa al exception");
                e.printStackTrace(System.err);
            }
        }
        return new ConvertidorXML().InternalServerError();
    }
}
