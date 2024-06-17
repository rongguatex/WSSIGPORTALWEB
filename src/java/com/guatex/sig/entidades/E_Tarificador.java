/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

/**
 *
 * @author RGALICIA
 */
public class E_Tarificador {

    private String _CODIGO;
    private String _DESCIPCION;
    private String _CODIGOENVIO;
    private String _CANTPIEZAS;
    private String _PESOENVIO;
    private String _TARIFAENVIO;

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        this._CODIGO = _CODIGO;
    }

    public String getDESCIPCION() {
        return _DESCIPCION;
    }

    public void setDESCIPCION(String _DESCIPCION) {
        this._DESCIPCION = _DESCIPCION;
    }

    public String getCODIGOENVIO() {
        return _CODIGOENVIO;
    }

    public void setCODIGOENVIO(String _CODIGOENVIO) {
        this._CODIGOENVIO = _CODIGOENVIO;
    }

    public String getCANTPIEZAS() {
        return _CANTPIEZAS;
    }

    public void setCANTPIEZAS(String _CANTPIEZAS) {
        this._CANTPIEZAS = _CANTPIEZAS;
    }

    public String getPESOENVIO() {
        return _PESOENVIO;
    }

    public void setPESOENVIO(String _PESOENVIO) {
        this._PESOENVIO = _PESOENVIO;
    }

    public String getTARIFAENVIO() {
        return _TARIFAENVIO;
    }

    public void setTARIFAENVIO(String _TARIFAENVIO) {
        this._TARIFAENVIO = _TARIFAENVIO;
    }

    @Override
    public String toString() {
        return "E_Tarificador{" + "_CODIGO=" + _CODIGO + ", _DESCIPCION=" + _DESCIPCION + ", _CODIGOENVIO=" + _CODIGOENVIO + ", _CANTPIEZAS=" + _CANTPIEZAS + ", _PESOENVIO=" + _PESOENVIO + ", _TARIFAENVIO=" + _TARIFAENVIO + '}';
    }

}
