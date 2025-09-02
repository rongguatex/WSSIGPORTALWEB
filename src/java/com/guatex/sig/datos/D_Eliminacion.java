/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.utils.Utils;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RGALICIA
 */
public class D_Eliminacion {

    Utils util = new Utils();

    public boolean eliminacionMultipleGuias(Connection con, List<E_ImpresionSIG> datos) {
        boolean exito = false;
        if (con != null && datos != null) {
            for (E_ImpresionSIG dato : datos) {
                if (dato.getNOGUIA() != null && !dato.getNOGUIA().isEmpty()) {
                    try (PreparedStatement psGuimadcl = con.prepareStatement("DELETE FROM GUIMADCL WHERE CODCOB=? AND NOGUIA=?")) {
                        psGuimadcl.setString(1, dato.getCODCOB());
                        psGuimadcl.setString(2, dato.getNOGUIA());
                        psGuimadcl.executeUpdate();
                        try (PreparedStatement psJguias = con.prepareStatement("DELETE FROM JGUIAS WHERE CODCOB=? AND NOGUIA=?")) {
                            psJguias.setString(1, dato.getCODCOB());
                            psJguias.setString(2, dato.getNOGUIA());
                            psJguias.executeUpdate();
                        }

                        try (PreparedStatement psJGuiasDet = con.prepareStatement("DELETE FROM JGUIASDETALLE WHERE NOGUIA=?")) {
                            psJGuiasDet.setString(1, dato.getNOGUIA());
                            psJGuiasDet.executeUpdate();
                        }

                        try (PreparedStatement psJGuiasHijas = con.prepareStatement("DELETE FROM JGUIASHIJAS WHERE HNOGUIA=?")) {
                            psJGuiasHijas.setString(1, dato.getNOGUIA());
                            psJGuiasHijas.executeUpdate();
                        }
                        exito = true;
                        Logger.getLogger(D_Eliminacion.class.getName()).log(Level.INFO, "Eliminaci\u00f3n exitosa gu\u00eda: {0}", dato.getNOGUIA());
                    } catch (Exception e) {
                        Logger.getLogger(D_Eliminacion.class.getName()).log(Level.SEVERE, "Error al eliminar guias ", e);
                    }
                }
            }
        }
        return exito;
    }

    public boolean insertaBitacoraEliminacion(Connection con, E_Credenciales credenciales, List<E_ImpresionSIG> datos) {
        if (con != null && datos != null) {
            for (E_ImpresionSIG dato : datos) {
                if (dato.getNOGUIA() != null && !dato.getNOGUIA().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement(""
                            + "INSERT INTO BITACORAGUIA "
                            + "            (fecha, "
                            + "             usuario, "
                            + "             noguiant, "
                            + "             noguianu, "
                            + "             codcob, "
                            + "             evento) "
                            + "VALUES      (Sysdatetime(), "
                            + "             ?, "
                            + "             ?, "
                            + "             ?, "
                            + "             ?, "
                            + "             ? ) ")) {
                        ps.setString(1, util.quitaNulo(credenciales.getUsuario()));
                        ps.setString(2, util.quitaNulo(dato.getNOGUIA()));
                        ps.setString(3, util.quitaNulo(dato.getNOGUIA()));
                        ps.setString(4, util.quitaNulo(credenciales.getCodcob()));
                        ps.setString(5, "ELIMINACION");
                        return ps.executeUpdate() > 0;
                    } catch (Exception e) {
                        Logger.getLogger(D_Eliminacion.class.getName()).log(Level.SEVERE, "Error al insertar en bitacora de eliminación de guias ", e);
                        if (con != null) {
                            try {
                                con.rollback();
                            } catch (SQLException exrollback) {
                                Logger.getLogger(D_Eliminacion.class.getName()).log(Level.SEVERE, "Error al realizar rollback.", exrollback);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean cambiarEstado(List<E_ImpresionSIG> datos, E_Credenciales credenciales) {
        boolean respuesta = false;

        if (datos != null && !datos.isEmpty()) {
            try (Connection con = new Conexion().AbrirConexion()) {
                con.setAutoCommit(false);

                String query = "UPDATE SIG_IMPRESION SET ESTADO = 'E' WHERE NOGUIA = ? AND CODIGO = ? AND USUARIO = ?";

                try (PreparedStatement ps = con.prepareStatement(query)) {

                    for (E_ImpresionSIG dato : datos) {
                        System.out.println("-> A actualizar guía: [" + dato.getNOGUIA() + "] - Código: [" + credenciales.getPadre() + "] - Usuario: [" + credenciales.getUsuario() + "] ");
                        ps.setString(1, dato.getNOGUIA().trim());
                        ps.setString(2, credenciales.getPadre().trim());
                        ps.setString(3, credenciales.getUsuario().trim());
                        ps.addBatch();
                    }

                    ps.executeBatch();

//                    int[] updateResults = ps.executeBatch();
//                    for (int arr : updateResults) {
//                        if (arr == PreparedStatement.EXECUTE_FAILED || arr <= 0) {
//                            con.rollback();
//                            respuesta = false;
//                            System.out.println("Error en batch de Update SIG_IMPRESION, se realiza rollback");
//                        }
//                    }
                    con.commit();
                    respuesta = true;

                } catch (BatchUpdateException bue) {
                    System.out.println("BatchUpdateException: " + bue.getMessage());
                    System.out.println("SQLState: " + bue.getSQLState());
                    System.out.println("ErrorCode: " + bue.getErrorCode());
                    System.out.println("UpdateCounts: " + Arrays.toString(bue.getUpdateCounts()));
                    bue.printStackTrace();
                    
                    con.rollback();
                    respuesta = false;

                } catch (SQLException sqlException) {
                    System.out.println("Error e ingreso al sqlException");
                    sqlException.printStackTrace(System.err);
                    if (con != null) {
                        try {
                            System.out.println("Se realiza el rollback");
                            con.rollback();
                        } catch (SQLException rollbackException) {
                            System.out.println("Error e ingreso al rollbackException");
                            rollbackException.printStackTrace(System.err);
                        }
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al cambiar de estado SIG_IMPRESION al exception");
                e.printStackTrace(System.err);
            }

        }

        return respuesta;
    }

    public boolean habilitaRotulador(String usuario) {
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(""
                        + "SELECT CODIGOOPCION FROM JUSUARIOSOPCION WHERE USUARIO = ? ")) {
            if (usuario == null && usuario.trim().isEmpty()) {
                return false;
            }

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (util.quitaNulo(rs.getString("CODIGOOPCION")).equalsIgnoreCase("HABILITAROTULADORCL")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
