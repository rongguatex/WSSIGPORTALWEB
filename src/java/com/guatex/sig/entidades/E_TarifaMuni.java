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
public class E_TarifaMuni {

    private String _NOMBRE;
    private String _PUNTODECOBERTURA;
    private String _TIPODETARIFA;
    private String _TOTALZONASEXTRA;
    private String _COBERTURAEXTRA;

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        if (_NOMBRE != null) {
            this._NOMBRE = _NOMBRE.trim();
        }
    }

    public String getPUNTODECOBERTURA() {
        return _PUNTODECOBERTURA;
    }

    public void setPUNTODECOBERTURA(String _PUNTODECOBERTURA) {
        if (_PUNTODECOBERTURA != null) {
            this._PUNTODECOBERTURA = _PUNTODECOBERTURA.trim();
        }
    }

    public String getTIPODETARIFA() {
        return _TIPODETARIFA;
    }

    public void setTIPODETARIFA(String _TIPODETARIFA) {
        if (_TIPODETARIFA != null) {
            this._TIPODETARIFA = _TIPODETARIFA.trim();
        }
    }

    public String getTOTALZONASEXTRA() {
        return _TOTALZONASEXTRA;
    }

    public void setTOTALZONASEXTRA(String _TOTALZONASEXTRA) {
        if (_TOTALZONASEXTRA != null) {
            this._TOTALZONASEXTRA = _TOTALZONASEXTRA.trim();
        }
    }

    public String getCOBERTURAEXTRA() {
        return _COBERTURAEXTRA;
    }

    public void setCOBERTURAEXTRA(String _COBERTURAEXTRA) {
        if (_COBERTURAEXTRA != null) {
            this._COBERTURAEXTRA = _COBERTURAEXTRA.trim();
        }
    }

    @Override
    public String toString() {
        return "E_TarifaMuni{" + "_NOMBRE=" + _NOMBRE + ", _PUNTODECOBERTURA=" + _PUNTODECOBERTURA + ", _TIPODETARIFA=" + _TIPODETARIFA + ", _TOTALZONASEXTRA=" + _TOTALZONASEXTRA + ", _COBERTURAEXTRA=" + _COBERTURAEXTRA + '}';
    }
}
