/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.services;

import com.guatex.sig.datos.DReporteClientes;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.ConvertidorXML;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PJUNIOR-3
 */
@WebService(serviceName = "WSCLIENTESCRUD")
public class WSCLIENTESCRUD {

    @WebMethod(operationName = "insertarClientes")
    public String insertarClientes(@WebParam(name = "datos") String datos) {
        String respXML = "";

        DReporteClientes reporte = new DReporteClientes();

        EReporteClientes cliente = new EReporteClientes();
        ConvertidorXML c = new ConvertidorXML();

        cliente.setCODCOB(c.getTag("CODCOB", datos));
        cliente.setCODIGO(c.getTag("CODIGOCLIENTE", datos));
        cliente.setNOMBRE(c.getTag("NOMBRECLIENTE", datos));
        cliente.setCONTACTO(c.getTag("CONTACTOCLIENTE", datos));
        cliente.setNIT(c.getTag("NIT", datos));
        cliente.setEMAIL(c.getTag("CORREOCLIENTE", datos));
        cliente.setTELEFONO(c.getTag("TELEFONOCLIENTE", datos));
        cliente.setDIRECCION(c.getTag("DIRECCIONCLIENTE", datos));
        cliente.setCAMPO1(c.getTag("CAMPO1", datos));
        cliente.setCAMPO2(c.getTag("CAMPO2", datos));
        cliente.setCAMPO3(c.getTag("CAMPO3", datos));
        cliente.setCAMPO4(c.getTag("CAMPO4", datos));
        cliente.setDEPTODES(c.getTag("DEPTODES", datos));
        cliente.setMUNICIPIO(c.getTag("MNCPDES", datos));
        cliente.setPUNTO(c.getTag("PTODES", datos));
        cliente.setPADRE(c.getTag("PADRE", datos));
        cliente.setRECOGEOFICINA(c.getTag("RECOGEOFICINA", datos));

        if (reporte.verificoClienteExistente(cliente)) {
            respXML = "<RESPUESTA>"
                    + "<CODIGO>003</CODIGO>"
                    + "<MENSAJE>El codigo de cliente ya existe</MENSAJE>"
                    + "</RESPUESTA>";
        } else {
            int filasAfectados = new DReporteClientes().insertarClientes(cliente);

            respXML = "<RESPUESTA>"
                    + "<CODIGO>" + (filasAfectados > 0 ? "001" : "0002") + "</CODIGO>"
                    + "<MENSAJE>" + (filasAfectados > 0 ? "Cliente creado exitosamente" : "Ocurrio un error al intentar crear el cliente") + "</MENSAJE>"
                    + "</RESPUESTA>";

        }

        return respXML;

    }

    @WebMethod(operationName = "actualizarCliente")
    public String actualizaCliente(@WebParam(name = "datos") String datos) {
        String respXML = "";

        DReporteClientes reporte = new DReporteClientes();

        EReporteClientes cliente = new EReporteClientes();
        ConvertidorXML c = new ConvertidorXML();

        cliente.setCODCOB(c.getTag("CODCOB", datos));
        cliente.setCODIGO(c.getTag("CODIGOCLIENTE", datos));
        cliente.setNOMBRE(c.getTag("NOMBRECLIENTE", datos));
        cliente.setCONTACTO(c.getTag("CONTACTOCLIENTE", datos));
        cliente.setNIT(c.getTag("NIT", datos));
        cliente.setEMAIL(c.getTag("CORREOCLIENTE", datos));
        cliente.setTELEFONO(c.getTag("TELEFONOCLIENTE", datos));
        cliente.setDIRECCION(c.getTag("DIRECCIONCLIENTE", datos));
        cliente.setCAMPO1(c.getTag("CAMPO1", datos));
        cliente.setCAMPO2(c.getTag("CAMPO2", datos));
        cliente.setCAMPO3(c.getTag("CAMPO3", datos));
        cliente.setCAMPO4(c.getTag("CAMPO4", datos));
        cliente.setDEPTODES(c.getTag("DEPTODES", datos));
        cliente.setMUNICIPIO(c.getTag("MNCPDES", datos));
        cliente.setPUNTO(c.getTag("PTODES", datos));
        cliente.setPADRE(c.getTag("PADRE", datos));
        cliente.setRECOGEOFICINA(c.getTag("RECOGEOFICINA", datos));

        int filasAfectados = new DReporteClientes().actualizarCliente(cliente);

        respXML = "<RESPUESTA>"
                + "<CODIGO>" + (filasAfectados > 0 ? "001" : "0002") + "</CODIGO>"
                + "<MENSAJE>" + (filasAfectados > 0 ? "Cliente editado exitosamente" : "Ocurrio un error al intentar editar el cliente") + "</MENSAJE>"
                + "</RESPUESTA>";
        return respXML;
    }

    @WebMethod(operationName = "eliminarCliente")
    public String eliminarCliente(@WebParam(name = "datos") String datos) {
        String respXML = "";
        int filasAfectados = 0;

        DReporteClientes reporte = new DReporteClientes();
        EReporteClientes cliente = new EReporteClientes();
        ConvertidorXML c = new ConvertidorXML();

        cliente.setCODCOB(c.getTag("CODCOB", datos));
        cliente.setCODIGO(c.getTag("CODIGOCLIENTE", datos));
        cliente.setPADRE(c.getTag("PADRE", datos));

        try {
            filasAfectados = reporte.eliminarCliente(cliente.getPADRE(), cliente.getCODCOB(), cliente.getCODIGO());
        } catch (Exception ex) {
            ex.printStackTrace();
            respXML = "<RESPUESTA>"
                    + "<CODIGO>002</CODIGO>"
                    + "<MENSAJE>Ocurrió un error al intentar eliminar el cliente</MENSAJE>"
                    + "</RESPUESTA>";
            return respXML;
        }

        respXML = "<RESPUESTA>"
                + "<CODIGO>" + (filasAfectados > 0 ? "001" : "002") + "</CODIGO>"
                + "<MENSAJE>" + (filasAfectados > 0 ? "Cliente eliminado exitosamente" : "No se encontró el cliente para eliminar") + "</MENSAJE>"
                + "</RESPUESTA>";

        return respXML;
    }

    @WebMethod(operationName = "mostrarCliente")
    public String mostrarCliente(@WebParam(name = "datos") String XML) {
        System.out.println("entre y tengo de peticion [" + XML + "]");
        E_Cliente cliente = new ConvertidorXML().extraerCliente(XML);
        E_respuestaClientes respuesta = new DReporteClientes().ObtenerCliente(cliente);
        return new ConvertidorXML().respuestaXMLDatosCliente(respuesta);
    }

    @WebMethod(operationName = "listadoClientes")
    public String listadoClientes(@WebParam(name = "XML") String XML) {
        System.out.println("entre y tengo de peticion [" + XML + "]");
        ConvertidorXML c = new ConvertidorXML();
        String PADRE = c.getTag("PADRE", XML);
        String CODCOB = c.getTag("CODCOB", XML);

        List<EReporteClientes> listadoClientes = new DReporteClientes().obtengoListadoClientes(PADRE, CODCOB);
        System.out.println("PESO LISTA " + listadoClientes.size());
        String RespXML = c.respuestaXMLListadoClientes(listadoClientes);
        return RespXML;
    }

    @WebMethod(operationName = "datosExcelCliente")
    public String datosExcelCliente(@WebParam(name = "datos") String XML) {
        List<EReporteClientes> listadoClientes = new LinkedList<>();
        DReporteClientes d = new DReporteClientes();
        String respXML = "";

        try {
            boolean inserto = true;
            ConvertidorXML conv = new ConvertidorXML();

            String[] clientesData = conv.getTag("CLIENTES", XML).split(",");
            respXML = "<RESPUESTA><CLIENTES>";
            for (String clienteData : clientesData) {

                // Ignorar datos vacíos o nulos
                if (clienteData == null || clienteData.trim().isEmpty()) {
                    continue;
                }

                EReporteClientes c = new EReporteClientes();
                c.setCODIGO(quitaNulo(conv.getTag("CODIGO", clienteData)));
                c.setNOMBRE(quitaNulo(conv.getTag("NOMBRE", clienteData)));
                c.setCONTACTO(quitaNulo(conv.getTag("CONTACTO", clienteData)));
                c.setEMAIL(quitaNulo(conv.getTag("EMAIL", clienteData)));
                c.setNIT(quitaNulo(conv.getTag("NIT", clienteData)));
                c.setTELEFONO(quitaNulo(conv.getTag("TELEFONO", clienteData)));
                c.setDIRECCION(quitaNulo(conv.getTag("DIRECCION", clienteData)));
                c.setMUNICIPIO(quitaNulo(conv.getTag("COBERTURA", clienteData)));
                c.setPUNTO(quitaNulo(conv.getTag("PUNTO", clienteData)));
                c.setCAMPO1(quitaNulo(conv.getTag("CAMPO1", clienteData)));
                c.setCAMPO2(quitaNulo(conv.getTag("CAMPO2", clienteData)));
                c.setCAMPO3(quitaNulo(conv.getTag("CAMPO3", clienteData)));
                c.setCAMPO4(quitaNulo(conv.getTag("CAMPO4", clienteData)));
                c.setCODCOB(quitaNulo(conv.getTag("CODCOB", clienteData)));//
                c.setPADRE(quitaNulo(conv.getTag("PADRE", clienteData)));//

                // Validar campos no vacíos
                boolean clienteValido = true;
                String estado = "";

                //System.out.println("CODCOB[" + c.getCODCOB() + "]");
                if (!validoDatosVacios(c.getCODIGO())) {
                    // validar que codigo no exista
                    estado += " --> El codigo de cliente no puede ser vacio.";
                    clienteValido = false;
                } else if (d.validarClienteExiste(c)) {
                    estado += " --> El codigo de cliente ya existe.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getNOMBRE())) {
                    estado += " --> El nombre de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getCONTACTO())) {
                    estado += " --> El contacto de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getNIT())) {
                    estado += " --> El nit de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getEMAIL())) {
                    estado += " --> El email de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getTELEFONO())) {
                    estado += " --> El telefono de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getDIRECCION())) {
                    estado += " --> El direción de cliente no puede ser vacio.";
                    clienteValido = false;
                }

                if (!d.verificoPuntoExistente(c)) {
                    estado += " --> El PUNTO y COBERTURA no son validos.";
                    clienteValido = false;
                }

                if (clienteValido) {

                    estado += "OK";
                } else {
                    inserto = false;
                }
                c.setESTADO(estado);
                listadoClientes.add(c);
                respXML += clienteData.replace("<ESTADO></ESTADO></CLIENTE>", conv.addTag("ESTADO", estado) + "</CLIENTE>" + "\n");
            }
            respXML += "</CLIENTES></RESPUESTA>";

            if (inserto) {
                for (EReporteClientes c : listadoClientes) {
                    d.insertarClientesMasivo(c);
                }
            }

            return respXML;

        } catch (Exception e) {
            e.printStackTrace();
            return "<RespuestaClientes><codigo>002</codigo><mensaje>Error al procesar los datos: " + e.getMessage() + "</mensaje></RespuestaClientes>";
        }
    }

    @WebMethod(operationName = "verificoPunto")
    public String verificoPunto(@WebParam(name = "datos") String datos
    ) {
        String respXML = "";

        DReporteClientes reporte = new DReporteClientes();
        EReporteClientes cliente = new EReporteClientes();
        ConvertidorXML c = new ConvertidorXML();

        String nombreMunicipio = c.getTag("NOMBRE", datos).trim();
        String puntoCobertura = c.getTag("PUNTO", datos).trim();

        cliente.setMUNICIPIO(nombreMunicipio);
        cliente.setPUNTO(puntoCobertura);

        if (reporte.verificoClienteExistente(cliente)) {
            respXML = "<RESPUESTA>"
                    + "<CODIGO>003</CODIGO>"
                    + "<MENSAJE>El nombre y punto de cobertura existe</MENSAJE>"
                    + "</RESPUESTA>";
        } else {
            boolean puntoExiste = new DReporteClientes().verificoPuntoExistente(cliente);

            respXML = "<RESPUESTA>"
                    + "<CODIGO>" + (puntoExiste ? "001" : "002") + "</CODIGO>"
                    + "<MENSAJE>" + (puntoExiste ? "Nombre o punto de cobertura no existe" : "Ocurrió un error al buscar el punto de cobertura") + "</MENSAJE>"
                    + "</RESPUESTA>";
        }

        return respXML;
    }

    private String quitaNulo(String dato) {
        return dato == null ? "" : dato.trim();
    }

    private boolean validoDatosVacios(String dato) {
        boolean esValido = true;

        if (dato == null || dato.isEmpty() || dato.equals("") || dato.length() == 0) {
            esValido = false;
        }
        return esValido;
    }

}
