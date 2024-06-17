package com.guatex.sig.entidades;

import java.util.LinkedList;
import java.util.List;

public class E_Departamento {
     private String _CODIGO;
    private String _NOMBRE;
    private String _PAIS;
    private List<E_Municipio> _MUNICIPIOS = new LinkedList<E_Municipio>();
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
        return "E_Departamento { \n" + "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _PAIS=" + _PAIS + ", \n_MUNICIPIOS=" + _MUNICIPIOS + ", \n_TIPO=" + _TIPO + '\''+ '}';
    }
}
