package com.guatex.sig.services;

import com.guatex.sig.controllers.C_GuiasMasivas;
import com.guatex.sig.controllers.ModificarGuiaController;
import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.datos.D_Detalle;
import com.guatex.sig.datos.D_Guia;
import com.guatex.sig.datos.D_ImpresionSIG;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidadesRespuesta.E_RespuestaDetalle;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import com.guatex.sig.utils.ConvertidorXML;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "WSSIGCLIENTES")
public class WSSIGCLIENTES {

    @WebMethod(operationName = "busquedaCliente")
    public String busquedaCliente(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerCliente(cliente);
        return new ConvertidorXML().respuestaXMLDatosCliente(respuesta);
    }

    @WebMethod(operationName = "busquedaClientes")
    public String busquedaClientes(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerListadoClientes(cliente);
        return new ConvertidorXML().respuestaXMLDatosCliente(respuesta);
    }

    /**
     * busqueda de listado de guias por rango de fechas, tipo de impresión que
     * no hayan sido recolectadas.
     *
     * @param datos
     * @return
     */
    @WebMethod(operationName = "busquedaGuiasxTipoImpresion")
    public String busquedaGuiasxTipoImpresion(@WebParam(name = "datos") String datos) {
        E_Guia datosSolicitud = new ConvertidorXML().solicitudGuiasSinImprimir(datos);
        if (!datosSolicitud.getFECHA_INICIAL().isEmpty()
                && !datosSolicitud.getFECHA_FINAL().isEmpty()
                && !datosSolicitud.getCODCOB().isEmpty()
                && !datosSolicitud.getIMPRESO().isEmpty()) {
            E_RespuestaGuia respuesta = new D_Guia().BuscarRangoFechaJGuiasNoImpresas(datosSolicitud);
            return new ConvertidorXML().respuestaXMLDatosGuia(respuesta, new E_Departamento(), new E_Municipio(), new E_PuntoCobertura());
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    @WebMethod(operationName = "obtenerDetalleGuia")
    public String obtenerDetalleGuia(@WebParam(name = "datos") String noguia) {
        noguia = noguia == null ? "" : noguia.trim();
        if (!noguia.isEmpty()) {
            E_RespuestaDetalle respuesta = new D_Detalle().buscarDetalleGuia(noguia);
            return new ConvertidorXML().respuestaXMLDetalleGuia(respuesta);
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    @WebMethod(operationName = "insertarImpresionData")
    public String insertarImpresionData(@WebParam(name = "datos") String XML) {
        List<E_ImpresionSIG> impresiones = new ConvertidorXML().getObjectImpresion(XML);
        if (new D_ImpresionSIG().insertaImpresionSIG(impresiones)) {
            return new ConvertidorXML().OK();
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    @WebMethod(operationName = "insertaReimpresion")
    public String insertaReimpresion(@WebParam(name = "datos") String XML) {
        List<E_ImpresionSIG> impresiones = new ConvertidorXML().getObjectImpresion(XML);
        if (new D_ImpresionSIG().insertaReimpresion(impresiones)) {
            return new ConvertidorXML().OK();
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    /**
     * Busca los datos de una guía que no haya sido impresa y tampoco entregada.
     *
     * @param noguia - número de guía a buscar.
     * @return - xml con datos de la guía encontrada, en caso de error xml con
     * código de respuesta 400
     */
    @WebMethod(operationName = "obtenerDatosxGuiaNoImpresa")
    public String obtenerDatosxGuiaNoImpresa(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new ModificarGuiaController().obtenerDatosGuia(XML);
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "creacionGuiasMasivas")
    public String creacionGuiasMasivas(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new C_GuiasMasivas().creacionGuiasMasivas(XML.trim());
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "modificaGuia")
    public String modificaGuia(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new ModificarGuiaController().modificaGuia(XML.trim());
        }
        return new ConvertidorXML().BadRequest();
    }
}
