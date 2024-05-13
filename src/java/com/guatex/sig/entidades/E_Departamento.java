package com.guatex.sig.entidades;

import java.util.LinkedList;
import java.util.List;

public class E_Departamento {
     private int _CODIGO = 0;
    private String _NOMBRE = "";
    private String _PAIS = "";
    private List<E_Municipio> _MUNICIPIOS = new LinkedList<E_Municipio>();

    public int getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(int _CODIGO) {
        this._CODIGO = _CODIGO;
    }

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        if (_NOMBRE != null) {
            this._NOMBRE = _NOMBRE.trim();
        } else {
            this._NOMBRE = "";
        }
    }

    public List<E_Municipio> getMUNICIPIOS() {
        return _MUNICIPIOS;
    }

    public void setMUNICIPIOS(List<E_Municipio> _MUNICIPIOS) {
        this._MUNICIPIOS = _MUNICIPIOS;
    }

    @Override
    public String toString() {
        return this._NOMBRE;
    }

    public String getPAIS() {
        return _PAIS;
    }

    public void setPAIS(String _PAIS) {
        this._PAIS = _PAIS;
    }
}
