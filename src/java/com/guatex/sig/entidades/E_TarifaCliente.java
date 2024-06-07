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
public class E_TarifaCliente {

    private String _CODIGO;
    private String _TARIFAUNICA;
    private String _TARIFANORMAL;
    private String _TARIFAEXTRA;

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        if (_CODIGO != null) {
            this._CODIGO = _CODIGO.trim();
        }
    }

    public String getTARIFAUNICA() {
        return _TARIFAUNICA;
    }

    public void setTARIFAUNICA(String _TARIFAUNICA) {
        if (_TARIFAUNICA != null) {
            this._TARIFAUNICA = _TARIFAUNICA.trim();
        }
    }

    public String getTARIFANORMAL() {
        return _TARIFANORMAL;
    }

    public void setTARIFANORMAL(String _TARIFANORMAL) {
        if (_TARIFANORMAL != null) {
            this._TARIFANORMAL = _TARIFANORMAL.trim();
        }
    }

    public String getTARIFAEXTRA() {
        return _TARIFAEXTRA;
    }

    public void setTARIFAEXTRA(String _TARIFAEXTRA) {
        if (_TARIFAEXTRA != null) {
            this._TARIFAEXTRA = _TARIFAEXTRA.trim();
        }
    }
}
