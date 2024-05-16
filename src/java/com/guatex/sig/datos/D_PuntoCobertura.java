package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.utils.ConvertirDiasAFrecuencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class D_PuntoCobertura {

    public E_PuntoCobertura BuscarUbicacionEspecifica(String Punto, String Ubicacion) {
        E_PuntoCobertura PuntoCobertura = new E_PuntoCobertura();
        String Query = "SELECT "
                + "TRFMUNI.codigo AS CODIGO,  "
                + "TRFMUNI.puntodecobertura AS PUNTO, "
                + "TRFMUNI.nombre AS UBIC, "
                + "ISNULL(DEPTO.NOMBRE,' ') DEPTO, "
                + "ISNULL(MUNI.NOMBRE,' ') MUNI, "
                + "ISNULL(TRFMUNI.D1, '0') L, "
                + "ISNULL(TRFMUNI.D2, '0') M, "
                + "ISNULL(TRFMUNI.D3, '0') Mi, "
                + "ISNULL(TRFMUNI.D4, '0') J, "
                + "ISNULL(TRFMUNI.D5, '0') V,  "
                + "ISNULL(TRFMUNI.D6, '0') S, "
                + "ISNULL(TRFMUNI.RECOGEOFICINA, '0') RECOGEOFICINA "
                + "FROM TRFMUNICIPIOS TRFMUNI "
                + "INNER JOIN OPEMUNI MUNI ON MUNI.CODIGO = TRFMUNI.MUNI  AND MUNI.DEPTO = TRFMUNI.DEPTO "
                + "INNER JOIN OPEDEPTOS DEPTO ON DEPTO.CODIGO = TRFMUNI.DEPTO "
                + "WHERE TRFMUNI.PUNTODECOBERTURA = ? AND TRFMUNI.NOMBRE = ?";

        try (Connection con = new Conexion().AbrirConexion();
            PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, Punto);
            ps.setString(2, Ubicacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PuntoCobertura.setCODIGOPUNTO(quitaNulo(rs.getString("CODIGO")));
                    PuntoCobertura.setPUNTO(quitaNulo(rs.getString("PUNTO")));
                    PuntoCobertura.setUBICACION(quitaNulo(rs.getString("UBIC")));
                    PuntoCobertura.setDEPARTAMENTO(quitaNulo(rs.getString("DEPTO")));
                    PuntoCobertura.setMUNICIPIO(quitaNulo(rs.getString("MUNI")));
                    if ("1".equals(quitaNulo(rs.getString("L")))) {
                        PuntoCobertura.setLUNES('L');
                    }
                    if ("1".equals(quitaNulo(rs.getString("M")))) {
                        PuntoCobertura.setMARTES('M');
                    }
                    if ("1".equals(quitaNulo(rs.getString("Mi")))) {
                        PuntoCobertura.setMIERCOLES('M');
                    }
                    if ("1".equals(quitaNulo(rs.getString("J")))) {
                        PuntoCobertura.setJUEVES('J');
                    }
                    if ("1".equals(quitaNulo(rs.getString("V")))) {
                        PuntoCobertura.setVIERNES('V');
                    }
                    if ("1".equals(quitaNulo(rs.getString("S")))) {
                        PuntoCobertura.setSABADO('S');
                    }
                    if ("1".equals(quitaNulo(rs.getString("RECOGEOFICINA")))) {
                        PuntoCobertura.setRECOGEOFICINA(true);
                    }else{ PuntoCobertura.setRECOGEOFICINA(false);}
                    String frecuencia = new ConvertirDiasAFrecuencia().Convertir(1, "", PuntoCobertura);
                    PuntoCobertura.setFRECUENCIA(frecuencia);
                }
                return PuntoCobertura;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }

    }

    public String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }
    
}
