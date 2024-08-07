/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private String _PTODES;

    /**
     * Datos de remitente
     */
    private String _CODREM;
    private String _NOMREM;
    private String _TELREM;
    private String _DIRREM;
    private String _PTOREM;
    private String _MUNIREM;
    private String _CODCOB;

    private String _CODTARIFA;
    private String _COBEX;
    private String _RUTAZONA;
    private String _CODIGOCLIENTE;

    private E_Cliente _CLIENTE;

    private E_TarifaMuni _TARIFA_ORIGEN;
    private E_TarifaMuni _TARIFA_DESTINO;
    private List<E_DetalleLinea> _DETALLE;
    private List<E_Tarificador> _TARIFICADOR;

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

    public String getCODREM() {
        return _CODREM;
    }

    public void setCODREM(String _CODREM) {
        this._CODREM = _CODREM;
    }

    public String getNOMREM() {
        return _NOMREM;
    }

    public void setNOMREM(String _NOMREM) {
        this._NOMREM = _NOMREM;
    }

    public String getTELREM() {
        return _TELREM;
    }

    public void setTELREM(String _TELREM) {
        this._TELREM = _TELREM;
    }

    public String getDIRREM() {
        return _DIRREM;
    }

    public void setDIRREM(String _DIRREM) {
        this._DIRREM = _DIRREM;
    }

    public String getPTOREM() {
        return _PTOREM;
    }

    public void setPTOREM(String _PTOREM) {
        this._PTOREM = _PTOREM;
    }

    public String getPTODES() {
        return _PTODES;
    }

    public void setPTODES(String _PTODES) {
        this._PTODES = _PTODES;
    }

    public String getMUNIREM() {
        return _MUNIREM;
    }

    public void setMUNIREM(String _MUNIREM) {
        this._MUNIREM = _MUNIREM;
    }

    public String getCODCOB() {
        return _CODCOB;
    }

    public void setCODCOB(String _CODCOB) {
        this._CODCOB = _CODCOB;
    }

    public String getCODTARIFA() {
        return _CODTARIFA;
    }

    public void setCODTARIFA(String _CODTARIFA) {
        this._CODTARIFA = _CODTARIFA;
    }

    public String getCOBEX() {
        return _COBEX;
    }

    public void setCOBEX(String _COBEX) {
        this._COBEX = _COBEX;
    }

    public String getRUTAZONA() {
        return _RUTAZONA;
    }

    public void setRUTAZONA(String _RUTAZONA) {
        this._RUTAZONA = _RUTAZONA;
    }

    public String getCODIGOCLIENTE() {
        return _CODIGOCLIENTE;
    }

    public void setCODIGOCLIENTE(String _CODIGOCLIENTE) {
        this._CODIGOCLIENTE = _CODIGOCLIENTE;
    }

    public E_TarifaMuni getTARIFA_ORIGEN() {
        return _TARIFA_ORIGEN;
    }

    public void setTARIFA_ORIGEN(E_TarifaMuni _TARIFA_ORIGEN) {
        this._TARIFA_ORIGEN = _TARIFA_ORIGEN;
    }

    public E_TarifaMuni getTARIFA_DESTINO() {
        return _TARIFA_DESTINO;
    }

    public void setTARIFA_DESTINO(E_TarifaMuni _TARIFA_DESTINO) {
        this._TARIFA_DESTINO = _TARIFA_DESTINO;
    }

    public List<E_DetalleLinea> getDETALLE() {
        return _DETALLE;
    }

    public void setDETALLE(List<E_DetalleLinea> _DETALLE) {
        this._DETALLE = _DETALLE;
    }

    public E_Cliente getCLIENTE() {
        return _CLIENTE;
    }

    public void setCLIENTE(E_Cliente _CLIENTE) {
        this._CLIENTE = _CLIENTE;
    }

    public List<E_Tarificador> getTARIFICADOR() {
        return _TARIFICADOR;
    }

    public void setTARIFICADOR(List<E_Tarificador> _TARIFICADOR) {
        this._TARIFICADOR = _TARIFICADOR;
    }

    @Override
    public String toString() {
        return "\n"
                + "E_DatosGuiaMasiva{" + "\n"
                + "     _CODIGO=" + _CODIGO 
                + ", _NOMBRE=" + _NOMBRE 
                + ", _TELEFONO=" + _TELEFONO 
                + ", _DIRECCION=" + _DIRECCION 
                + ",  _CODIGO_DESTINATARIO=" + _CODIGO_DESTINATARIO 
                + ", _MUNICIPIO_DESTINATARIO=" + _MUNICIPIO_DESTINATARIO
                + ",  _TIPO_PIEZA_PESO=" + _TIPO_PIEZA_PESO + 
                ", _DESCRIPCION=" + _DESCRIPCION 
                + ", _LLAVE=" + _LLAVE 
                + ",  _COD=" + _COD 
                + ", _PRECIO=" + _PRECIO 
                + ", _PTODES=" + _PTODES 
                + ", _CODREM=" + _CODREM 
                + ", _NOMREM=" + _NOMREM 
                + ", _TELREM=" + _TELREM 
                + ", _DIRREM=" + _DIRREM
                + ", _PTOREM=" + _PTOREM 
                + ", _MUNIREM=" + _MUNIREM 
                + ", _CODCOB=" + _CODCOB 
                + ", _CODTARIFA=" + _CODTARIFA 
                + ", _COBEX=" + _COBEX 
                + ", _RUTAZONA=" + _RUTAZONA 
                + ", _CODIGOCLIENTE=" + _CODIGOCLIENTE 
                + ", _ESTADO=" + _ESTADO 
                + ", _CLIENTE= " + _CLIENTE + ", \n"
                + "         _TARIFA_ORIGEN=     " + _TARIFA_ORIGEN + ", \n"
                + "         _TARIFA_DESTINO=        " + _TARIFA_DESTINO + ", \n"
                + "         _DETALLE=       " + _DETALLE + ", \n"
                + "         _TARIFICADOR=       " + _TARIFICADOR + "\n" + '}';
    }

}
