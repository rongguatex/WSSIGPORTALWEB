package com.guatex.sig.datos;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_respuestaClientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class D_Clientes {

    /**
     * Método que retorna todos los clientes que en sus campos CODIGO, NOMBRE,
     * CONTACTO O TELEFONO contienen los datos recibidos .
     *
     * @param cliente - XML
     * @return Listado con información de los clientes
     */
    public E_respuestaClientes ObtenerListadoClientes(E_Cliente cliente) {
        System.out.println("----> Obtener  Listado Clientes");

        List<E_Cliente> clientes = new LinkedList<>();
        String Query = "";

        if (cliente.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (cliente.getCODCOB().equalsIgnoreCase(cliente.getPADRE())) {
                Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA, "
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                        + "WHERE ISNULL(CODIGO,'') LIKE ? AND ISNULL(C_NOMBRE,'') LIKE ? AND ISNULL(C_CONTACTO,'') LIKE ? AND ISNULL(C_TEL,'') LIKE ? "
                        + "AND PADRE = ? ";
            } else {
                Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA,"
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                        + "WHERE ISNULL(CODIGO,'') LIKE ? AND ISNULL(C_NOMBRE,'') LIKE ? AND ISNULL(C_CONTACTO,'') LIKE ? AND ISNULL(C_TEL,'') LIKE ? "
                        + "AND PADRE = ? AND CODCOB = ? ";
            }
        } else {
            Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                    + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA,"
                    + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                    + "WHERE ISNULL(CODIGO,'') LIKE ? AND ISNULL(C_NOMBRE,'') LIKE ? AND ISNULL(C_CONTACTO,'') LIKE ? AND ISNULL(C_TEL,'') LIKE ? "
                    + "AND CODCOB = ? ";
        }

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, "%" + cliente.getCODIGO() + "%");
            ps.setString(2, "%" + quitaNulo(cliente.getNOMBRE()) + "%");
            ps.setString(3, "%" + quitaNulo(cliente.getCONTACTO()) + "%");
            ps.setString(4, "%" + quitaNulo(cliente.getTELEFONO()) + "%");

            if (cliente.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (cliente.getCODCOB().equalsIgnoreCase(cliente.getPADRE())) {
                    ps.setString(5, quitaNulo(cliente.getPADRE()));
                } else {
                    ps.setString(5, quitaNulo(cliente.getPADRE()));
                    ps.setString(6, quitaNulo(cliente.getCODCOB()));
                }
            } else {
                ps.setString(5, quitaNulo(cliente.getCODCOB()));
            }
            System.out.println("sql query [" + Query + "]");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Cliente NuevoCliente = new E_Cliente();
                    NuevoCliente.setCODIGO(quitaNulo(rs.getString("CODIGO")));
                    NuevoCliente.setCODCOB(quitaNulo(rs.getString("CODCOB")));
                    NuevoCliente.setPADRE(quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setNOMBRE(quitaNulo(rs.getString("NOMBRE")));
                    NuevoCliente.setCONTACTO(quitaNulo(rs.getString("CONTACTO")));
                    NuevoCliente.setDIRECCION(quitaNulo(rs.getString("DIRECCION")));
                    NuevoCliente.setUBICACION(quitaNulo(rs.getString("UBICACION")));
                    NuevoCliente.setPUNTO(quitaNulo(rs.getString("PUNTO")));
                    NuevoCliente.setCORREO(quitaNulo(rs.getString("CORREO")));
                    NuevoCliente.setNIT(quitaNulo(rs.getString("NIT")));
                    NuevoCliente.setTELEFONO(quitaNulo(rs.getString("TELEFONO")));
                    NuevoCliente.setRECOGEOFICINA(quitaNulo(rs.getString("RECOGEOFICINA")));
                    if (rs.getString("CAMPO1") != null) {
                        if (quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            NuevoCliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")).substring(quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            NuevoCliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")).substring(quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            NuevoCliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")).substring(quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            NuevoCliente.setCAMPO4(quitaNulo(rs.getString("CAMPO4")).substring(quitaNulo(rs.getString("CAMPO4")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO4")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO4(quitaNulo(rs.getString("CAMPO4")));
                    }
                    NuevoCliente.setPADRE(quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(NuevoCliente.getPUNTO(), NuevoCliente.getUBICACION()));
                    List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();
                    if (departamentos != null) {
                        for (E_Departamento depto : departamentos) {
                            if (depto.getNOMBRE().equals(NuevoCliente.getCOBERTURA().getDEPARTAMENTO())) {
                                NuevoCliente.setDEPARTAMENTO(depto);
                                for (E_Municipio muni : depto.getMUNICIPIOS()) {
                                    if (muni.getNOMBRE().equals(NuevoCliente.getCOBERTURA().getMUNICIPIO())) {
                                        NuevoCliente.setMUNICIPIO(muni);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    clientes.add(NuevoCliente);
                }

                if (!clientes.isEmpty()) {
                    return new E_respuestaClientes("200", clientes);
                } else {
                    return new E_respuestaClientes("204", clientes);
                }

            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return new E_respuestaClientes("500", clientes);
        }
    }

    /**
     * Método que busca toda la información del cliente por CODIGO
     *
     * @param cliente - XML
     * @return Información del cliente
     */
    public E_respuestaClientes ObtenerCliente(E_Cliente cliente) {
        System.out.println("----> Obtener Cliente");

        List<E_Cliente> datosCliente = new LinkedList<>();
        String Query = "";

        if (cliente.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (cliente.getCODCOB().equalsIgnoreCase(cliente.getPADRE())) {
                Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA, "
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                        + "WHERE ISNULL(CODIGO,'') = ? "
                        + "AND PADRE = ? ";
            } else {
                Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA,"
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                        + "WHERE ISNULL(CODIGO,'') = ? "
                        + "AND PADRE = ? AND CODCOB = ? ";
            }
        } else {
            Query = "SELECT  CODIGO, CODCOB, PADRE, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                    + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, RECOGEOFICINA,"
                    + "CAMPO1, CAMPO2, CAMPO3, CAMPO4 FROM FACCLICLIENTES "
                    + "WHERE ISNULL(CODIGO,'') = ?  "
                    + "AND CODCOB = ? ";
        }

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, quitaNulo(cliente.getCODIGO()));

            if (cliente.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (cliente.getCODCOB().equalsIgnoreCase(quitaNulo(cliente.getPADRE()))) {
                    ps.setString(2, quitaNulo(cliente.getPADRE()));
                } else {
                    ps.setString(2, quitaNulo(cliente.getPADRE()));
                    ps.setString(3, quitaNulo(cliente.getCODCOB()));
                }
            } else {
                ps.setString(2, quitaNulo(cliente.getCODCOB()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    E_Cliente NuevoCliente = new E_Cliente();
                    NuevoCliente.setCODIGO(quitaNulo(rs.getString("CODIGO")));
                    NuevoCliente.setCODCOB(quitaNulo(rs.getString("CODCOB")));
                    NuevoCliente.setPADRE(quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setNOMBRE(quitaNulo(rs.getString("NOMBRE")));
                    NuevoCliente.setCONTACTO(quitaNulo(rs.getString("CONTACTO")));
                    NuevoCliente.setDIRECCION(quitaNulo(rs.getString("DIRECCION")));
                    NuevoCliente.setUBICACION(quitaNulo(rs.getString("UBICACION")));
                    NuevoCliente.setPUNTO(quitaNulo(rs.getString("PUNTO")));
                    NuevoCliente.setCORREO(quitaNulo(rs.getString("CORREO")));
                    NuevoCliente.setNIT(quitaNulo(rs.getString("NIT")));
                    NuevoCliente.setTELEFONO(quitaNulo(rs.getString("TELEFONO")));
                    NuevoCliente.setRECOGEOFICINA(quitaNulo(rs.getString("RECOGEOFICINA")));

                    if (rs.getString("CAMPO1") != null) {
                        if (quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            NuevoCliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")).substring(quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            NuevoCliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")).substring(quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            NuevoCliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")).substring(quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            NuevoCliente.setCAMPO4(rs.getString("CAMPO4").substring(rs.getString("CAMPO4").indexOf("/") + 1, rs.getString("CAMPO4").length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO4(quitaNulo(rs.getString("CAMPO4")));
                    }
                    NuevoCliente.setPADRE(quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(NuevoCliente.getPUNTO(), NuevoCliente.getUBICACION()));

                    List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();
                    if (departamentos != null) {
                        for (E_Departamento depto : departamentos) {
                            if (depto.getNOMBRE().equals(NuevoCliente.getCOBERTURA().getDEPARTAMENTO())) {
                                NuevoCliente.setDEPARTAMENTO(depto);
                                for (E_Municipio muni : depto.getMUNICIPIOS()) {
                                    if (muni.getNOMBRE().equals(NuevoCliente.getCOBERTURA().getMUNICIPIO())) {
                                        NuevoCliente.setMUNICIPIO(muni);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    datosCliente.add(NuevoCliente);
                }
                if (!datosCliente.isEmpty()) {
                    return new E_respuestaClientes("200", datosCliente);
                } else {
                    return new E_respuestaClientes("204", datosCliente);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return new E_respuestaClientes("500", datosCliente);
        }
    }

    public String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }

}
