package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.utils.ConvertirDiasAFrecuencia;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    PuntoCobertura.setCODIGOPUNTO(Utils.quitaNulo(rs.getString("CODIGO")));
                    PuntoCobertura.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    PuntoCobertura.setUBICACION(Utils.quitaNulo(rs.getString("UBIC")));
                    PuntoCobertura.setDEPARTAMENTO(Utils.quitaNulo(rs.getString("DEPTO")));
                    PuntoCobertura.setMUNICIPIO(Utils.quitaNulo(rs.getString("MUNI")));
                    if ("1".equals(Utils.quitaNulo(rs.getString("L")))) {
                        PuntoCobertura.setLUNES('L');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("M")))) {
                        PuntoCobertura.setMARTES('M');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("Mi")))) {
                        PuntoCobertura.setMIERCOLES('M');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("J")))) {
                        PuntoCobertura.setJUEVES('J');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("V")))) {
                        PuntoCobertura.setVIERNES('V');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("S")))) {
                        PuntoCobertura.setSABADO('S');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("RECOGEOFICINA")))) {
                        PuntoCobertura.setRECOGEOFICINA(true);
                    } else {
                        PuntoCobertura.setRECOGEOFICINA(false);
                    }
                    String frecuencia = new ConvertirDiasAFrecuencia().Convertir(1, "", PuntoCobertura);
                    PuntoCobertura.setFRECUENCIA(frecuencia);
                }
                return PuntoCobertura;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
    
    public E_PuntoCobertura BuscarUbicacionEspecifica(Connection con, String Punto, String Ubicacion) {
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

        try (PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, Punto);
            ps.setString(2, Ubicacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PuntoCobertura.setCODIGOPUNTO(Utils.quitaNulo(rs.getString("CODIGO")));
                    PuntoCobertura.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    PuntoCobertura.setUBICACION(Utils.quitaNulo(rs.getString("UBIC")));
                    PuntoCobertura.setDEPARTAMENTO(Utils.quitaNulo(rs.getString("DEPTO")));
                    PuntoCobertura.setMUNICIPIO(Utils.quitaNulo(rs.getString("MUNI")));
                    if ("1".equals(Utils.quitaNulo(rs.getString("L")))) {
                        PuntoCobertura.setLUNES('L');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("M")))) {
                        PuntoCobertura.setMARTES('M');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("Mi")))) {
                        PuntoCobertura.setMIERCOLES('M');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("J")))) {
                        PuntoCobertura.setJUEVES('J');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("V")))) {
                        PuntoCobertura.setVIERNES('V');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("S")))) {
                        PuntoCobertura.setSABADO('S');
                    }
                    if ("1".equals(Utils.quitaNulo(rs.getString("RECOGEOFICINA")))) {
                        PuntoCobertura.setRECOGEOFICINA(true);
                    } else {
                        PuntoCobertura.setRECOGEOFICINA(false);
                    }
                    String frecuencia = new ConvertirDiasAFrecuencia().Convertir(1, "", PuntoCobertura);
                    PuntoCobertura.setFRECUENCIA(frecuencia);
                }
                return PuntoCobertura;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public E_Cliente obtengoPuntosCobertura(E_Cliente cliente) {

        String codigoPunto = cliente.getCOBERTURA().getCODIGOPUNTO();
        if (Utils.quitaNulo(codigoPunto).isEmpty()) {
            return null;
        }

        String query = " SELECT "
                + "                 PUNTODECOBERTURA,  "
                + "                 NOMBRE  "
                + "             FROM TRFMUNICIPIOS "
                + "             WHERE CODIGO = ?  ";

        try (PreparedStatement ps = new Conexion().AbrirConexion().prepareStatement(query)) {
            ps.setString(1, codigoPunto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente.getCOBERTURA().setPUNTO(Utils.quitaNulo(rs.getString("PUNTODECOBERTURA")));
                    cliente.getCOBERTURA().setMUNICIPIO(Utils.quitaNulo(rs.getString("NOMBRE")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error: ", e);
        }
        return cliente;
    }
}
