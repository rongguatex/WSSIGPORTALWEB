/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

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
public class E_GuiasMasivas {
     @XmlElement(name = "CREDENCIALES")
    E_Credenciales credenciales;

    @XmlElementWrapper(name = "LISTADODATOSGUIA")
    @XmlElement(name = "DATOSGUIA")
    private List<E_DatosGuiaMasiva> listaDatosGuia = new LinkedList<>();

    public E_Credenciales getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(E_Credenciales credenciales) {
        this.credenciales = credenciales;
    }

    public List<E_DatosGuiaMasiva> getListaDatosGuia() {
        return listaDatosGuia;
    }

    public void setListaDatosGuia(List<E_DatosGuiaMasiva> listaDatosGuia) {
        this.listaDatosGuia = listaDatosGuia;
    }

    @Override
    public String toString() {
        return "E_GuiasMasivas{" + "credenciales=" + credenciales + ", listaDatosGuia=" + listaDatosGuia + '}';
    }
}
