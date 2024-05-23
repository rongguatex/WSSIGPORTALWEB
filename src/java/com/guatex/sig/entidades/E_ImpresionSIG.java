/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "DATOS_GUIA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_ImpresionSIG implements Serializable {

    @XmlElement(name = "NOGUIA")
    private String _NOGUIA;

    private String _ESTADO;

    @XmlElement(name = "CODCOB")
    private String _CODCOB;

    @XmlElement(name = "USUARIO")
    private String _USUARIO;

    public String getNOGUIA() {
        return _NOGUIA;
    }

    public void setNOGUIA(String _NOGUIA) {
        this._NOGUIA = _NOGUIA;
    }

    public String getESTADO() {
        return _ESTADO;
    }

    public void setESTADO(String _ESTADO) {
        this._ESTADO = _ESTADO;
    }

    public String getCODCOB() {
        return _CODCOB;
    }

    public void setCODCOB(String _CODCOB) {
        this._CODCOB = _CODCOB;
    }

    public String getUSUARIO() {
        return _USUARIO;
    }

    public void setUSUARIO(String _USUARIO) {
        this._USUARIO = _USUARIO;
    }

    @Override
    public String toString() {
        return "E_ImpresionSIG{" + "_NOGUIA=" + _NOGUIA + ", _ESTADO=" + _ESTADO + ", _CODCOB=" + _CODCOB + ", _USUARIO=" + _USUARIO + '}';
    }

   
}
