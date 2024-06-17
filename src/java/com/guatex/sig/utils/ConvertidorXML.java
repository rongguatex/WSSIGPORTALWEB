package com.guatex.sig.utils;

import com.guatex.sig.entidades.EReporteClienteXML;
import com.guatex.sig.entidades.EReporteClientes;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_respuestaClientes;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

    public String respuestaXML(E_respuestaClientes data) {

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
        System.out.println(XML);
        return XML;
    }

    public String respuestaXMLListadoClientes(List<EReporteClientes> listadoClientes) {

        String XML = "<RESPUESTA>"
                + "<CLIENTES>";
        for (EReporteClientes cliente : listadoClientes) {
            XML += "<CLIENTE>"
                    + addTag("CODIGO", cliente.getCODIGO())
                    + addTag("CODCOB", cliente.getCODCOB())
                    + addTag("PADRE", cliente.getPADRE())
                    + addTag("NOMBRE", cliente.getNOMBRE())
                    + addTag("CONTACTO", cliente.getCONTACTO())
                    + addTag("DIRECION", cliente.getDIRECCION())
                    + addTag("PUNTO", cliente.getPUNTO())
                    + addTag("CORREO", cliente.getEMAIL())
                    + addTag("TELEFONO", cliente.getTELEFONO())
                    + addTag("NIT", cliente.getNIT())
                    + addTag("RECOGEOFICINA", cliente.getRECOGEOFICINA())
                    + addTag("CAMPO1", cliente.getCAMPO1())
                    + addTag("CAMPO2", cliente.getCAMPO2())
                    + addTag("CAMPO3", cliente.getCAMPO3())
                    + addTag("CAMPO4", cliente.getCAMPO4())
                    + "</CLIENTE>";
        }
        XML += "</CLIENTES>"
                + "</RESPUESTA>";
        System.out.println(XML);
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

    public String respXMLListadoClientes(List<EReporteClienteXML> clientes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientesWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ClientesWrapper wrapper = new ClientesWrapper();
            wrapper.setClientes(clientes);

            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "<RespuestaClientes><codigo>002</codigo><mensaje>Error al convertir los datos a XML: " + e.getMessage() + "</mensaje></RespuestaClientes>";
        }
    }

    // Renamed method to avoid clash
    public String respXMLClientes(List<EReporteClienteXML> clientes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientesWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ClientesWrapper wrapper = new ClientesWrapper();
            wrapper.setClientes(clientes);

            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "<RespuestaClientes><codigo>002</codigo><mensaje>Error al convertir los datos a XML: " + e.getMessage() + "</mensaje></RespuestaClientes>";
        }
    }

    public String getTag(String tag, String dato) {
        String respuesta = dato;
        if (dato.contains("<" + tag + ">")) {
            respuesta = dato.substring(dato.indexOf("<" + tag + ">"), dato.indexOf("</" + tag + ">")).replace("<" + tag + ">", "");
        }
        return respuesta;
    }

    public String addTag(String tag, String dato) {
        return "<" + tag + ">" + dato + "</" + tag + ">";
    }

}
