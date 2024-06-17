/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "SOLICITUD")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Solicitud implements Serializable {

    @XmlElementWrapper(name = "LISTADO_GUIAS")
    @XmlElement(name = "DATOS_GUIA")
    private List<E_ImpresionSIG> listadoGuiaImpresion;

    public List<E_ImpresionSIG> getListadoGuiaImpresion() {
        return listadoGuiaImpresion;
    }

    public void setListadoGuiaImpresion(List<E_ImpresionSIG> listadoGuiaImpresion) {
        this.listadoGuiaImpresion = listadoGuiaImpresion;
    }
}
