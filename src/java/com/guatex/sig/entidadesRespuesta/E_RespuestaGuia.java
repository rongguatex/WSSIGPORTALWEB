package com.guatex.sig.entidadesRespuesta;

import com.guatex.sig.entidades.E_Guia;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class E_RespuestaGuia {

    private String _CODIGO;
    private String _MENSAJE;
    private List<E_Guia> _LISTADO_GUIAS = new LinkedList<>();

    public E_RespuestaGuia(String _CODIGO, List<E_Guia> _LISTADO_GUIAS) {
        this._CODIGO = _CODIGO;
        if (_CODIGO.equals("200")) {
            this._MENSAJE = "OK";
            this._LISTADO_GUIAS = _LISTADO_GUIAS;
        } else if (_CODIGO.equals("204")) {
            this._MENSAJE = "No se obtuvo información de la base de datos.";
        } else if (_CODIGO.equals("500")) {
            this._MENSAJE = "Ocurrió un error con la base de datos.";
        } else if (_CODIGO.equals("400")) {
            this._MENSAJE = "Ocurrió un error, no encuentra parametros";
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

    public List<E_Guia> getLISTADO_GUIAS() {
        return _LISTADO_GUIAS;
    }

    public void setLISTADO_GUIAS(List<E_Guia> _LISTADO_GUIAS) {
        this._LISTADO_GUIAS = _LISTADO_GUIAS;
    }

}
