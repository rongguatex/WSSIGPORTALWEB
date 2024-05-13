package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_respuestaClientes;
import java.util.LinkedList;
import java.util.List;

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
