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
public class RespuestaTomaServicio {

    @XmlElement(name = "NOFILA")
    private int nofila;
    
    @XmlElement(name = "LINEADETALLE")
    private int lineaDetalle;
    
    @XmlElement(name = "RESPUESTA")
    private RespuestaGeneral general;

    public RespuestaTomaServicio() {
    }
    
    public RespuestaTomaServicio(int nofila, int lineaDetalle, RespuestaGeneral general) {
        this.nofila = nofila;
        this.lineaDetalle = lineaDetalle;
        this.general = general;
    }

    public int getNofila() {
        return nofila;
    }

    public void setNofila(int nofila) {
        this.nofila = nofila;
    }

    public int getLineaDetalle() {
        return lineaDetalle;
    }

    public void setLineaDetalle(int lineaDetalle) {
        this.lineaDetalle = lineaDetalle;
    }

    public RespuestaGeneral getGeneral() {
        return general;
    }

    public void setGeneral(RespuestaGeneral general) {
        this.general = general;
    }

    @Override
    public String toString() {
        return "RespuestaTomaServicio {" + "nofila= " + nofila + ", lineaDetalle= " + lineaDetalle + ", general= " + general + '}';
    }
}
