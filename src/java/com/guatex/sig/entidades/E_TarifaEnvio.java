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
public class E_TarifaEnvio {
    private String _CODIGO;
    private String _NOMBRE;
    private String _ABREVIATURA;
    private String _ATADOS;
    private String _SBULTOS;
    private String _CPIEZASBULTOS;
    private String _PESOFIJO;

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        if (_CODIGO != null) {
            this._CODIGO = _CODIGO.trim();
        }

    }

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        if (_NOMBRE != null) {
            this._NOMBRE = _NOMBRE.trim();
        }

    }

    public String getABREVIATURA() {
        return _ABREVIATURA;
    }

    public void setABREVIATURA(String _ABREVIATURA) {
        if (_ABREVIATURA != null) {
            this._ABREVIATURA = _ABREVIATURA.trim();
        }

    }

    public String getATADOS() {

        return _ATADOS;
    }

    public void setATADOS(String _ATADOS) {
        if (_ATADOS != null) {
            this._ATADOS = _ATADOS.trim();
        }

    }

    public String getSBULTOS() {
        return _SBULTOS;
    }

    public void setSBULTOS(String _SBULTOS) {
        if (_SBULTOS != null) {
            this._SBULTOS = _SBULTOS.trim();
        }

    }

    public String getCPIEZASBULTOS() {
        return _CPIEZASBULTOS;
    }

    public void setCPIEZASBULTOS(String _CPIEZASBULTOS) {
        if (_CPIEZASBULTOS != null) {
            this._CPIEZASBULTOS = _CPIEZASBULTOS.trim();
        }

    }

    public String getPESOFIJO() {
        return _PESOFIJO;
    }

    public void setPESOFIJO(String _PESOFIJO) {
        if (_PESOFIJO != null) {
            this._PESOFIJO = _PESOFIJO.trim();
        }

    }

    @Override
    public String toString() {
        return "E_TarifaEnvio{" + "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _ABREVIATURA=" + _ABREVIATURA + ", _ATADOS=" + _ATADOS + ", _SBULTOS=" + _SBULTOS + ", _CPIEZASBULTOS=" + _CPIEZASBULTOS + ", _PESOFIJO=" + _PESOFIJO + '}';
    }
    
}