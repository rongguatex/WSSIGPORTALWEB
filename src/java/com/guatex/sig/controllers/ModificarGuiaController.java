/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.controllers;

import com.guatex.sig.datos.D_Depto_Municipios;
import com.guatex.sig.datos.D_Detalle;
import com.guatex.sig.datos.D_Guia;
import com.guatex.sig.datos.D_GuiasHijas;
import com.guatex.sig.datos.D_PuntoCobertura;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_Ubicacion;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.entidades.EWSSIGCLIENTES;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import com.guatex.sig.utils.ConvertidorXML;
import com.guatex.sig.utils.ValidacionCredenciales;
import java.io.StringReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author RGALICIA
 */
public class ModificarGuiaController {

    public String modificaGuia(String XML) {
        System.out.println(XML);

        EWSSIGCLIENTES<?> parseo = (EWSSIGCLIENTES<?>) parseoXML(XML, EWSSIGCLIENTES.class);
        System.out.println(parseo);
        if (parseo.getCredenciales() != null) {
            E_Credenciales credenciales = parseo.getCredenciales();
            RespuestaGeneral validacionCredenciales = new ValidacionCredenciales().validar(credenciales);
            if ("0000".equals(validacionCredenciales.getCodigo())) {
                E_Credenciales datos = parseo.getCredenciales();
                boolean existErrorEliminacion = false;
//                existErrorEliminacion = !new D_Guia().eliminaGuia(quitaNulo(datos.getNoguia()));
                
                if(existErrorEliminacion) {
                    return "<RESPUESTA><CODIGO>9999</CODIGO><MENSAJE>Error al eliminar guía</MENSAJE></RESPUESTA>";
                }
                
                existErrorEliminacion = !new D_Detalle().EliminarGuiasDetalle(quitaNulo(datos.getNoguia()));
                
                if(existErrorEliminacion) {
                    return "<RESPUESTA><CODIGO>9999</CODIGO><MENSAJE>Error al eliminar guía detalle</MENSAJE></RESPUESTA>";
                }
                existErrorEliminacion = !new D_GuiasHijas().eliminaHijas(quitaNulo(datos.getNoguia()));

                if(existErrorEliminacion) {
                    return "<RESPUESTA><CODIGO>9999</CODIGO><MENSAJE>Error al eliminar guías hijas</MENSAJE></RESPUESTA>";
                }
                
                return new ConvertidorXML().OK();
            }
        }

        return new ConvertidorXML().BadRequest();
    }

    /**
     * Obtiene datos de guía y departamento, municipio y punto de cobertura de
     * origen y destino.
     *
     * @param XML
     * @return
     */
    public String obtenerDatosGuia(String XML) {
        System.out.println("--> " + XML);
        EWSSIGCLIENTES<?> parseo = (EWSSIGCLIENTES<?>) parseoXML(XML, EWSSIGCLIENTES.class);

        if (parseo != null) {
            E_Credenciales datos = parseo.getCredenciales();
            datos.setNoguia(datos.getNoguia() == null ? "" : datos.getNoguia().trim());
            if (!datos.getNoguia().isEmpty()) {
                E_RespuestaGuia respuesta = new D_Guia().obtenerDatosxGuiaNoImpresa(datos);
                if (respuesta.getCODIGO().equals("200")) {
                    List<E_Departamento> departamentos = new D_Depto_Municipios().ObtenerDeptosMunicipios();

                    E_Ubicacion ubicacionOrigen = new E_Ubicacion();
                    E_Ubicacion ubicacionDestino = new E_Ubicacion();

                    //Obtiene datos de punto de origen - remitente
                    ubicacionOrigen.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(
                            respuesta.getLISTADO_GUIAS().get(0).getPTOORI(),
                            respuesta.getLISTADO_GUIAS().get(0).getMNCPORI()
                    ));

                    ubicacionOrigen.setDEPARTAMENTO(encontrarDepartamento(departamentos, ubicacionOrigen.getCOBERTURA().getDEPARTAMENTO()));
                    ubicacionOrigen.setMUNICIPIO(encontrarMunicipio(ubicacionOrigen.getDEPARTAMENTO(), ubicacionOrigen.getCOBERTURA().getMUNICIPIO()));

                    //Obtiene datos de punto de destino - destinatario.
                    ubicacionDestino.setCOBERTURA(new D_PuntoCobertura().BuscarUbicacionEspecifica(
                            respuesta.getLISTADO_GUIAS().get(0).getPTODES(),
                            respuesta.getLISTADO_GUIAS().get(0).getMNCPDES()
                    ));

                    ubicacionDestino.setDEPARTAMENTO(encontrarDepartamento(departamentos, ubicacionDestino.getCOBERTURA().getDEPARTAMENTO()));
                    ubicacionDestino.setMUNICIPIO(encontrarMunicipio(ubicacionDestino.getDEPARTAMENTO(), ubicacionDestino.getCOBERTURA().getMUNICIPIO()));

                    return new ConvertidorXML().respuestaXMLGuia(respuesta, ubicacionOrigen, ubicacionDestino);
                } else if(respuesta.getCODIGO().equals("204")){
                    return new ConvertidorXML().NoContent();
                }
            }
        }
        return new ConvertidorXML().BadRequest();
    }

    private E_Departamento encontrarDepartamento(List<E_Departamento> departamentos, String nombreDepto) {
        for (E_Departamento departamento : departamentos) {
            if (departamento.getNOMBRE().equalsIgnoreCase(nombreDepto)) {
                return departamento;
            }
        }
        return new E_Departamento();
    }

    private E_Municipio encontrarMunicipio(E_Departamento departamento, String nombreMunicipio) {
        for (E_Municipio municipio : departamento.getMUNICIPIOS()) {
            if (municipio.getNOMBRE().equalsIgnoreCase(nombreMunicipio)) {
                return municipio;
            }
        }
        return new E_Municipio();
    }

    /**
     * Deserializa la cadena XML y la combierte en un objeto..
     *
     * @param xml
     * @param classesToBeBound
     * @return - Objeto.
     */
    public Object parseoXML(String xml, Class... classesToBeBound) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(classesToBeBound);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();
            return unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException ex) {
            System.out.println("Ocurrio un error al parsear xml a objeto. [" + ex.getMessage() + "]");
            ex.printStackTrace();
        }
        return null;
    }

    private String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }

}
