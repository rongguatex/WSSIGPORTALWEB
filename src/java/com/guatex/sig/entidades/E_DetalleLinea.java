/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.Objects;

/**
 *
 * @author RGALICIA
 */
public class E_DetalleLinea {

    private String _IDGUIA;
    private String _NOGUIA;
    private String _LINEA;
    private int _PIEZAS;
    private String _TIPOENVIO;
    private String _ABVENVIO;
    private String _DESCRIPCIONENVIO;
    private String _PESO;
    private String _PESOTOTAL;
    private String _COSTOTARIFA;
    private String _TARIFA;
    private String _MANUAL;
    private String _PBULTOS;
    private String _TABLAZONA;
    private String _CODTARIFA;
    private boolean _ESTADO;

    public void setNOGUIA(String _NOGUIA) {
        this._NOGUIA = _NOGUIA;
    }

    public String getIDGUIA() {
        return _IDGUIA;
    }

    public void setIDGUIA(String _IDGUIA) {
        this._IDGUIA = _IDGUIA;
    }

    public String getPESO() {
        return _PESO;
    }

    public void setPESO(String _PESO) {
        this._PESO = _PESO;
    }

    public String getPESOTOTAL() {
        return _PESOTOTAL;
    }

    public void setPESOTOTAL(String _PESOTOTAL) {
        this._PESOTOTAL = _PESOTOTAL;
    }

    public String getCOSTOTARIFA() {
        return _COSTOTARIFA;
    }

    public void setCOSTOTARIFA(String _COSTOTARIFA) {
        this._COSTOTARIFA = _COSTOTARIFA;
    }

    public String getTARIFA() {
        return _TARIFA;
    }

    public void setTARIFA(String _TARIFA) {
        this._TARIFA = _TARIFA;
    }

    public boolean isESTADO() {
        return _ESTADO;
    }

    public void setESTADO(boolean _ESTADO) {
        this._ESTADO = _ESTADO;
    }

    public String getNOGUIA() {
        return _NOGUIA;
    }

    public String getLINEA() {
        return _LINEA;
    }

    public void setLINEA(String _LINEA) {
        if (_LINEA != null) {
            this._LINEA = _LINEA.trim();
        }
    }

    public int getPIEZAS() {
        return _PIEZAS;
    }

    public void setPIEZAS(int _PIEZAS) {
        this._PIEZAS = _PIEZAS;
    }

    public String getTIPOENVIO() {
        return _TIPOENVIO;
    }

    public void setTIPOENVIO(String _TIPOENVIO) {
        if (_TIPOENVIO != null) {
            this._TIPOENVIO = _TIPOENVIO.trim();
        }
    }

    public String getMANUAL() {
        return _MANUAL;
    }

    public void setMANUAL(String _MANUAL) {
        if (_MANUAL != null) {
            this._MANUAL = _MANUAL.trim();
        }
    }

    public String getPBULTOS() {
        return _PBULTOS;
    }

    public void setPBULTOS(String _PBULTOS) {
        if (_PBULTOS != null) {
            this._PBULTOS = _PBULTOS.trim();
        }
    }

    public String getDESCRIPCIONENVIO() {
        return _DESCRIPCIONENVIO;
    }

    public void setDESCRIPCIONENVIO(String _DESCRIPCIONENVIO) {
        this._DESCRIPCIONENVIO = _DESCRIPCIONENVIO;
    }

    public String getTABLAZONA() {
        return _TABLAZONA;
    }

    public void setTABLAZONA(String _TABLAZONA) {
        if (_TABLAZONA != null) {
            this._TABLAZONA = _TABLAZONA.trim();
        }
    }

    public String getCODTARIFA() {
        return _CODTARIFA;
    }

    public void setCODTARIFA(String _CODTARIFA) {
        if (_CODTARIFA != null) {
            this._CODTARIFA = _CODTARIFA.trim();
        }
    }

    public String getABVENVIO() {
        return _ABVENVIO;
    }

    public void setABVENVIO(String _ABVENVIO) {
        if (_ABVENVIO != null) {
            this._ABVENVIO = _ABVENVIO.trim();
        }
    }

    @Override
    public String toString() {
        return "E_DETALLELINEA{" + "_IDGUIA=" + _IDGUIA + ", _NOGUIA=" + _NOGUIA + ", _LINEA=" + _LINEA + ", _PIEZAS=" + _PIEZAS + ", _TIPOENVIO=" + _TIPOENVIO + ", _ABVENVIO=" + _ABVENVIO + ", _DESCRIPCIONENVIO=" + _DESCRIPCIONENVIO + ", _PESO=" + _PESO + ", _PESOTOTAL=" + _PESOTOTAL + ", _COSTOTARIFA=" + _COSTOTARIFA + ", _TARIFA=" + _TARIFA + ", _MANUAL=" + _MANUAL + ", _PBULTOS=" + _PBULTOS + ", _TABLAZONA=" + _TABLAZONA + ", _CODTARIFA=" + _CODTARIFA + '}';
    }
}
