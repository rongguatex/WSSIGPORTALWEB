package com.guatex.sig.entidades;

public class E_Municipio {

    private String _CODIGO = "00";
    private String _NOMBRE = "";

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
        if (_NOMBRE != null) {
            this._NOMBRE = _NOMBRE.trim();
        } else {
            this._NOMBRE = "";
        }
    }

    @Override
    public String toString() {
        return this._NOMBRE;
    }
}
