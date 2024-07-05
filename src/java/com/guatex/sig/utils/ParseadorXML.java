/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_DatosGuiaMasiva;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuiasMasivas;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author RGALICIA
 */
public class ParseadorXML {

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

    public String parseoObj(Object objetc, Class... classesToBeBound) {//List<Object> objects
        try {
            JAXBContext contexto = JAXBContext.newInstance(classesToBeBound);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter stringWriter = new StringWriter();
//            for (Object objetc : objetcs) {
                marshaller.marshal(objetc, stringWriter);
//            }
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            System.err.println("Ocurrio un error " + e.getMessage());
        }
        return null;
    }
}
