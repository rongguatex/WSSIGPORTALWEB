/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        return true;
                    } catch (Exception e) {
                        Logger.getLogger(D_Eliminacion.class.getName()).log(Level.SEVERE, "Error al eliminar guias ", e);
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

    public boolean habilitaRotulador(String usuario) {
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(""
                        + "SELECT CODIGOOPCION FROM JUSUARIOSOPCION WHERE USUARIO = ? ")) {
            if (usuario == null && usuario.trim().isEmpty()) {
                return false;
            }
            System.out.println(usuario);
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("CODIGOOPCION: " + rs.getString("CODIGOOPCION"));
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
