/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "RESPUESTA")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespuestaGeneral {

    @XmlElement(name = "CODIGO")
    private String codigo = "";
    
    @XmlElement(name = "MENSAJE")
    private String mensaje = "";
    
    private List<String> errores;

    public RespuestaGeneral(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public RespuestaGeneral(String codigo, String mensaje, List<String> errores) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public RespuestaGeneral() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    @Override
    public String toString() {
        return "RespuestaCodigoMensaje{" + "codigo=" + codigo + ", mensaje=" + mensaje + ", errores=" + errores + '}';
    }

}
