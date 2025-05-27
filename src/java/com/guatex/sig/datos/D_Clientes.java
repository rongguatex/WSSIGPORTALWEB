package com.guatex.sig.datos;

import com.guatex.sig.entidades.EWSSIGCLIENTES;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class D_Clientes {

    /**
     * Método que busca toda la información del cliente por CODIGO.
     *
     * @param params
     * @param credenciales
     * @return Información del cliente
     */
    public E_respuestaClientes ObtenerCliente(E_FacCliente params, E_Credenciales credenciales) {
        String Query;

        if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                Query = " SELECT "
                        + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                        + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                        + " FROM FACCLICLIENTES "
                        + " WHERE   CODIGO = ? "
                        + " AND         PADRE = ?";
            } else {
                Query = " SELECT "
                        + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                        + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                        + " FROM FACCLICLIENTES "
                        + " WHERE   CODIGO = ? "
                        + " AND         PADRE = ?  "
                        + " AND         CODCOB = ?";
            }
        } else {
            Query = "SELECT "
                    + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                    + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                    + " FROM FACCLICLIENTES "
                    + " WHERE   CODIGO = ? "
                    + " AND         CODCOB = ? ";
        }

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, Utils.quitaNulo(credenciales.getCodigo()));

            if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                    ps.setString(2, params.getCODPADRE());
                } else {
                    ps.setString(2, params.getCODPADRE());
                    ps.setString(3, credenciales.getPadre());
                }
            } else {
                ps.setString(2, credenciales.getPadre());
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<E_Cliente> datosCliente = new LinkedList<>();
                List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();

                if (rs.next()) {
                    E_Cliente NuevoCliente = new E_Cliente();
                    NuevoCliente.setCODIGO(credenciales.getCodigo());
                    NuevoCliente.setCODCOB(Utils.quitaNulo(rs.getString("CODCOB")));
                    NuevoCliente.setPADRE(Utils.quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setNOMBRE(Utils.quitaNulo(rs.getString("NOMBRE")));
                    NuevoCliente.setCONTACTO(Utils.quitaNulo(rs.getString("CONTACTO")));
                    NuevoCliente.setDIRECCION(Utils.quitaNulo(rs.getString("DIRECCION")));
                    NuevoCliente.setUBICACION(Utils.quitaNulo(rs.getString("UBICACION")));
                    NuevoCliente.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    NuevoCliente.setCORREO(Utils.quitaNulo(rs.getString("CORREO")));
                    NuevoCliente.setNIT(Utils.quitaNulo(rs.getString("NIT")));
                    NuevoCliente.setTELEFONO(Utils.quitaNulo(rs.getString("TELEFONO")));
                    NuevoCliente.setRECOGEOFICINA(Utils.quitaNulo(rs.getString("RECOGEOFICINA")));

                    if (rs.getString("CAMPO1") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")).substring(Utils.quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")).substring(Utils.quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")).substring(Utils.quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            NuevoCliente.setCAMPO4(rs.getString("CAMPO4").substring(rs.getString("CAMPO4").indexOf("/") + 1, rs.getString("CAMPO4").length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")));
                    }
                    NuevoCliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(NuevoCliente.getPUNTO(), NuevoCliente.getUBICACION()));

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
            return new E_respuestaClientes("500", null);
        }
    }

    /**
     * Método que retorna todos los clientes que en sus campos CODIGO, NOMBRE,
     * CONTACTO O TELEFONO contienen los datos recibidos .
     *
     * @param params
     * @param datos
     * @return Listado con información de los clientes
     */
    public E_respuestaClientes ObtenerListadoClientes(E_FacCliente params, EWSSIGCLIENTES datos) {
        List<E_Cliente> clientes = new LinkedList<>();

        E_Credenciales credenciales = datos.getCredenciales();
        E_Cliente cliente = (E_Cliente) datos.getDatosEntrada();

        String Query;
        if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (credenciales.getPadre().equals(params.getCODPADRE())) {
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
            ps.setString(2, "%" + Utils.quitaNulo(cliente.getNOMBRE()) + "%");
            ps.setString(3, "%" + Utils.quitaNulo(cliente.getCONTACTO()) + "%");
            ps.setString(4, "%" + Utils.quitaNulo(cliente.getTELEFONO()) + "%");

            if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                    ps.setString(5, params.getCODPADRE());
                } else {
                    ps.setString(5, params.getCODPADRE());
                    ps.setString(6, credenciales.getPadre());
                }
            } else {
                ps.setString(5, credenciales.getPadre());
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();
                while (rs.next()) {
                    E_Cliente NuevoCliente = new E_Cliente();
                    NuevoCliente.setCODIGO(Utils.quitaNulo(rs.getString("CODIGO")));
                    NuevoCliente.setCODCOB(Utils.quitaNulo(rs.getString("CODCOB")));
                    NuevoCliente.setPADRE(Utils.quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setNOMBRE(Utils.quitaNulo(rs.getString("NOMBRE")));
                    NuevoCliente.setCONTACTO(Utils.quitaNulo(rs.getString("CONTACTO")));
                    NuevoCliente.setDIRECCION(Utils.quitaNulo(rs.getString("DIRECCION")));
                    NuevoCliente.setUBICACION(Utils.quitaNulo(rs.getString("UBICACION")));
                    NuevoCliente.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    NuevoCliente.setCORREO(Utils.quitaNulo(rs.getString("CORREO")));
                    NuevoCliente.setNIT(Utils.quitaNulo(rs.getString("NIT")));
                    NuevoCliente.setTELEFONO(Utils.quitaNulo(rs.getString("TELEFONO")));
                    NuevoCliente.setRECOGEOFICINA(Utils.quitaNulo(rs.getString("RECOGEOFICINA")));
                    if (rs.getString("CAMPO1") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")).substring(Utils.quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")).substring(Utils.quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")).substring(Utils.quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            NuevoCliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")).substring(Utils.quitaNulo(rs.getString("CAMPO4")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO4")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")));
                    }
                    NuevoCliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(NuevoCliente.getPUNTO(), NuevoCliente.getUBICACION()));
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

    public boolean ValidaExistenciaCliente(E_FacCliente params, E_Credenciales credenciales, String codigo) {
        if (Utils.quitaNulo(codigo).isEmpty()) {
            return false;
        }

        String Query;
        if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                Query = " SELECT CODIGO FROM FACCLICLIENTES WHERE   "
                        + "             CODIGO = ? "
                        + " AND     PADRE = ?";
            } else {
                Query = " SELECT CODIGO FROM FACCLICLIENTES WHERE "
                        + "          CODIGO = ? "
                        + " AND  PADRE = ? "
                        + " AND CODCOB = ?";
            }
        } else {
            Query = "SELECT CODIGO FROM FACCLICLIENTES WHERE   "
                    + "         CODIGO = ? "
                    + " AND CODCOB = ?";
        }

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, Utils.quitaNulo(codigo));

            if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                    ps.setString(2, params.getCODPADRE());
                } else {
                    ps.setString(2, params.getCODPADRE());
                    ps.setString(3, credenciales.getPadre());
                }
            } else {
                ps.setString(2, credenciales.getPadre());
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    public E_respuestaClientes ObtenerTodosClientes(E_FacCliente params, E_Credenciales credenciales) {
        String Base = ""
                + " SELECT    "
                + " CODIGO,   "
                + " PADRE,   "
                + " CODCOB,   "
                + " C_NOMBRE AS NOMBRE,   "
                + " C_CONTACTO AS CONTACTO,   "
                + " C_DIRECC AS DIRECCION,   "
                + " C_MNCP AS UBICACION,   "
                + " C_PTO AS PUNTO,   "
                + " C_EMAIL AS CORREO,   "
                + " C_TEL AS TELEFONO,   "
                + " C_NIT AS NIT,   "
                + " CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA   "
                + " FROM FACCLICLIENTES ";

        StringBuilder whereClause = new StringBuilder();
        if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
            whereClause.append(" WHERE PADRE = ? ");
            if (!credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                whereClause.append(" AND CODCOB = ? ");
            }
        } else {
            whereClause.append(" WHERE CODCOB = ? ");
        }

        if (whereClause.length() == 0) {
            return new E_respuestaClientes("500", null);
        }

        String queryCompleto = Base + whereClause.toString();

        List<E_Cliente> listado = new LinkedList<>();
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(queryCompleto)) {
            if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
                ps.setString(1, params.getCODPADRE());
                if (!credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                    ps.setString(2, credenciales.getPadre());
                }
            } else {
                ps.setString(1, credenciales.getPadre());
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();
                while (rs.next()) {
                    E_Cliente cliente = new E_Cliente();
                    cliente.setCODIGO(Utils.quitaNulo(rs.getString("CODIGO")));
                    cliente.setPADRE(Utils.quitaNulo(rs.getString("PADRE")));
                    cliente.setCODCOB(Utils.quitaNulo(rs.getString("CODCOB")));
                    cliente.setNOMBRE(Utils.quitaNulo(rs.getString("NOMBRE")));
                    cliente.setCONTACTO(Utils.quitaNulo(rs.getString("CONTACTO")));
                    cliente.setDIRECCION(Utils.quitaNulo(rs.getString("DIRECCION")));
                    cliente.setUBICACION(Utils.quitaNulo(rs.getString("UBICACION")));
                    cliente.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    cliente.setCORREO(Utils.quitaNulo(rs.getString("CORREO")));
                    cliente.setTELEFONO(Utils.quitaNulo(rs.getString("TELEFONO")));
                    cliente.setNIT(Utils.quitaNulo(rs.getString("NIT")));
                    cliente.setRECOGEOFICINA(Utils.quitaNulo(rs.getString("RECOGEOFICINA")));

                    if (rs.getString("CAMPO1") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            cliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")).substring(Utils.quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        cliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            cliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")).substring(Utils.quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        cliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            cliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")).substring(Utils.quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        cliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            cliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")).substring(Utils.quitaNulo(rs.getString("CAMPO4")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO4")).length()));
                        }
                    } else {
                        cliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")));
                    }
                    cliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(cliente.getPUNTO(), cliente.getUBICACION()));
                    if (departamentos != null) {
                        for (E_Departamento depto : departamentos) {
                            if (depto.getNOMBRE().equals(cliente.getCOBERTURA().getDEPARTAMENTO())) {
                                cliente.setDEPARTAMENTO(depto);
                                for (E_Municipio muni : depto.getMUNICIPIOS()) {
                                    if (muni.getNOMBRE().equals(cliente.getCOBERTURA().getMUNICIPIO())) {
                                        cliente.setMUNICIPIO(muni);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    listado.add(cliente);
                }
                if (listado.isEmpty()) {
                    return new E_respuestaClientes("204", listado);
                }

                return new E_respuestaClientes("200", listado);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error ", e);
        }
        return new E_respuestaClientes("500", null);
    }

    /**
     * Inserta cliente en la base de datos.
     *
     * @param usuario
     * @param params
     * @param clientes
     * @return
     */
    public boolean insertarClientes(E_Credenciales usuario, E_FacCliente params, E_Cliente clientes) {
        if (params == null || clientes == null) {
            throw new IllegalArgumentException("Los parámetros y los datos del cliente no pueden ser nulos");
        }

        String columnas = params.getUNIFICACLI().equalsIgnoreCase("S")
                ? " PADRE, CODCOB, CODIGO "
                : " CODCOB, PADRE, CODIGO ";

        String query = "INSERT INTO FACCLICLIENTES ( " + columnas + ", "
                + "C_NOMBRE, C_CONTACTO, C_TEL, C_EMAIL, C_NIT, C_DIRECC, "
                + "C_PTO, C_MNCP, RECOGEOFICINA, "
                + "CAMPO1, CAMPO2, CAMPO3, CAMPO4) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = new Conexion().AbrirConexion();
                PreparedStatement ps = connection.prepareStatement(query)) {

            String[] valores = {
                params.getCODPADRE(), usuario.getPadre(), clientes.getCODIGO().trim(),
                clientes.getNOMBRE(), clientes.getCONTACTO(), clientes.getTELEFONO(),
                clientes.getCORREO(), clientes.getNIT(), clientes.getDIRECCION(),
                clientes.getCOBERTURA().getPUNTO(), clientes.getCOBERTURA().getMUNICIPIO(),
                clientes.getRECOGEOFICINA(), usuario.getPadre() + "/" + clientes.getCAMPO1(), usuario.getPadre() + "/" + clientes.getCAMPO2(),
                usuario.getPadre() + "/" + clientes.getCAMPO3(), usuario.getPadre() + "/" + clientes.getCAMPO4()
            };

            for (int i = 0; i < valores.length; i++) {
                ps.setString(i + 1, valores[i]);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error SQL: ", e);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error inesperado: ", e);
        }
        return false;
    }

    public boolean actualizarCliente(String padre, E_Cliente clientes) {

        String Query = " "
                + " UPDATE FACCLICLIENTES SET "
                + "     C_NOMBRE = ?, "
                + "     C_CONTACTO = ?, "
                + "     C_DIRECC = ?, "
                + "     C_EMAIL = ?, "
                + "     C_TEL = ?, "
                + "     C_NIT = ?, "
                + "     CAMPO1 = ?, "
                + "     CAMPO2 = ?, "
                + "     CAMPO3 = ?, "
                + "     CAMPO4 = ?, "
                + "     RECOGEOFICINA = ?, "
                + "     C_MNCP = ?, "
                + "     C_PTO = ? "
                + " WHERE "
                + "     PADRE = ? AND CODIGO = ? ";

        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, clientes.getNOMBRE());
            ps.setString(2, clientes.getCONTACTO());
            ps.setString(3, clientes.getDIRECCION());
            ps.setString(4, clientes.getCORREO());
            ps.setString(5, clientes.getTELEFONO());
            ps.setString(6, clientes.getNIT());
            ps.setString(7, padre + "/" + clientes.getCAMPO1());
            ps.setString(8, padre + "/" + clientes.getCAMPO2());
            ps.setString(9, padre + "/" + clientes.getCAMPO3());
            ps.setString(10, padre + "/" + clientes.getCAMPO4());
            ps.setString(11, clientes.getRECOGEOFICINA());
            ps.setString(12, clientes.getCOBERTURA().getMUNICIPIO());
            ps.setString(13, clientes.getCOBERTURA().getPUNTO());
            ps.setString(14, padre);
            ps.setString(15, clientes.getCODIGO());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Error al intentar ACTUALIZAR cliente");
            ex.printStackTrace();
        }
        return false;
    }

    public E_respuestaClientes ObtenerClientesCargaMasiva(Connection con, E_FacCliente params, E_Credenciales credenciales, List<E_Departamento> departamentos, String codigo) {
        String Query;

        if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
            if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                Query = " SELECT "
                        + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                        + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                        + " FROM FACCLICLIENTES "
                        + " WHERE   CODIGO = ? "
                        + " AND         PADRE = ?";
            } else {
                Query = " SELECT "
                        + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                        + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                        + " FROM FACCLICLIENTES "
                        + " WHERE   CODIGO = ? "
                        + " AND         PADRE = ?  "
                        + " AND         CODCOB = ?";
            }
        } else {
            Query = "SELECT "
                    + "         CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, C_MNCP AS UBICACION, "
                    + "         C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, RECOGEOFICINA "
                    + " FROM FACCLICLIENTES "
                    + " WHERE   CODIGO = ? "
                    + " AND         CODCOB = ?";
        }

        try (PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setString(1, Utils.quitaNulo(codigo));

            if (params.getUNIFICACLI().equalsIgnoreCase("S")) {
                if (credenciales.getPadre().equalsIgnoreCase(params.getCODPADRE())) {
                    ps.setString(2, params.getCODPADRE());
                } else {
                    ps.setString(2, params.getCODPADRE());
                    ps.setString(3, credenciales.getPadre());
                }
            } else {
                ps.setString(2, credenciales.getPadre());
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<E_Cliente> datosCliente = new LinkedList<>();
                if (rs.next()) {
                    E_Cliente NuevoCliente = new E_Cliente();
                    NuevoCliente.setCODIGO(codigo);
                    NuevoCliente.setCODCOB(Utils.quitaNulo(rs.getString("CODCOB")));
                    NuevoCliente.setPADRE(Utils.quitaNulo(rs.getString("PADRE")));
                    NuevoCliente.setNOMBRE(Utils.quitaNulo(rs.getString("NOMBRE")));
                    NuevoCliente.setCONTACTO(Utils.quitaNulo(rs.getString("CONTACTO")));
                    NuevoCliente.setDIRECCION(Utils.quitaNulo(rs.getString("DIRECCION")));
                    NuevoCliente.setUBICACION(Utils.quitaNulo(rs.getString("UBICACION")));
                    NuevoCliente.setPUNTO(Utils.quitaNulo(rs.getString("PUNTO")));
                    NuevoCliente.setCORREO(Utils.quitaNulo(rs.getString("CORREO")));
                    NuevoCliente.setNIT(Utils.quitaNulo(rs.getString("NIT")));
                    NuevoCliente.setTELEFONO(Utils.quitaNulo(rs.getString("TELEFONO")));
                    NuevoCliente.setRECOGEOFICINA(Utils.quitaNulo(rs.getString("RECOGEOFICINA")));

                    if (rs.getString("CAMPO1") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO1")).contains("/")) {
                            NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")).substring(Utils.quitaNulo(rs.getString("CAMPO1")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO1")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO1(Utils.quitaNulo(rs.getString("CAMPO1")));
                    }
                    if (rs.getString("CAMPO2") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO2")).contains("/")) {
                            NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")).substring(Utils.quitaNulo(rs.getString("CAMPO2")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO2")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO2(Utils.quitaNulo(rs.getString("CAMPO2")));
                    }
                    if (rs.getString("CAMPO3") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO3")).contains("/")) {
                            NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")).substring(Utils.quitaNulo(rs.getString("CAMPO3")).indexOf("/") + 1, Utils.quitaNulo(rs.getString("CAMPO3")).length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO3(Utils.quitaNulo(rs.getString("CAMPO3")));
                    }
                    if (rs.getString("CAMPO4") != null) {
                        if (Utils.quitaNulo(rs.getString("CAMPO4")).contains("/")) {
                            NuevoCliente.setCAMPO4(rs.getString("CAMPO4").substring(rs.getString("CAMPO4").indexOf("/") + 1, rs.getString("CAMPO4").length()));
                        }
                    } else {
                        NuevoCliente.setCAMPO4(Utils.quitaNulo(rs.getString("CAMPO4")));
                    }
                    NuevoCliente.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(NuevoCliente.getPUNTO(), NuevoCliente.getUBICACION()));

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
            return new E_respuestaClientes("500", null);
        }
    }
}
