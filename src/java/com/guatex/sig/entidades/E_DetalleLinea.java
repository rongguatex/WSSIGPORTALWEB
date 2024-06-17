/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "LINEADETALLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_DetalleLinea {

    @XmlElement(name = "IDGUIA")
    private String _IDGUIA;
    
    @XmlElement(name = "NOGUIA")
    private String _NOGUIA;
    
    @XmlElement(name = "LINEA")
    private String _LINEA;
    
    @XmlElement(name = "PIEZAS")
    private int _PIEZAS;
    
    @XmlElement(name = "TIPOENVIO")
    private String _TIPOENVIO;
    
    @XmlElement(name = "ABVENVIO")
    private String _ABVENVIO;
    
    @XmlElement(name = "DESCRIPCIONENVIO")
    private String _DESCRIPCIONENVIO;
    
    @XmlElement(name = "PESO")
    private String _PESO;
    
    @XmlElement(name = "PESOTOTAL")
    private String _PESOTOTAL;
    
    @XmlElement(name = "COSTOTARIFA")
    private String _COSTOTARIFA;
    
    @XmlElement(name = "TARIFA")
    private String _TARIFA;
    
    @XmlElement(name = "MANUAL")
    private String _MANUAL;
    
    @XmlElement(name = "PBULTOS")
    private String _PBULTOS;
    
    @XmlElement(name = "TABLAZONA")
    private String _TABLAZONA;
    
    @XmlElement(name = "CODTARIFA")
    private String _CODTARIFA;
    
    @XmlElement(name = "ESTADO")
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
        return "\nE_DETALLELINEA{" + "\n"
                + "     _IDGUIA=" + _IDGUIA + ", \n"
                + "     _NOGUIA=" + _NOGUIA + ",\n"
                + "      _LINEA=" + _LINEA + ",\n"
                + "      _PIEZAS=" + _PIEZAS + ", \n"
                + "     _TIPOENVIO=" + _TIPOENVIO + ",\n"
                + "      _ABVENVIO=" + _ABVENVIO + ",\n"
                + "      _DESCRIPCIONENVIO=" + _DESCRIPCIONENVIO + ",\n"
                + "      _PESO=" + _PESO + ",\n"
                + "      _PESOTOTAL=" + _PESOTOTAL + ", \n"
                + "     _COSTOTARIFA=" + _COSTOTARIFA + ",\n"
                + "      _TARIFA=" + _TARIFA + ", \n"
                + "     _MANUAL=" + _MANUAL + ",\n"
                + "     _PBULTOS=" + _PBULTOS + ", \n"
                + "     _TABLAZONA=" + _TABLAZONA + ", \n"
                + "     _CODTARIFA=" + _CODTARIFA + "\n"+ '}';
    }
}
