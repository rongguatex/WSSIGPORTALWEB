/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 * @param <T>
 */
@XmlRootElement(name = "WSSIGCLIENTES")
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSIGCLIENTES<T> {

    @XmlElement(name = "CREDENCIALES")
    E_Credenciales credenciales;

    @XmlAnyElement(lax = true)
    private T datosEntrada;

    public E_Credenciales getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(E_Credenciales credenciales) {
        this.credenciales = credenciales;
    }

    public T getDatosEntrada() {
        return datosEntrada;
    }

    public void setDatosEntrada(T datosEntrada) {
        this.datosEntrada = datosEntrada;
    }

    @Override
    public String toString() {
        return "WSSIGCLIENTES{\n"
                + "         credenciales=" + credenciales + ", \n"
                + "         datosEntrada=" + datosEntrada + "\n"
                + '}';
    }

}
