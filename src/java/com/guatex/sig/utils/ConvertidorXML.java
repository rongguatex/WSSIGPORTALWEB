package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_Solicitud;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidadesRespuesta.E_RespuestaDetalle;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ConvertidorXML {

    public E_Cliente extraerCliente(String xml) {
        E_Cliente cliente = new E_Cliente();
        cliente.setCODIGO(getTag("CODIGO", xml));
        cliente.setNOMBRE(getTag("NOMBRE", xml));
        cliente.setTELEFONO(getTag("TELEFONO", xml));
        cliente.setCONTACTO(getTag("CONTACTO", xml));
        cliente.setUNIFICACLI(getTag("UNIFICACLI", xml));
        cliente.setCODCOB(getTag("CODCOB", xml));
        cliente.setPADRE(getTag("PADRE", xml));
        return cliente;
    }

    public String respuestaXMLDatosCliente(E_respuestaClientes data) {

        String XML = "<RESPUESTA>"
                + addTag("CODIGO", data.getCODIGO())
                + addTag("MENSAJE", data.getMENSAJE())
                + "<LISTADO_CLIENTES>";
        if (data.getDATOS_CLIENTES().size() > 0) {
            for (E_Cliente cliente : data.getDATOS_CLIENTES()) {
                XML += "<CLIENTE>"
                        + addTag("CODIGO_CLIENTE", cliente.getCODIGO())
                        + addTag("CODCOB", cliente.getCODCOB())
                        + addTag("PADRE", cliente.getPADRE())
                        + addTag("NOMBRE", cliente.getNOMBRE())
                        + addTag("CONTACTO", cliente.getCONTACTO())
                        + addTag("DIRECCION", cliente.getDIRECCION())
                        + addTag("UBICACION", cliente.getUBICACION())
                        + addTag("PUNTO", cliente.getPUNTO())
                        + addTag("CORREO", cliente.getCORREO())
                        + addTag("TELEFONO", cliente.getTELEFONO())
                        + addTag("NIT", cliente.getNIT())
                        + addTag("RECOGEOFICINA", cliente.getRECOGEOFICINA())
                        + addTag("CAMPO1", cliente.getCAMPO1())
                        + addTag("CAMPO2", cliente.getCAMPO2())
                        + addTag("CAMPO3", cliente.getCAMPO3())
                        + addTag("CAMPO4", cliente.getCAMPO4())
                        + "<COBERTURA>"
                        + addTag("FRECUENCIA", cliente.getCOBERTURA().getFRECUENCIA())
                        + addTag("DEPARTAMENTO", cliente.getCOBERTURA().getDEPARTAMENTO())
                        + addTag("MUNICIPIO", cliente.getCOBERTURA().getMUNICIPIO())
                        + addTag("UBICACION", cliente.getCOBERTURA().getUBICACION())
                        + addTag("CODIGO_PUNTO", cliente.getCOBERTURA().getCODIGOPUNTO())
                        + addTag("PUNTO", cliente.getCOBERTURA().getPUNTO())
                        + addTag("RECOGEOFICINA", String.valueOf(cliente.getCOBERTURA().isRECOGEOFICINA()))
                        + addTag("LUNES", String.valueOf(cliente.getCOBERTURA().getLUNES()))
                        + addTag("MARTES", String.valueOf(cliente.getCOBERTURA().getMARTES()))
                        + addTag("MIERCOLES", String.valueOf(cliente.getCOBERTURA().getMIERCOLES()))
                        + addTag("JUEVES", String.valueOf(cliente.getCOBERTURA().getJUEVES()))
                        + addTag("VIERNES", String.valueOf(cliente.getCOBERTURA().getVIERNES()))
                        + addTag("SABADO", String.valueOf(cliente.getCOBERTURA().getSABADO()))
                        + "</COBERTURA>"
                        + "<DEPARTAMENTO>"
                        + addTag("CODDEPTO", String.valueOf(cliente.getDEPARTAMENTO().getCODIGO()))
                        + addTag("NOMBREDEPTO", cliente.getDEPARTAMENTO().getNOMBRE())
                        + addTag("PAIS", cliente.getDEPARTAMENTO().getPAIS())
                        //                        + "<MUNICIPIOS_DEPTO>"    //Se verificará su utilidad próximamente :)
                        //                        + municipios(cliente)
                        //                        + "</MUNICIPIOS_DEPTO>"
                        + "</DEPARTAMENTO>"
                        + "<MUNICIPIO>"
                        + addTag("CODMUNICIPIO", String.valueOf(cliente.getMUNICIPIO().getCODIGO()))
                        + addTag("NOMBREMUNICIPIO", cliente.getMUNICIPIO().getNOMBRE())
                        + "</MUNICIPIO>"
                        + "</CLIENTE>";
            }
        } else {
            data.setDATOS_CLIENTES(new LinkedList<E_Cliente>());
        }
        XML += "</LISTADO_CLIENTES>"
                + "</RESPUESTA>";
        return XML;
    }

    private String municipios(E_Cliente cliente) {
        String xml = "";
        for (E_Municipio m : cliente.getDEPARTAMENTO().getMUNICIPIOS()) {
            xml += "<MUNICIPIODEPTO>"
                    + addTag("CODDEPTO", String.valueOf(m.getCODIGO()))
                    + addTag("NOMBREDEPTO", m.getNOMBRE())
                    + "</MUNICIPIODEPTO>";
        }
        return xml;
    }

    /**
     * Convierte el parametro xml a un objeto de tipo guia con los datos:
     * FECHAINICIO, FECHAFINAL y CODCOB
     *
     * @param xml
     * @return
     */
    public E_Guia solicitudGuiasSinImprimir(String xml) {
        E_Guia datos = new E_Guia();
        datos.setFECHA_INICIAL(getTag("FECHAINICIO", xml));
        datos.setFECHA_FINAL(getTag("FECHAFINAL", xml));
        datos.setCODCOB(getTag("CODCOB", xml));
        datos.setIMPRESO(getTag("TIPOIMPRESION", xml));
        return datos;
    }

    /**
     * Crear XML de respuesta para los datos de la guía
     *
     * @param data
     * @return
     */
    public String respuestaXMLDatosGuia(E_RespuestaGuia data) {
        String XML = "<RESPUESTA>"
                + addTag("CODIGO", data.getCODIGO())
                + addTag("MENSAJE", data.getMENSAJE())
                + "<LISTADO_GUIAS>";
        if (data.getLISTADO_GUIAS().size() > 0) {
            for (E_Guia guia : data.getLISTADO_GUIAS()) {
                XML += "<DATOS_GUIA>"
                        + addTag("NOGUIA", guia.getNOGUIA())
                        + addTag("CONTACTO", guia.getCONTACTO())
                        + addTag("NOMDES", guia.getNOMDES())
                        + addTag("TELDES", guia.getTELDES())
                        + addTag("DIRDES", guia.getDIRDES())
                        + addTag("CODCOB", guia.getCODCOB())
                        + addTag("SEGURO", guia.getSEGURO())
                        + addTag("DECLARADO", guia.getDECLARADO())
                        + addTag("MNCPDES", guia.getMNCPDES())
                        + addTag("FECHA", guia.getFECHA())
                        + addTag("DESCRENV", guia.getDESCRENV())
                        + addTag("SEABREPAQUETE", guia.getSEABREPAQUETE())
                        + addTag("CONTSEG", guia.getCONTSEG())
                        + addTag("COD_VALORACOBRAR", guia.getCOD_VALORACOBRAR())
                        + "</DATOS_GUIA>";
            }
        }
        XML += "</LISTADO_GUIAS>"
                + "</RESPUESTA>";
        return XML;
    }

    /**
     * Crea XML de respuesta para el detalle de la guía
     *
     * @param data
     * @return
     */
    public String respuestaXMLDetalleGuia(E_RespuestaDetalle data) {
        String XML = "<RESPUESTA>"
                + addTag("CODIGO", data.getCODIGO())
                + addTag("MENSAJE", data.getMENSAJE())
                + "<DETALLE_GUIA>";
        if (data.getDETALLE_GUIA().size() > 0) {
            for (E_DetalleLinea datos : data.getDETALLE_GUIA()) {
                XML += "<DATOS_DETALLE>"
                        + addTag("LINEA", datos.getLINEA())
                        + addTag("PIEZAS", datos.getPIEZAS() + "")
                        + addTag("TIPOENVIO", datos.getTIPOENVIO())
                        + addTag("PESO", datos.getPESO())
                        + addTag("TARIFA", datos.getTARIFA())
                        + "</DATOS_DETALLE>";
            }
        }
        XML += "</DETALLE_GUIA>"
                + "</RESPUESTA>";
        return XML;
    }

    /**
     * Crea una lista de objetos E_ImpresionSIG a partir del XML proporcionado.
     *
     * @param xml El XML que contiene los datos.
     * @return Una lista de objetos E_ImpresionSIG, o null si ocurre un error.
     */
    public List<E_ImpresionSIG> getObjectImpresion(String xml) {
        if (xml != null || !xml.isEmpty()) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(E_Solicitud.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                E_Solicitud solicitud = (E_Solicitud) unmarshaller.unmarshal(new StringReader(xml));
                return solicitud.getListadoGuiaImpresion();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                return null;
            }
        } else {
            throw new IllegalArgumentException("El parámetro xml no puede ser nulo o vacío.");
        }
    }

    /**
     * Retorna XML con código de respuesta 200 cuando todo funcionó
     * correctamente.
     *
     * @return
     */
    public String OK() {
        return "<RESPUESTA>"
                + addTag("CODIGO", "200")
                + addTag("MENSAJE", "OK")
                + "</RESPUESTA>";
    }

    /**
     * Retorna XML con código de respuesta 400 para cuando existe un error por
     * falta de datos.
     *
     * @return
     */
    public String BadRequest() {
        return "<RESPUESTA>"
                + addTag("CODIGO", "400")
                + addTag("MENSAJE", "BAD REQUEST")
                + "</RESPUESTA>";
    }

    /**
     *
     * @param tag
     * @param dato
     * @return
     */
    public String getTag(String tag, String dato) {
        String respuesta = dato;
        if (dato.contains("<" + tag + ">")) {
            respuesta = dato.substring(dato.indexOf("<" + tag + ">"), dato.indexOf("</" + tag + ">")).replace("<" + tag + ">", "");
        }
        return respuesta;
    }

    /**
     *
     * @param tag
     * @param dato
     * @return
     */
    public String addTag(String tag, String dato) {
        return "<" + tag + ">" + dato + "</" + tag + ">";
    }
}
