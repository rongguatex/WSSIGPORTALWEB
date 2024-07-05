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
@XmlRootElement(name = "OPCION")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_JUsuarioOpcion {

    @XmlElement(name = "USUARIO")
    private String _USUARIO;

    @XmlElement(name = "CODIGOOPCION")
    private String _CODIGOOPCION;

    public String getUSUARIO() {
        return _USUARIO;
    }

    public void setUSUARIO(String _USUARIO) {
        this._USUARIO = _USUARIO;
    }

    public String getCODIGOOPCION() {
        return _CODIGOOPCION;
    }

    public void setCODIGOOPCION(String _CODIGOOPCION) {
        this._CODIGOOPCION = _CODIGOOPCION;
    }

    @Override
    public String toString() {
        return "E_JUsuarioOpcion{" + "_USUARIO= " + _USUARIO + ", _CODIGOOPCION= " + _CODIGOOPCION + '}';
    }
}
