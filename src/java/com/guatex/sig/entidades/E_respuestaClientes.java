package com.guatex.sig.entidades;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RESPUESTA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_respuestaClientes {

    @XmlElement(name = "CODIGO")
    private String _CODIGO = "";

    @XmlElement(name = "MENSAJE")
    private String _MENSAJE = "";

    @XmlElementWrapper(name = "LISTADO_CLIENTES")
    @XmlElement(name = "CLIENTE")
    private List<E_Cliente> _DATOS_CLIENTES;

    public E_respuestaClientes() {
    }

    public E_respuestaClientes(String pCodigo, List<E_Cliente> pListaClientes) {
        this._CODIGO = pCodigo;
        this._DATOS_CLIENTES = pListaClientes;
        if (pCodigo.equalsIgnoreCase("200")) {
            this._MENSAJE = "OK";
            this._DATOS_CLIENTES = pListaClientes;
        } else if (pCodigo.equalsIgnoreCase("500")) {
            this._MENSAJE = "Error en el servidor";
        } else if (pCodigo.equalsIgnoreCase("204")) {
            this._MENSAJE = "No existen coincidencias en la base de datos.";
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

    public List<E_Cliente> getDATOS_CLIENTES() {
        return _DATOS_CLIENTES;
    }

    public void setDATOS_CLIENTES(List<E_Cliente> _DATOS_CLIENTES) {
        this._DATOS_CLIENTES = _DATOS_CLIENTES;
    }

    @Override
    public String toString() {
        return "E_respuestaClientes{" + "_CODIGO=" + _CODIGO + ", _MENSAJE=" + _MENSAJE + ", _DATOS_CLIENTES=" + _DATOS_CLIENTES + '}';
    }

}
