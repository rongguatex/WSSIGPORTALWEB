/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "CREDENCIALES")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Credenciales {

    @XmlElement(name = "PADRE")
    private String padre;

    @XmlElement(name = "USUARIO")
    private String usuario;

    @XmlElement(name = "PASSWORD")
    private String password;

    @XmlElement(name = "CODCOB")
    private String codcob;

    @XmlElement(name = "CODIGO")
    private String codigo;

    @XmlElement(name = "NOGUIA")
    private String noguia;

    @XmlElement(name = "TIPO")
    private String tipo;

    @XmlElement(name = "FECHAINICIO")
    private String fechaInicio;

    @XmlElement(name = "FECHAFINAL")
    private String fechaFinal;

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodcob() {
        return codcob;
    }

    public void setCodcob(String codcob) {
        this.codcob = codcob;
    }

    public String getNoguia() {
        return noguia;
    }

    public void setNoguia(String noguia) {
        this.noguia = noguia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getUsuarioCompuesto() {
        return padre + "/" + usuario;
    }

    @Override
    public String toString() {
        return "E_Credenciales{" + "padre=" + padre + ", usuario=" + usuario + ", password=" + password
                + ", codcob=" + codcob + ", codigo=" + codigo + ", noguia=" + noguia + ", tipo=" + tipo
                + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", usuarioCompuesto " + getUsuarioCompuesto() + '}';
    }

}
