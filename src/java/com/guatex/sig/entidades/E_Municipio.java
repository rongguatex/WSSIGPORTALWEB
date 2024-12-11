package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MUNICIPIO")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Municipio {

    @XmlElement(name = "CODIGO")
    private String _CODIGO;
    
    @XmlElement(name = "NOMBRE")
    private String _NOMBRE;
    
    @XmlElement(name = "TIPO")
    private String _TIPO;

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        this._CODIGO = _CODIGO;
    }

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        this._NOMBRE = _NOMBRE;
    }

    public String getTIPO() {
        return _TIPO;
    }

    public void setTIPO(String _TIPO) {
        this._TIPO = _TIPO;
    }

    @Override
    public String toString() {
        return "E_Municipio{" + "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _TIPO=" + _TIPO + '}';
    }    
}
