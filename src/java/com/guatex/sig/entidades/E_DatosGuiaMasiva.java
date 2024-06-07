/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "DATOSGUIA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_DatosGuiaMasiva {

    @XmlElement(name = "CODIGO")
    private String _CODIGO;

    @XmlElement(name = "NOMBRE")
    private String _NOMBRE;

    @XmlElement(name = "TELEFONO")
    private String _TELEFONO;

    @XmlElement(name = "DIRECCION")
    private String _DIRECCION;

    @XmlElement(name = "CODIGO_DESTINATARIO")
    private String _CODIGO_DESTINATARIO;

    @XmlElement(name = "MUNICIPIO_DESTINATARIO")
    private String _MUNICIPIO_DESTINATARIO;

    @XmlElement(name = "TIPO_PIEZA_PESO")
    private String _TIPO_PIEZA_PESO;

    @XmlElement(name = "DESCRIPCION")
    private String _DESCRIPCION;

    @XmlElement(name = "LLAVE")
    private String _LLAVE;

    @XmlElement(name = "COD")
    private String _COD;

    @XmlElement(name = "PRECIO")
    private String _PRECIO;

//    @XmlList
    @XmlElement(name = "ESTADO")
    private List<String> _ESTADO;

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

    public String getTELEFONO() {
        return _TELEFONO;
    }

    public void setTELEFONO(String _TELEFONO) {
        this._TELEFONO = _TELEFONO;
    }

    public String getDIRECCION() {
        return _DIRECCION;
    }

    public void setDIRECCION(String _DIRECCION) {
        this._DIRECCION = _DIRECCION;
    }

    public String getCODIGO_DESTINATARIO() {
        return _CODIGO_DESTINATARIO;
    }

    public void setCODIGO_DESTINATARIO(String _CODIGO_DESTINATARIO) {
        this._CODIGO_DESTINATARIO = _CODIGO_DESTINATARIO;
    }

    public String getMUNICIPIO_DESTINATARIO() {
        return _MUNICIPIO_DESTINATARIO;
    }

    public void setMUNICIPIO_DESTINATARIO(String _MUNICIPIO_DESTINATARIO) {
        this._MUNICIPIO_DESTINATARIO = _MUNICIPIO_DESTINATARIO;
    }

    public String getTIPO_PIEZA_PESO() {
        return _TIPO_PIEZA_PESO;
    }

    public void setTIPO_PIEZA_PESO(String _TIPO_PIEZA_PESO) {
        this._TIPO_PIEZA_PESO = _TIPO_PIEZA_PESO;
    }

    public String getDESCRIPCION() {
        return _DESCRIPCION;
    }

    public void setDESCRIPCION(String _DESCRIPCION) {
        this._DESCRIPCION = _DESCRIPCION;
    }

    public String getLLAVE() {
        return _LLAVE;
    }

    public void setLLAVE(String _LLAVE) {
        this._LLAVE = _LLAVE;
    }

    public String getCOD() {
        return _COD;
    }

    public void setCOD(String _COD) {
        this._COD = _COD;
    }

    public String getPRECIO() {
        return _PRECIO;
    }

    public void setPRECIO(String _PRECIO) {
        this._PRECIO = _PRECIO;
    }

    public List<String> getESTADO() {
        List<String> mensajes = new ArrayList<>();
        _ESTADO.forEach((estado) -> {
            if (!estado.isEmpty()) {
                String[] partes = estado.split(", ");
                mensajes.addAll(Arrays.asList(partes));
            }
        });
        return new ArrayList<>(mensajes);
    }
    
    public void setESTADO(List<String> _ESTADO) {
        this._ESTADO = _ESTADO;
    }

    public void AddStateLastPosition(String newState) {
        _ESTADO.add(newState);
    }

    public void AddStateFirstPosition(String newState) {
        _ESTADO.add(0, newState);
    }

    public void AddStatesErrorList(List<String> states) {
        _ESTADO.addAll(states);
    }

    @Override
    public String toString() {
        return "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _TELEFONO=" + _TELEFONO + ", _DIRECCION=" + _DIRECCION + ", _CODIGO_DESTINATARIO=" + _CODIGO_DESTINATARIO + ", _MUNICIPIO_DESTINATARIO=" + _MUNICIPIO_DESTINATARIO + ", _TIPO_PIEZA_PESO=" + _TIPO_PIEZA_PESO + ", _DESCRIPCION=" + _DESCRIPCION + ", _LLAVE=" + _LLAVE + ", _COD=" + _COD + ", _PRECIO=" + _PRECIO + ", _ESTADO=" + _ESTADO;
    }
}
