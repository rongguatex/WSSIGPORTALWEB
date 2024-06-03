package com.guatex.sig.services;

import com.guatex.sig.datos.DReporteClientes;
import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.utils.ConvertidorXML;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(serviceName = "WSSIGCLIENTES")
public class WSSIGCLIENTES {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + "   dd!";
    }

    @WebMethod(operationName = "busquedaCliente")
    public String busquedaCliente(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerCliente(cliente);
        return new ConvertidorXML().respuestaXML(respuesta);
    }

    @WebMethod(operationName = "busquedaClientes")
    public String busquedaClientes(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerListadoClientes(cliente);
        return new ConvertidorXML().respuestaXML(respuesta);
    }
    

    
    
     
}
