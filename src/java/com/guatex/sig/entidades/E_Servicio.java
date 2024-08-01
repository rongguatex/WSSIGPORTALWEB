/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "SERVICIO")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Servicio {

    @XmlElement(name = "IDSERVICIO")
    private String IDSERVICIO;

    @XmlElement(name = "URLSERVICIO")
    private String URLSERVICIO;

    public String getIDSERVICIO() {
        return IDSERVICIO;
    }

    public void setIDSERVICIO(String IDSERVICIO) {
        this.IDSERVICIO = IDSERVICIO;
    }

    public String getURLSERVICIO() {
        return URLSERVICIO;
    }

    public void setURLSERVICIO(String URLSERVICIO) {
        this.URLSERVICIO = URLSERVICIO;
    }

    @Override
    public String toString() {
        return "E_Servicio{" + "IDSERVICIO=" + IDSERVICIO + ", URLSERVICIO=" + URLSERVICIO + '}';
    }
}
