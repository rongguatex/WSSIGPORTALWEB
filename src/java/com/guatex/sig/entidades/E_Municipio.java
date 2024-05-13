package com.guatex.sig.entidades;

public class E_Municipio {

    private int _CODIGO = 0;
    private String _NOMBRE = "";

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

    @Override
    public String toString() {
        return this._NOMBRE;
    }
}
