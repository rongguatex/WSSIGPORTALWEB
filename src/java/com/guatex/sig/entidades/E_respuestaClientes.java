package com.guatex.sig.entidades;

import java.util.List;

public class E_respuestaClientes {

    private String _CODIGO = "";
    private String _MENSAJE = "";
    private List<E_Cliente> _DATOS_CLIENTES;

    public E_respuestaClientes(String pCodigo, List<E_Cliente> pListaClientes) {
        this._CODIGO = pCodigo;
        this._DATOS_CLIENTES = pListaClientes;
        if (pCodigo.equalsIgnoreCase("200")) {
            this._MENSAJE = "OK";
            this._DATOS_CLIENTES = pListaClientes;
        } else if (pCodigo.equalsIgnoreCase("500")) {
            this._MENSAJE = "Error en el servidor";
        } else if (pCodigo.equalsIgnoreCase("204")) {
            this._MENSAJE = "No se obtuvo información de la base de datos";
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

}
