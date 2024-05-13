package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Municipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class D_Depto_Municipios {

    public List<E_Departamento> ObtenerDeptosMunicipios() {
        List<E_Departamento> Departamentos = new LinkedList<>();
        List<String> CodigosExistentes = new LinkedList<>();
        final String Query = "SELECT "
                + "DEP.CODIGO AS COD_DEPTO, "
                + "DEP.NOMBRE AS NOM_DEPTO, "
                + "MUN.CODIGO AS COD_MUN, "
                + "MUN.NOMBRE AS NOM_MUN, "
                + " DEP.PAIS AS COD_PAIS "
                + "FROM OPEDEPTOS DEP INNER JOIN OPEMUNI MUN ON MUN.DEPTO = DEP.CODIGO "
                + "ORDER BY DEP.NOMBRE, MUN.NOMBRE";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boolean encontrado = false;
                    for (String cod_depto : CodigosExistentes) {
                        if (cod_depto.equals(rs.getString(1))) {
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        CodigosExistentes.add(rs.getString(1));
                        E_Departamento NuevoDepartamento = new E_Departamento();
                        NuevoDepartamento.setCODIGO(Integer.valueOf(quitaNulo(String.valueOf(rs.getInt("COD_DEPTO")))));
                        NuevoDepartamento.setNOMBRE(quitaNulo(rs.getString("NOM_DEPTO")));
                        NuevoDepartamento.setPAIS(quitaNulo(rs.getString("COD_PAIS")));
                        Departamentos.add(NuevoDepartamento);
                        for (E_Departamento departamento : Departamentos) {
                            if (departamento.getCODIGO() == rs.getInt("COD_DEPTO")) {
                                E_Municipio NuevoMunicipio = new E_Municipio();
                                NuevoMunicipio.setCODIGO(Integer.valueOf(quitaNulo(String.valueOf(rs.getInt("COD_MUN")))));
                                NuevoMunicipio.setNOMBRE(quitaNulo(rs.getString("NOM_MUN")));
                                departamento.getMUNICIPIOS().add(NuevoMunicipio);
                            }
                        }
                    } else {
                        for (E_Departamento departamento : Departamentos) {
                            if (departamento.getCODIGO() == rs.getInt(1)) {
                                E_Municipio NuevoMunicipio = new E_Municipio();
                                NuevoMunicipio.setCODIGO(Integer.valueOf(quitaNulo(String.valueOf(rs.getInt("COD_MUN")))));
                                NuevoMunicipio.setNOMBRE(quitaNulo(rs.getString("NOM_MUN")));
                                departamento.getMUNICIPIOS().add(NuevoMunicipio);
                            }
                        }
                    }
                }
            }
            return Departamentos;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
    
    private String quitaNulo(String var){
        return var == null ? "" : var.trim();
    } 
}
