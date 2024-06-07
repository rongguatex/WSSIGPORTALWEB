/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidadesRespuesta;

import com.guatex.sig.entidades.E_DatosGuiaMasiva;
import com.guatex.sig.entidades.RespuestaGeneral;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "WSSIGCLIENTES")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_RespuestaGuiasMasivas {

    @XmlElement(name = "RESPUESTA")
    RespuestaGeneral respuesta;

    @XmlElementWrapper(name = "LISTADODATOSGUIA")
    @XmlElement(name = "DATOSGUIA")
    private List<E_DatosGuiaMasiva> listaDatosGuia = new LinkedList<>();

    public E_RespuestaGuiasMasivas(RespuestaGeneral respuesta, List<E_DatosGuiaMasiva> listaDatosGuia) {
        this.listaDatosGuia = listaDatosGuia;
        this.respuesta = respuesta;
    }

    public E_RespuestaGuiasMasivas() {
    }

    public RespuestaGeneral getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaGeneral respuesta) {
        this.respuesta = respuesta;
    }

    public List<E_DatosGuiaMasiva> getListaDatosGuia() {
        return listaDatosGuia;
    }

    public void setListaDatosGuia(List<E_DatosGuiaMasiva> listaDatosGuia) {
        this.listaDatosGuia = listaDatosGuia;
    }

    @Override
    public String toString() {
        return "E_RespuestaGuiasMasivas{" + "respuesta=" + respuesta + ", listaDatosGuia=" + listaDatosGuia + '}';
    }
}
