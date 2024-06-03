/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.services;

import com.guatex.sig.datos.DReporteClientes;
import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.ConvertidorXML;
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

    /**
     * This is a sample web service operation
     */
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
        return new ConvertidorXML().respuestaXML(respuesta);
//        EReporteClientes cliente = new EReporteClientes();
//        System.out.println("entre y tengo de peticion [" + XML + "]");
//        ConvertidorXML convertidor = new ConvertidorXML();
//        String respXML = "";
//        try {
//            String codigo = convertidor.getTag("CODIGO", XML);
//            String padre = convertidor.getTag("PADRE", XML);
//            String codCob = convertidor.getTag("CODCOB", XML);
//
//            DReporteClientes reporte = new DReporteClientes();
//            cliente = reporte.obtenerClientes(padre, codCob, codigo);
//
//            respXML = "<RESPUESTA>"
//                    + "<PADRE>" + cliente.getPADRE() + "</PADRE>"
//                    + "<CODCOB>" + cliente.getCODCOB() + "</CODCOB>"
//                    + "<CODIGO>" + cliente.getCODIGO() + "</CODIGO>"
//                    + "<NIT>" + cliente.getNIT() + "</NIT>"
//                    + "<TELEFONO>" + cliente.getTELEFONO() + "</TELEFONO>"
//                    + "<NOMBRE>" + cliente.getNOMBRE() + "</NOMBRE>"
//                    + "<DIRECCION>" + cliente.getDIRECCION() + "</DIRECCION>"
//                    + "<CONTACTO>" + cliente.getCONTACTO() + "</CONTACTO>"
//                    + "<MUNICIPIO>" + cliente.getMUNICIPIO() + "</MUNICIPIO>"
//                    + "<PUNTO>" + cliente.getPUNTO() + "</PUNTO>"
//                    + "<DEPTODES>" + cliente.getDEPTODES() + "</DEPTODES>"
//                    + "<EMAIL>" + cliente.getEMAIL() + "</EMAIL>"
//                    + "<CAMPO1>" + cliente.getCAMPO1() + "</CAMPO1>"
//                    + "<CAMPO2>" + cliente.getCAMPO2() + "</CAMPO2>"
//                    + "<CAMPO3>" + cliente.getCAMPO3() + "</CAMPO3>"
//                    + "<CAMPO4>" + cliente.getCAMPO4() + "</CAMPO4>"
//                    + "<RECOGEOFICINA>" + cliente.getRECOGEOFICINA() + "</RECOGEOFICINA>"
//                    + "<CODIGO>001</CODIGO>"
//                    + "<MENSAJE>Se encontro datos de cliente</MENSAJE>"
//                    + "</RESPUESTA>";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            respXML = "<RESPUESTA>"
//                    + "< >002</CODIGO>"
//                    + "<MENSAJE>No se encontró el cliente</MENSAJE>"
//                    + "</RESPUESTA>";
//        }
//
//        return respXML;
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
}
