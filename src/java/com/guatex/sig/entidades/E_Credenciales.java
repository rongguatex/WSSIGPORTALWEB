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

    @XmlElement(name = "CODIGO")
    private String codigo;

    @XmlElement(name = "PASSWORD")
    private String password;

    @XmlElement(name = "CODCOB")
    private String codcob;
    
    @XmlElement(name = "NOGUIA")
    private String noguia;
    
    @XmlElement(name = "TIPO")
    private String tipo;

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

    public String getUsuario() {
        return padre + "/" + codigo;
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

    @Override
    public String toString() {
        if (padre != null && !padre.isEmpty()
                && codigo != null && !codigo.isEmpty()
                && password != null && !password.isEmpty()) {
            return "E_Credenciales{ noguia= "+ noguia + "padre=" + padre + ", codigo=" + codigo + ", password= " + password + ", password= " + tipo + '}';
        }
        return null;
    }
}
