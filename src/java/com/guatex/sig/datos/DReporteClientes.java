/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.datos;

import com.guatex.sig.entidades.EReporte;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PJUNIOR-3
 */
public class DReporteClientes {
    Utils util = new Utils();

    public List<EReporteClientes> reporteClientes(String CODCOB) {
        List<EReporteClientes> listaClientes = new LinkedList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        EReporte r = new EReporte();
        r.setCODCOB(CODCOB);
        r = obtengoUnificaCliente(r);

        String Query = "";

        if (r.getUNIFICA().equals("S")) {
            if (r.getCODCOB().equals(r.getPADRE())) {

                Query = "SELECT  CODIGO, CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, "
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, PADRE FROM FACCLICLIENTES "
                        + "WHERE PADRE = ?";
            } else {
                Query = "SELECT  CODIGO, CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                        + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, "
                        + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, PADRE FROM FACCLICLIENTES "
                        + "WHERE PADRE = ? AND CODCOB = ?";
            }
        } else {
            Query = "SELECT  CODIGO, CODCOB, C_NOMBRE AS NOMBRE, C_CONTACTO AS CONTACTO, C_DIRECC AS DIRECCION, "
                    + "C_MNCP AS UBICACION, C_PTO AS PUNTO, C_EMAIL AS CORREO, C_TEL AS TELEFONO, C_NIT AS NIT, "
                    + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, PADRE FROM FACCLICLIENTES "
                    + "WHERE CODCOB = ?";
        }

        try {

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);
            ps.setString(1, r.CODCOB);
            ps.setString(2, r.CODCOB);
            rs = ps.executeQuery();

            while (rs.next()) {
                EReporteClientes cliente = new EReporteClientes();
                cliente.setCODIGO(rs.getString("CODIGO"));
                cliente.setCODCOB(rs.getString("CODCOB"));
                cliente.setNOMBRE(rs.getString("NOMBRE"));
                cliente.setCONTACTO(rs.getString("CONTACTO"));
                cliente.setDIRECCION(rs.getString("DIRECCION"));
                cliente.setMUNICIPIO(rs.getString("UBICACION"));
                cliente.setPUNTO(rs.getString("PUNTO"));
                cliente.setEMAIL(rs.getString("CORREO"));
                cliente.setTELEFONO(rs.getString("TELEFONO"));
                cliente.setNIT(rs.getString("NIT"));
                cliente.setCAMPO1(rs.getString("CAMPO1"));
                cliente.setCAMPO2(rs.getString("CAMPO2"));
                cliente.setCAMPO3(rs.getString("CAMPO3"));
                cliente.setCAMPO4(rs.getString("CAMPO4"));
                cliente.setRECOGEOFICINA(rs.getString("RECOGEOFICINA"));
                cliente.setPADRE(rs.getString("PADRE"));
                listaClientes.add(cliente);
            }

        } catch (Exception ex) {
            System.out.println("Ocurrio un error al intentar buscar CLIENTES");
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

                if (con != null) {
                    con.close();
                    con = null;
                }

            } catch (Exception ex) {
                System.out.println("Ocurrio un error al intentar cerrar BD");
                ex.printStackTrace();
            }
        }
        return listaClientes;

    }

    public EReporteClientes obtenerCliente(String padre, String codCob, String codigo) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        EReporteClientes cliente = null;
        String query = "";

        try {
            query = "SELECT C_NOMBRE, C_CONTACTO, C_DIRECC, C_EMAIL, C_TEL, C_NIT, "
                    + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, C_MNCP, C_PTO "
                    + "FROM FACCLICLIENTES WHERE PADRE = ? AND CODCOB = ? AND CODIGO = ?";

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(query);

            ps.setString(1, padre);
            ps.setString(2, codCob);
            ps.setString(3, codigo);

            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new EReporteClientes();
                cliente.setNOMBRE(rs.getString("C_NOMBRE"));
                cliente.setCONTACTO(rs.getString("C_CONTACTO"));
                cliente.setDIRECCION(rs.getString("C_DIRECC"));
                cliente.setEMAIL(rs.getString("C_EMAIL"));
                cliente.setTELEFONO(rs.getString("C_TEL"));
                cliente.setNIT(rs.getString("C_NIT"));
                cliente.setCAMPO1(rs.getString("CAMPO1"));
                cliente.setCAMPO2(rs.getString("CAMPO2"));
                cliente.setCAMPO3(rs.getString("CAMPO3"));
                cliente.setCAMPO4(rs.getString("CAMPO4"));
                cliente.setRECOGEOFICINA(rs.getString("RECOGEOFICINA"));
                cliente.setMUNICIPIO(rs.getString("C_MNCP"));
                cliente.setPUNTO(rs.getString("C_PTO"));
                cliente.setPADRE(padre);
                cliente.setCODCOB(codCob);
                cliente.setCODIGO(codigo);
            }

        } catch (Exception ex) {
            System.out.println("Error al intentar OBTENER cliente");
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return cliente;
    }

    public List<EReporteClientes> obtengoListadoClientes(String PADRE, String CODCOB) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EReporteClientes> clientes = new LinkedList<>();
        String query = "";

        try {
            query = "SELECT CODIGO, C_NOMBRE, C_CONTACTO, C_DIRECC, C_EMAIL, C_TEL, C_NIT, "
                    + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, C_MNCP, C_PTO "
                    + "FROM FACCLICLIENTES WHERE PADRE = ?";

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(query);

            ps.setString(1, PADRE);
//            ps.setString(2, CODCOB);

            rs = ps.executeQuery();

            while (rs.next()) {
                EReporteClientes cliente = new EReporteClientes();

                cliente = new EReporteClientes();
                cliente.setNOMBRE(util.limpiaStr(rs.getString("C_NOMBRE")));
                cliente.setCONTACTO(util.limpiaStr(rs.getString("C_CONTACTO")));
                cliente.setDIRECCION(util.limpiaStr(rs.getString("C_DIRECC")));
                cliente.setEMAIL(util.limpiaStr(rs.getString("C_EMAIL")));
                cliente.setTELEFONO(util.limpiaStr(rs.getString("C_TEL")));
                cliente.setNIT(util.limpiaStr(rs.getString("C_NIT")));
                cliente.setCAMPO1(util.limpiaStr(rs.getString("CAMPO1")).replaceAll("/", ""));
                cliente.setCAMPO2(util.limpiaStr(rs.getString("CAMPO2")).replaceAll("/", ""));
                cliente.setCAMPO3(util.limpiaStr(rs.getString("CAMPO3")).replaceAll("/", ""));
                cliente.setCAMPO4(util.limpiaStr(rs.getString("CAMPO4")).replaceAll("/", ""));
                cliente.setRECOGEOFICINA(util.limpiaStr(rs.getString("RECOGEOFICINA")));
                cliente.setMUNICIPIO(util.limpiaStr(rs.getString("C_MNCP")));
                cliente.setPUNTO(util.limpiaStr(rs.getString("C_PTO")));
                cliente.setPADRE(PADRE);
                cliente.setCODCOB(CODCOB);
                cliente.setCODIGO(util.limpiaStr(rs.getString("CODIGO")));
                clientes.add(cliente);
            }

        } catch (Exception ex) {
            System.out.println("Error al intentar OBTENER cliente");
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return clientes;
    }

    public EReporte obtengoUnificaCliente(EReporte reporte) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT UNIFICACLI, PADRE FROM FACCLIENTES WHERE PADRE = ? and CODIGO = ?";

        try {
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(query);
            ps.setString(1, reporte.CODCOB);
            ps.setString(2, reporte.CODCOB);
            rs = ps.executeQuery();

            while (rs.next()) {
                reporte.setPADRE(util.limpiaStr(rs.getString("PADRE")));
                reporte.setUNIFICA(util.limpiaStr(rs.getString("UNIFICACLI")));
            }

        } catch (Exception ex) {
            System.out.println("Ocurrio un error al intentar buscar UNIFICACLIENTE");
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

                if (con != null) {
                    con.close();
                    con = null;
                }

            } catch (Exception ex) {
                System.out.println("Ocurrio un error al intentar cerrar BD");
                ex.printStackTrace();
            }
        }
        return reporte;
    }

    public boolean verificoClienteExistente(EReporteClientes cliente) {

        boolean existe = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = " SELECT * "
                + " FROM FACCLICLIENTES "
                + " WHERE PADRE = ? AND CODCOB = ? AND CODIGO = ? ";

        try {
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(query);
            ps.setString(1, cliente.getPADRE());
            ps.setString(2, cliente.getCODCOB());
            ps.setString(3, cliente.getCODIGO());
            rs = ps.executeQuery();

            while (rs.next()) {
                existe = true;
            }

        } catch (Exception ex) {
            System.out.println("Ocurrio un error al intentar buscar clienten existente");
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

                if (con != null) {
                    con.close();
                    con = null;
                }

            } catch (Exception ex) {
                System.out.println("Ocurrio un error al intentar cerrar BD");
                ex.printStackTrace();
            }
        }
        return existe;
    }

    public boolean verificoPuntoExistente(EReporteClientes cliente) {
        boolean existe = false;

        String query = "SELECT * FROM TRFMUNICIPIOS WHERE PUNTODECOBERTURA = ? AND NOMBRE = ?";
        try (Connection con = new Conexion().AbrirConexion();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cliente.getPUNTO());
            ps.setString(2, cliente.getMUNICIPIO());
            try (ResultSet rs = ps.executeQuery()) {
                existe = rs.next();
            }

        } catch (Exception ex) {
            System.out.println("Ocurrió un error al intentar validar el punto de cobertura");
            ex.printStackTrace();
        }

        return existe;
    }

    public int insertarClientes(EReporteClientes clientes) {

        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement ps = null;

        String Query = "";

        try {

            Query = "INSERT INTO FACCLICLIENTES ("
                    + "CODIGO, CODCOB, C_NOMBRE, C_CONTACTO, C_DIRECC , C_EMAIL, C_TEL, C_NIT,"
                    + "  CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, PADRE, C_MNCP, C_PTO  )"
                    + "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);

            ps.setString(1, clientes.getCODIGO());
            ps.setString(2, clientes.getCODCOB());
            ps.setString(3, clientes.getNOMBRE());
            ps.setString(4, clientes.getCONTACTO());
            ps.setString(5, clientes.getDIRECCION());
            ps.setString(6, clientes.getEMAIL());
            ps.setString(7, clientes.getTELEFONO());
            ps.setString(8, clientes.getNIT());
            ps.setString(9, clientes.getCAMPO1());
            ps.setString(10, clientes.getCAMPO2());
            ps.setString(11, clientes.getCAMPO3());
            ps.setString(12, clientes.getCAMPO4());
            ps.setString(13, clientes.getRECOGEOFICINA());
            ps.setString(14, clientes.getPADRE());
            ps.setString(15, clientes.getMUNICIPIO());
            ps.setString(16, clientes.getPUNTO());
            //System.out.println("lo que llevo en MUNICIPIO: [" + clientes.getMUNICIPIO() + "] [" + clientes.getPUNTO() + "]");
            rowsAffected = ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al intentar insertar cliente");
            ex.printStackTrace();

        } finally {
            try {
                if (ps != null) {
                    ps.close();

                }
                if (con != null) {
                    con.close();

                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return rowsAffected;

    }

    public int insertarClientesMasivo(EReporteClientes clientes) {

        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement ps = null;

        String Query = "";

//        System.out.println("clientes " + clientes.getMUNICIPIO() + " - " + clientes.getPUNTO());
//        System.out.println("recoge oficina " + clientes.getRECOGEOFICINA());
        try {

            Query = " INSERT INTO FACCLICLIENTES ("
                    + " CODIGO, C_NOMBRE, C_CONTACTO, C_DIRECC , C_EMAIL, C_TEL, C_NIT, "
                    + "  CAMPO1, CAMPO2, CAMPO3, CAMPO4, PADRE, CODCOB)"
                    + "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);

            ps.setString(1, clientes.getCODIGO());
            ps.setString(2, clientes.getNOMBRE());
            ps.setString(3, clientes.getCONTACTO());
            ps.setString(4, clientes.getDIRECCION());
            ps.setString(5, clientes.getEMAIL());
            ps.setString(6, clientes.getTELEFONO());
            ps.setString(7, clientes.getNIT());
            ps.setString(8, clientes.getCAMPO1());
            ps.setString(9, clientes.getCAMPO2());
            ps.setString(10, clientes.getCAMPO3());
            ps.setString(11, clientes.getCAMPO4());
            ps.setString(12, clientes.getPADRE()); //          
            ps.setString(13, clientes.getCODCOB());
            rowsAffected = ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al intentar insertar cliente");
            ex.printStackTrace();

        } finally {
            try {
                if (ps != null) {
                    ps.close();

                }
                if (con != null) {
                    con.close();

                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return rowsAffected;

    }

    public int actualizarCliente(EReporteClientes clientes) {

        int rowsAffected = 0;
        Connection con = null;
        PreparedStatement ps = null;
        String Query = "";

        try {

            Query = "UPDATE FACCLICLIENTES SET C_NOMBRE = ?, C_CONTACTO = ?, C_DIRECC = ?, C_EMAIL = ?, "
                    + "C_TEL = ?, C_NIT = ?, CAMPO1 = ?, CAMPO2 = ?, CAMPO3 = ?, CAMPO4 = ?, "
                    + "RECOGEOFICINA = ?, C_MNCP = ?, C_PTO = ? "
                    + "WHERE PADRE = ? AND CODCOB = ? AND CODIGO = ?";

            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);

            ps.setString(1, clientes.getNOMBRE());
            ps.setString(2, clientes.getCONTACTO());
            ps.setString(3, clientes.getDIRECCION());
            ps.setString(4, clientes.getEMAIL());
            ps.setString(5, clientes.getTELEFONO());
            ps.setString(6, clientes.getNIT());
            ps.setString(7, clientes.getCAMPO1());
            ps.setString(8, clientes.getCAMPO2());
            ps.setString(9, clientes.getCAMPO3());
            ps.setString(10, clientes.getCAMPO4());
            ps.setString(11, clientes.getRECOGEOFICINA());
            ps.setString(12, clientes.getMUNICIPIO());
            ps.setString(13, clientes.getPUNTO());
            ps.setString(14, clientes.getPADRE());
            ps.setString(15, clientes.getCODCOB());
            ps.setString(16, clientes.getCODIGO());

            rowsAffected = ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al intentar ACTUALIZAR cliente");
            ex.printStackTrace();

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return rowsAffected;
    }

    public int eliminarCliente(String padre, String codCob, String codigo) {
        Connection con = null;
        PreparedStatement ps = null;
        int filasAfectados = 0;

        try {
            String Eliminar = "DELETE FROM FACCLICLIENTES WHERE padre = ? AND CODCOB = ? AND CODIGO = ?";
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Eliminar);

            ps.setString(1, padre);
            ps.setString(2, codCob);
            ps.setString(3, codigo);

            filasAfectados = ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Ocurre un problema al eliminar el cliente");
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al intentar cerrar la BD");
                ex.printStackTrace();
            }
        }
        return filasAfectados;
    }

    public EReporteClientes obtenerClientes(String CODCOB, String padre, String CODIGO) {
        EReporteClientes cliente = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String Query = "SELECT CODIGO, CODCOB, C_NOMBRE, C_CONTACTO, C_DIRECC,"
                + "C_MNCP, C_PTO, C_EMAIL , C_TEL, C_NIT, "
                + "CAMPO1, CAMPO2, CAMPO3, CAMPO4, RECOGEOFICINA, PADRE FROM FACCLICLIENTES "
                + "WHERE CODCOB = ? AND PADRE = ? AND CODIGO = ?";

        try {
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);
            ps.setString(1, CODCOB);
            ps.setString(2, padre);
            ps.setString(3, CODIGO);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new EReporteClientes();
                cliente.setCODIGO(quitaNulo(rs.getString("CODIGO")));
                cliente.setCODCOB(quitaNulo(rs.getString("CODCOB")));
                cliente.setPADRE(quitaNulo(rs.getString("PADRE")));
                cliente.setNOMBRE(quitaNulo(rs.getString("C_NOMBRE")));
                cliente.setCONTACTO(quitaNulo(rs.getString("C_CONTACTO")));
                cliente.setDIRECCION(quitaNulo(rs.getString("C_DIRECC")));
                cliente.setMUNICIPIO(quitaNulo(rs.getString("C_MNCP")));
                cliente.setPUNTO(quitaNulo(rs.getString("C_PTO")));
                cliente.setEMAIL(quitaNulo(rs.getString("C_EMAIL")));
                cliente.setTELEFONO(quitaNulo(rs.getString("C_TEL")));
                cliente.setNIT(quitaNulo(rs.getString("C_NIT")));
                cliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")));
                cliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")));
                cliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")));
                cliente.setCAMPO4(quitaNulo(rs.getString("CAMPO4")));
                cliente.setRECOGEOFICINA(quitaNulo(rs.getString("RECOGEOFICINA")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cliente;
    }

    public boolean validarClienteExiste(EReporteClientes cliente) {
        boolean existeCliente = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String Query = " SELECT CODIGO "
                + " FROM FACCLICLIENTES "
                + " WHERE CODIGO = ? AND PADRE = ? ";

        try {
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);
            ps.setString(1, cliente.getCODIGO());
            ps.setString(2, cliente.getPADRE());
//            ps.setString(3, cliente.getCODCOB());

            rs = ps.executeQuery();

            if (rs.next()) {
                existeCliente = true;
                System.out.println("SI EXISTE CLIENTE!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return existeCliente;
    }

    public EReporteClientes obtengoPuntosCobertura(EReporteClientes cliente) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String Query = " SELECT PUNTODECOBERTURA, NOMBRE "
                + " FROM TRFMUNICIPIOS "
                + " WHERE CODIGO = ?  ";

        try {
            con = new Conexion().AbrirConexion();
            ps = con.prepareStatement(Query);
            ps.setString(1, cliente.getPUNTO());

            rs = ps.executeQuery();

            if (rs.next()) {
                cliente.setPUNTO(rs.getString("PUNTODECOBERTURA"));
                cliente.setMUNICIPIO(rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cliente;
    }

    private String quitaNulo(String dato) {
        if (dato != null) {
            dato = dato.trim();
        } else {
            dato = "";
        }
        return dato;
    }

    public E_respuestaClientes ObtenerCliente(E_Cliente cliente) {
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
                    NuevoCliente.setCAMPO1(quitaNulo(rs.getString("CAMPO1")).replaceAll("/", ""));
                    NuevoCliente.setCAMPO2(quitaNulo(rs.getString("CAMPO2")).replaceAll("/", ""));
                    NuevoCliente.setCAMPO3(quitaNulo(rs.getString("CAMPO3")).replaceAll("/", ""));
                    NuevoCliente.setCAMPO4(quitaNulo(rs.getString("CAMPO4")).replaceAll("/", ""));

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

}
