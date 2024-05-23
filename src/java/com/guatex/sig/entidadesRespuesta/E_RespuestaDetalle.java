/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidadesRespuesta;

import com.guatex.sig.entidades.E_DetalleLinea;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class E_RespuestaDetalle {

    private String _CODIGO;
    private String _MENSAJE;
    private List<E_DetalleLinea> _DETALLE_GUIA = new LinkedList<>();

    public E_RespuestaDetalle(String _CODIGO, List<E_DetalleLinea> _DETALLE_GUIA) {
        this._CODIGO = _CODIGO;
        if (_CODIGO.equals("200")) {
            this._MENSAJE = "OK";
            this._DETALLE_GUIA = _DETALLE_GUIA;
        } else if (_CODIGO.equals("204")) {
            this._MENSAJE = "No se obtuvo información de la base de datos.";
        } else if (_CODIGO.equals("500")) {
            this._MENSAJE = "Ocurrió un error con la base de datos.";
        }
    }

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        this._CODIGO = _CODIGO;
    }

    public String getMENSAJE() {
        return _MENSAJE;
    }

    public void setMENSAJE(String _MENSAJE) {
        this._MENSAJE = _MENSAJE;
    }

    public List<E_DetalleLinea> getDETALLE_GUIA() {
        return _DETALLE_GUIA;
    }

    public void setDETALLE_GUIA(List<E_DetalleLinea> _DETALLE_GUIA) {
        this._DETALLE_GUIA = _DETALLE_GUIA;
    }

}
