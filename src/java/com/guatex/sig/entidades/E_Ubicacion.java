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
public class E_Ubicacion {

    private E_Departamento _DEPARTAMENTO;
    private E_Municipio _MUNICIPIO;
    private E_PuntoCobertura _COBERTURA;

    public E_Departamento getDEPARTAMENTO() {
        return _DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(E_Departamento _DEPARTAMENTO) {
        this._DEPARTAMENTO = _DEPARTAMENTO;
    }

    public E_Municipio getMUNICIPIO() {
        return _MUNICIPIO;
    }

    public void setMUNICIPIO(E_Municipio _MUNICIPIO) {
        this._MUNICIPIO = _MUNICIPIO;
    }

    public E_PuntoCobertura getCOBERTURA() {
        return _COBERTURA;
    }

    public void setCOBERTURA(E_PuntoCobertura _COBERTURA) {
        this._COBERTURA = _COBERTURA;
    }

    @Override
    public String toString() {
        return "-->\nE_Ubicacion{" + "\n_DEPARTAMENTO=" + _DEPARTAMENTO + ", \n_MUNICIPIO=" + _MUNICIPIO + ", \n_COBERTURA=" + _COBERTURA + "\n" + '}';
    }

}
