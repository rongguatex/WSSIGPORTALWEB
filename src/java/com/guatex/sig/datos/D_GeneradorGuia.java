/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_GeneradorGuia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RGALICIA
 */
public class D_GeneradorGuia {

    /**
     * Obtiene los datos que serviran para crear nueva guía.
     *
     * @param parametros
     * @return
     */
    public E_GeneradorGuia generadorGuia(E_GeneradorGuia parametros) {

        String query = "SELECT ULTIMAMODIFICACION, FECHAELIETI, CORRELMADRESETI, CORRELHIJASETI, UEGUIAS FROM FACUSUARIOS WHERE USUARIO = ?";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, parametros.getUSUARIO());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    parametros.setFECHA_ULTIMA_MODIFICACION(quitaNulo(rs.getString("ULTIMAMODIFICACION")));
                    parametros.setFECHAELIETI(quitaNulo(rs.getString("FECHAELIETI")));
                    parametros.setCORRELMADRESETI(quitaNulo(rs.getString("CORRELMADRESETI")));
                    parametros.setCORRELHIJASETI(quitaNulo(rs.getString("CORRELHIJASETI")));
                    parametros.setUEGUIAS(quitaNulo(rs.getString("UEGUIAS")));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return parametros;
    }

    /**
     * Actualiza el correlativo de la última guía MADRE generada en la tabla
     * FACUSUARIOS
     *
     * @param parametros
     * @return
     */
    public boolean actualizarCorrelativoGuiaMadre(E_GeneradorGuia parametros) {
        String query = "UPDATE FACUSUARIOS SET CORRELMADRESETI = ?, ULTIMAMODIFICACION = GETDATE(), FECHAELIETI = ? WHERE USUARIO = ?";

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, parametros.getGUIAMADRE());
            ps.setString(2, sf.format(new Date()));
            ps.setString(3, parametros.getCODIGO() + "/" + parametros.getUSUARIO());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    /**
     * Actualiza el correlativo de la última guía HIJA generada en la tabla
     * FACUSUARIOS
     *
     * @param parametros
     * @return
     */
    public boolean actualizarCorrelativoGuiaHija(E_GeneradorGuia parametros) {
        String query = "UPDATE FACUSUARIOS SET CORRELHIJASETI = ?, ULTIMAMODIFICACION = GETDATE(), FECHAELIETI = ? WHERE USUARIO = ?";

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, parametros.getGUIAHIJA());
            ps.setString(2, sf.format(new Date()));
            ps.setString(3, parametros.getCODIGO() + "/" + parametros.getUSUARIO());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    /**
     * Método valida que sea un nuevo día para creación de guias madre e hija.
     *
     * @param prefijoGuia
     * @param tipoGuia m = madre; h = hija
     * @return
     */
    public boolean validarNuevoDia(String prefijoGuia, String tipoGuia) {
        String query = "";
        System.out.println("prefijo guia: [" + prefijoGuia + "] tipoGuia [" + tipoGuia + "]");
        if (tipoGuia.equalsIgnoreCase("m")) {
            query = "SELECT TOP 1 NOGUIA FROM JGUIAS WHERE NOGUIA LIKE ? ORDER BY NOGUIA DESC";
        } else if (tipoGuia.equalsIgnoreCase("h")) {
            query = "SELECT TOP 1 HGUIAHIJA FROM JGUIASHIJAS WHERE HGUIAHIJA LIKE ? ORDER BY HGUIAHIJA DESC";
        }
        prefijoGuia = prefijoGuia + "%";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, prefijoGuia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("No es un nuevo día, respuesta: [" + quitaNulo(rs.getString(1)) + "]");
                    return quitaNulo(rs.getString(1)).isEmpty();
                } else {
                    System.out.println("Buenos días señor sol, es un nuevo día.");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean ActualizaUltimaGuiaHijaFacUsuarios(String correlativoGuiaHija, String usuario) {
        System.out.println("ActualizaUltimaGuiaFacUsuarios  hija correlativo: [" + correlativoGuiaHija + "] usuario: [" + usuario + "]");
        String query = "UPDATE FACUSUARIOS SET CORRELHIJASETI = ? WHERE USUARIO = ?";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, correlativoGuiaHija);
            ps.setString(2, usuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * este método nos ayuda a quitar los valores nulos y asignarles valor
     * vacío.
     *
     * @param var
     * @return
     */
    public String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }
}
