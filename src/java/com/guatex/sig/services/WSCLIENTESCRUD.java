/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.services;

import com.guatex.sig.datos.DReporteClientes;
import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.datos.D_FacCliente;
import com.guatex.sig.datos.D_PuntoCobertura;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.EWSSIGCLIENTES;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.ConvertidorXML;
import com.guatex.sig.utils.ParseadorXML;
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
    public String insertarClientes(@WebParam(name = "datos") String xml) {
        EWSSIGCLIENTES<E_Cliente> datos = (EWSSIGCLIENTES<E_Cliente>) new ParseadorXML().parseoXML(xml, EWSSIGCLIENTES.class, E_Cliente.class);
        if (datos.getCredenciales() == null || datos.getDatosEntrada() == null) {
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Error en el envío de datos, por favor, intente de nuevo.");
        }

        E_Credenciales credenciales = datos.getCredenciales();
        E_Cliente cliente = (E_Cliente) datos.getDatosEntrada();

        E_FacCliente paramsUsuario = new D_FacCliente().obtenerParamsUsuario(credenciales);
        if (paramsUsuario == null) {
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Usuario inválido, por favor, intente de nuevo.");
        }

        boolean existeCliente = new D_Clientes().ValidaExistenciaCliente(paramsUsuario, credenciales, cliente.getCODIGO());
        if (existeCliente) {
            return new ConvertidorXML().RespuestaGeneralSIG("204", "El codigo de cliente ya existe.");
        }

        cliente = new D_PuntoCobertura().obtengoPuntosCobertura(cliente);
        boolean creacionExitosa = new D_Clientes().insertarClientes(credenciales, paramsUsuario, cliente);
        String codigo = creacionExitosa ?  "200" : "500";
        String mensaje = creacionExitosa ? "Cliente creado exitosamente" : "Ocurrio un error al intentar crear el cliente";
        return new ConvertidorXML().RespuestaGeneralSIG(codigo, mensaje);
    }

    @WebMethod(operationName = "actualizarCliente")
    public String actualizaCliente(@WebParam(name = "datos") String xml) {
         EWSSIGCLIENTES<E_Cliente> datos = (EWSSIGCLIENTES<E_Cliente>) new ParseadorXML().parseoXML(xml, EWSSIGCLIENTES.class, E_Cliente.class);
        if (datos.getCredenciales() == null || datos.getDatosEntrada() == null) {
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Error en el envío de datos, por favor, intente de nuevo.");
        }
        
        E_Credenciales credenciales = datos.getCredenciales();
        E_Cliente cliente = (E_Cliente) datos.getDatosEntrada();
        cliente = new D_PuntoCobertura().obtengoPuntosCobertura(cliente);
        
        boolean actualizacionExitosa = new D_Clientes().actualizarCliente(credenciales.getPadre(), cliente);
        String codigo = actualizacionExitosa ?  "200" : "500";
        String mensaje = actualizacionExitosa ? "Cliente actualizado exitosamente" : "Ocurrio un error al intentar actualizar al cliente";
        
        return new ConvertidorXML().RespuestaGeneralSIG(codigo, mensaje);
    }

    @WebMethod(operationName = "eliminarCliente")
    public String eliminarCliente(@WebParam(name = "datos") String xml) {
        EWSSIGCLIENTES<?> datos = (EWSSIGCLIENTES<?>) new ParseadorXML().parseoXML(xml, EWSSIGCLIENTES.class);
        if (datos.getCredenciales() == null) {
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Error al obtener datos.");
        }

        String respXML = "";
        int filasAfectados = 0;

        DReporteClientes reporte = new DReporteClientes();
        E_Credenciales credenciales = datos.getCredenciales();
        try {
            filasAfectados = reporte.eliminarCliente(credenciales.getPadre(), credenciales.getCodigo());
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
                    estado += " El codigo de cliente no puede ser vacio.";
                    clienteValido = false;
                } else if (d.validarClienteExiste(c)) {
                    estado += " El codigo de cliente ya existe.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getNOMBRE())) {
                    estado += " El nombre de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getCONTACTO())) {
                    estado += " El contacto de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getNIT())) {
                    estado += " El nit de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getEMAIL())) {
                    estado += " El email de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getTELEFONO())) {
                    estado += " El telefono de cliente no puede ser vacio.";
                    clienteValido = false;
                }
                if (!validoDatosVacios(c.getDIRECCION())) {
                    estado += " El direción de cliente no puede ser vacio.";
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

            return respXML.replaceAll("&", "&amp;");

        } catch (Exception e) {
            e.printStackTrace();
            return "<RespuestaClientes><codigo>002</codigo><mensaje>Error al procesar los datos: " + e.getMessage() + "</mensaje></RespuestaClientes>";
        }
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
