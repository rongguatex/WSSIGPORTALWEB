package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.entidades.E_Solicitud;
import com.guatex.sig.entidades.E_Tarificador;
import com.guatex.sig.entidades.E_Ubicacion;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidades.RespuestaGeneral;
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
        System.out.println("[" + XML + "]");
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
    public String respuestaXMLDatosGuia(E_RespuestaGuia data, E_Departamento departamento, E_Municipio municipio, E_PuntoCobertura cobertura) {
        String XML = "<RESPUESTA>"
                + addTag("CODIGO", data.getCODIGO())
                + addTag("MENSAJE", data.getMENSAJE())
                + "<LISTADO_GUIAS>";
        if (data.getLISTADO_GUIAS().size() > 0) {
            for (E_Guia guia : data.getLISTADO_GUIAS()) {
                XML += "<DATOS_GUIA>"
                        //datos generales de guía
                        + addTag("IDGUIA", guia.getIDGUIA())
                        + addTag("NOGUIA", guia.getNOGUIA())
                        + addTag("CODCOB", guia.getCODCOB())
                        + addTag("IDSERVICIO", guia.getIDSERVICIO())
                        + addTag("FECHA", guia.getFECHA())
                        //datos de remitente
                        + addTag("CODREM", guia.getCODREM())
                        + addTag("NOMREM", guia.getNOMREM())
                        + addTag("TELREM", guia.getTELREM())
                        + addTag("DIRREM", guia.getDIRREM())
                        //datos de destinatario
                        + addTag("CODDES", guia.getCODDES())
                        + addTag("NOMDES", guia.getNOMDES())
                        + addTag("TELDES", guia.getTELDES())
                        + addTag("DIRDES", guia.getDIRDES())
                        + addTag("CONTACTO", guia.getCONTACTO())
                        //otros datos
                        + addTag("PTOORI", guia.getPTOORI())
                        + addTag("PTODES", guia.getPTODES())
                        + addTag("MNCPORI", guia.getMNCPORI())
                        + addTag("MNCPDES", guia.getMNCPDES())
                        + addTag("LLAVECLI", guia.getLLAVECLIENTE())
                        + addTag("DESCRENV", guia.getDESCRENV())
                        + addTag("EMAIL", guia.getEMAIL())
                        + addTag("PIEZAS", guia.getPIEZAS() + "")
                        + addTag("PESO", guia.getPESO())
                        + addTag("TIPTAR", guia.getTIPTAR())
                        + addTag("COBEX", guia.getCOBEX())
                        + addTag("SEGURO", guia.getSEGURO())
                        + addTag("DECLARADO", guia.getDECLARADO())
                        + addTag("COD_VALORACOBRAR", guia.getCOD_VALORACOBRAR())
                        + addTag("SEABREPAQUETE", guia.getSEABREPAQUETE())
                        + addTag("CONTSEG", guia.getCONTSEG())
                        + addTag("FECOPE", guia.getFECOPE())
                        + addTag("HORAOPE", guia.getHORAOPE())
                        + addTag("RECOGEOFICINA", guia.getRECOGEOFICINA())
                        + addTag("CAMPO1", guia.getCAMPO1())
                        + addTag("CAMPO2", guia.getCAMPO2())
                        + addTag("CAMPO3", guia.getCAMPO3())
                        + addTag("CAMPO4", guia.getCAMPO4())
                        + addTag("CODORIGEN", guia.getCODORIGEN())
                        + addTag("CODDESTINO", guia.getCODDESTINO())
                        + addTag("OBSERVACIONES", guia.getOBSERVACIONES())
                        + addTag("OBSERVACIONESENTRE", guia.getOBSERVACIONESENTRE())
                        + "<DEPARTAMENTO>"
                        + addTag("CODIGO", departamento.getCODIGO())
                        + addTag("NOMBRE", departamento.getNOMBRE())
                        + "</DEPARTAMENTO>"
                        + "<MUNICIPIO>"
                        + addTag("CODIGO", municipio.getCODIGO())
                        + addTag("NOMBRE", municipio.getNOMBRE())
                        + "</MUNICIPIO>"
                        + "<COBERTURA>"
                        + addTag("CODIGO_PUNTO", cobertura.getCODIGOPUNTO())
                        + addTag("PUNTO", cobertura.getPUNTO())
                        + addTag("UBICACION", cobertura.getUBICACION())
                        + addTag("DEPTO_COBERTURA", cobertura.getDEPARTAMENTO())
                        + addTag("MUNI_COBERTURA", cobertura.getMUNICIPIO())
                        + addTag("LUNES", String.valueOf(cobertura.getLUNES()))
                        + addTag("MARTES", String.valueOf(cobertura.getMARTES()))
                        + addTag("MIERCOLES", String.valueOf(cobertura.getMIERCOLES()))
                        + addTag("JUEVES", String.valueOf(cobertura.getJUEVES()))
                        + addTag("VIERNES", String.valueOf(cobertura.getVIERNES()))
                        + addTag("SABADO", String.valueOf(cobertura.getSABADO()))
                        + addTag("FRECUENCIA", cobertura.getFRECUENCIA())
                        + addTag("RECOGEOFICINA", cobertura.isRECOGEOFICINA() ? "1" : "0")
                        + "</COBERTURA>"
                        + "</DATOS_GUIA>";
            }
        }
        XML += "</LISTADO_GUIAS>"
                + "</RESPUESTA>";
        System.out.println("[" + XML + "]");
        return XML;
    }

    public String respuestaXMLGuia(E_RespuestaGuia data, E_Ubicacion ubicacionOrigen, E_Ubicacion ubicacionDestino) {
        String XML = "<RESPUESTA>"
                + addTag("CODIGO", data.getCODIGO())
                + addTag("MENSAJE", data.getMENSAJE())
                + "<LISTADO_GUIAS>";
        if (data.getLISTADO_GUIAS().size() > 0) {
            for (E_Guia guia : data.getLISTADO_GUIAS()) {
                XML += "<DATOS_GUIA>"
                        //datos generales de guía
                        + addTag("IDGUIA", guia.getIDGUIA())
                        + addTag("NOGUIA", guia.getNOGUIA())
                        + addTag("CODCOB", guia.getCODCOB())
                        + addTag("IDSERVICIO", guia.getIDSERVICIO())
                        + addTag("FECHA", guia.getFECHA())
                        //datos de remitente
                        + addTag("CODREM", guia.getCODREM())
                        + addTag("NOMREM", guia.getNOMREM())
                        + addTag("TELREM", guia.getTELREM())
                        + addTag("DIRREM", guia.getDIRREM())
                        //datos de destinatario
                        + addTag("CODDES", guia.getCODDES())
                        + addTag("NOMDES", guia.getNOMDES())
                        + addTag("TELDES", guia.getTELDES())
                        + addTag("DIRDES", guia.getDIRDES())
                        + addTag("CONTACTO", guia.getCONTACTO())
                        //otros datos
                        + addTag("PTOORI", guia.getPTOORI())
                        + addTag("PTODES", guia.getPTODES())
                        + addTag("MNCPORI", guia.getMNCPORI())
                        + addTag("MNCPDES", guia.getMNCPDES())
                        + addTag("LLAVECLI", guia.getLLAVECLIENTE())
                        + addTag("DESCRENV", guia.getDESCRENV())
                        + addTag("EMAIL", guia.getEMAIL())
                        + addTag("PIEZAS", guia.getPIEZAS() + "")
                        + addTag("PESO", guia.getPESO())
                        + addTag("TIPTAR", guia.getTIPTAR())
                        + addTag("COBEX", guia.getCOBEX())
                        + addTag("SEGURO", guia.getSEGURO())
                        + addTag("DECLARADO", guia.getDECLARADO())
                        + addTag("COD_VALORACOBRAR", guia.getCOD_VALORACOBRAR())
                        + addTag("SEABREPAQUETE", guia.getSEABREPAQUETE())
                        + addTag("CONTSEG", guia.getCONTSEG())
                        + addTag("FECOPE", guia.getFECOPE())
                        + addTag("HORAOPE", guia.getHORAOPE())
                        + addTag("RECOGEOFICINA", guia.getRECOGEOFICINA())
                        + addTag("CAMPO1", guia.getCAMPO1())
                        + addTag("CAMPO2", guia.getCAMPO2())
                        + addTag("CAMPO3", guia.getCAMPO3())
                        + addTag("CAMPO4", guia.getCAMPO4())
                        + addTag("CODORIGEN", guia.getCODORIGEN())
                        + addTag("CODDESTINO", guia.getCODDESTINO())
                        + addTag("OBSERVACIONES", guia.getOBSERVACIONES())
                        + addTag("OBSERVACIONESENTRE", guia.getOBSERVACIONESENTRE())
                        + "<ORIGEN>"
                        + "<DEPARTAMENTO>"
                        + addTag("CODIGO", ubicacionOrigen.getDEPARTAMENTO().getCODIGO())
                        + addTag("NOMBRE", ubicacionOrigen.getDEPARTAMENTO().getNOMBRE())
                        + "</DEPARTAMENTO>"
                        + "<MUNICIPIO>"
                        + addTag("CODIGO", ubicacionOrigen.getMUNICIPIO().getCODIGO())
                        + addTag("NOMBRE", ubicacionOrigen.getMUNICIPIO().getNOMBRE())
                        + "</MUNICIPIO>"
                        + "<COBERTURA>"
                        + addTag("CODIGO_PUNTO", ubicacionOrigen.getCOBERTURA().getCODIGOPUNTO())
                        + addTag("PUNTO", ubicacionOrigen.getCOBERTURA().getPUNTO())
                        + addTag("UBICACION", ubicacionOrigen.getCOBERTURA().getUBICACION())
                        + addTag("DEPTO_COBERTURA", ubicacionOrigen.getCOBERTURA().getDEPARTAMENTO())
                        + addTag("MUNI_COBERTURA", ubicacionOrigen.getCOBERTURA().getMUNICIPIO())
                        + addTag("LUNES", String.valueOf(ubicacionOrigen.getCOBERTURA().getLUNES()))
                        + addTag("MARTES", String.valueOf(ubicacionOrigen.getCOBERTURA().getMARTES()))
                        + addTag("MIERCOLES", String.valueOf(ubicacionOrigen.getCOBERTURA().getMIERCOLES()))
                        + addTag("JUEVES", String.valueOf(ubicacionOrigen.getCOBERTURA().getJUEVES()))
                        + addTag("VIERNES", String.valueOf(ubicacionOrigen.getCOBERTURA().getVIERNES()))
                        + addTag("SABADO", String.valueOf(ubicacionOrigen.getCOBERTURA().getSABADO()))
                        + addTag("FRECUENCIA", ubicacionOrigen.getCOBERTURA().getFRECUENCIA())
                        + addTag("RECOGEOFICINA", ubicacionOrigen.getCOBERTURA().isRECOGEOFICINA() ? "1" : "0")
                        + "</COBERTURA>"
                        + "</ORIGEN>"
                        + "<DESTINO>"
                        + "<DEPARTAMENTO>"
                        + addTag("CODIGO", ubicacionDestino.getDEPARTAMENTO().getCODIGO())
                        + addTag("NOMBRE", ubicacionDestino.getDEPARTAMENTO().getNOMBRE())
                        + "</DEPARTAMENTO>"
                        + "<MUNICIPIO>"
                        + addTag("CODIGO", ubicacionDestino.getMUNICIPIO().getCODIGO())
                        + addTag("NOMBRE", ubicacionDestino.getMUNICIPIO().getNOMBRE())
                        + "</MUNICIPIO>"
                        + "<COBERTURA>"
                        + addTag("CODIGO_PUNTO", ubicacionDestino.getCOBERTURA().getCODIGOPUNTO())
                        + addTag("PUNTO", ubicacionDestino.getCOBERTURA().getPUNTO())
                        + addTag("UBICACION", ubicacionDestino.getCOBERTURA().getUBICACION())
                        + addTag("DEPTO_COBERTURA", ubicacionDestino.getCOBERTURA().getDEPARTAMENTO())
                        + addTag("MUNI_COBERTURA", ubicacionDestino.getCOBERTURA().getMUNICIPIO())
                        + addTag("LUNES", String.valueOf(ubicacionDestino.getCOBERTURA().getLUNES()))
                        + addTag("MARTES", String.valueOf(ubicacionDestino.getCOBERTURA().getMARTES()))
                        + addTag("MIERCOLES", String.valueOf(ubicacionDestino.getCOBERTURA().getMIERCOLES()))
                        + addTag("JUEVES", String.valueOf(ubicacionDestino.getCOBERTURA().getJUEVES()))
                        + addTag("VIERNES", String.valueOf(ubicacionDestino.getCOBERTURA().getVIERNES()))
                        + addTag("SABADO", String.valueOf(ubicacionDestino.getCOBERTURA().getSABADO()))
                        + addTag("FRECUENCIA", ubicacionDestino.getCOBERTURA().getFRECUENCIA())
                        + addTag("RECOGEOFICINA", ubicacionDestino.getCOBERTURA().isRECOGEOFICINA() ? "1" : "0")
                        + "</COBERTURA>"
                        + "</DESTINO>"
                        + "</DATOS_GUIA>";
            }
        }
        XML += "</LISTADO_GUIAS>"
                + "</RESPUESTA>";
        System.out.println("[" + XML + "]");
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

    public E_Tarificador parseoTarificador(String xml) {
        E_Tarificador tarificador = new E_Tarificador();
        tarificador.setCODIGO(getTag("CODIGO", xml));
        tarificador.setDESCIPCION(getTag("DESCRIPCION", xml));

        if (tarificador.getCODIGO().equalsIgnoreCase("S")) {
            tarificador.setCODIGOENVIO(getTag("CODIGOENVIO", xml));
            tarificador.setCANTPIEZAS(getTag("CANTPIEZAS", xml));
            tarificador.setPESOENVIO(getTag("PESOENVIO", xml));
            tarificador.setTARIFAENVIO(getTag("TARIFAENVIO", xml));
        }
        return tarificador;
    }

    public RespuestaGeneral parseoRespuestaTomaServicio(String xml) {
        if (getTag("CODIGO", xml).equals("9999")) {
            return new RespuestaGeneral("9999", getTag("DESCRIPCION", xml));
        }
        return new RespuestaGeneral("0000", "Guía insertada correctamente.");
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
        System.out.println("bad request");
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
