package com.guatex.sig.entidadesRespuesta;

import com.guatex.sig.entidades.E_Guia;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "RESPUESTA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_RespuestaGuia {

    @XmlElement(name = "CODIGO")
    private String _CODIGO;

    @XmlElement(name = "MENSAJE")
    private String _MENSAJE;

    @XmlElementWrapper(name = "LISTADODATOSGUIA")
    @XmlElement(name = "DATOSGUIA")
    private List<E_Guia> _LISTADO_GUIAS = new LinkedList<>();

    public E_RespuestaGuia() {
    }

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

    public E_RespuestaGuia(String _CODIGO) {
        this._CODIGO = _CODIGO;
        if (_CODIGO.equals("200")) {
            this._MENSAJE = "OK";
        } else if (_CODIGO.equals("204")) {
            this._MENSAJE = "No se encontraron datos para la fecha solicitada.";
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

    @Override
    public String toString() {
        return "E_RespuestaGuia{" + "_CODIGO=" + _CODIGO + ", _MENSAJE=" + _MENSAJE + ", _LISTADO_GUIAS=" + _LISTADO_GUIAS + '}';
    }

}
