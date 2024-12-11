package com.guatex.sig.entidades;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DEPARTAMENTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Departamento {
    
    @XmlElement(name = "CODIGO")
     private String _CODIGO;
    
    @XmlElement(name = "NOMBRE")
    private String _NOMBRE;
    
    @XmlElement(name = "PAIS")
    private String _PAIS;
    
    @XmlElement(name = "TIPO")
    private String _TIPO;
    
    private List<E_Municipio> _MUNICIPIOS = new LinkedList<E_Municipio>();
    
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

    public String getPAIS() {
        return _PAIS;
    }

    public void setPAIS(String _PAIS) {
        this._PAIS = _PAIS;
    }

    public List<E_Municipio> getMUNICIPIOS() {
        return _MUNICIPIOS;
    }

    public void setMUNICIPIOS(List<E_Municipio> _MUNICIPIOS) {
        this._MUNICIPIOS = _MUNICIPIOS;
    }

    public String getTIPO() {
        return _TIPO;
    }

    public void setTIPO(String _TIPO) {
        this._TIPO = _TIPO;
    }

    @Override
    public String toString() {
        return "E_Departamento { " + "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _PAIS=" + _PAIS + ", _MUNICIPIOS=" + _MUNICIPIOS + ", _TIPO=" + _TIPO + '}';
    }
}
