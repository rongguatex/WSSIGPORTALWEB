package com.guatex.sig.entidades;

public class E_Municipio {

    private String _CODIGO;
    private String _NOMBRE;
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
