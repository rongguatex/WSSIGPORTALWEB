package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class D_Depto_Municipios {
    Utils util = new Utils();

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
                        CodigosExistentes.add(util.limpiaStr(rs.getString("COD_DEPTO")));
                        E_Departamento NuevoDepartamento = new E_Departamento();
                        NuevoDepartamento.setCODIGO(util.limpiaStr(rs.getString("COD_DEPTO")));
                        NuevoDepartamento.setNOMBRE(util.limpiaStr(rs.getString("NOM_DEPTO")));
                        NuevoDepartamento.setPAIS(util.limpiaStr(rs.getString("COD_PAIS")));
                        Departamentos.add(NuevoDepartamento);
                        for (E_Departamento departamento : Departamentos) {
                            if (departamento.getCODIGO().equals(util.limpiaStr(rs.getString("COD_DEPTO")))) {
                                E_Municipio NuevoMunicipio = new E_Municipio();
                                NuevoMunicipio.setCODIGO(util.limpiaStr(rs.getString("COD_MUN")));
                                NuevoMunicipio.setNOMBRE(util.limpiaStr(rs.getString("NOM_MUN")));
                                departamento.getMUNICIPIOS().add(NuevoMunicipio);
                               
                            }
                        }
                    } else {
                        for (E_Departamento departamento : Departamentos) {
                            if (departamento.getCODIGO() == rs.getString("COD_DEPTO")) {
                                E_Municipio NuevoMunicipio = new E_Municipio();
                                NuevoMunicipio.setCODIGO(util.limpiaStr(rs.getString("COD_MUN")));
                                NuevoMunicipio.setNOMBRE(util.limpiaStr(rs.getString("NOM_MUN")));
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
}
