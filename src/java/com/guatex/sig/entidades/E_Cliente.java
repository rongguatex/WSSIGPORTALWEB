package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CLIENTE")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Cliente {

    private boolean _SELECCIONADO = false;
    
    @XmlElement(name = "CODCOB")
    private String _CODCOB = "";
    
    @XmlElement(name = "CODIGO")
    private String _CODIGO = "";
    
    @XmlElement(name = "NIT")
    private String _NIT = "";
    
    @XmlElement(name = "TELEFONO")
    private String _TELEFONO = "";
    
    @XmlElement(name = "NOMBRE")
    private String _NOMBRE = "";
    
    @XmlElement(name = "DIRECCION")
    private String _DIRECCION = "";
    
    @XmlElement(name = "CONTACTO")
    private String _CONTACTO = "";
    
    @XmlElement(name = "UBICACION")
    private String _UBICACION = "";
    
    @XmlElement(name = "PUNTO")
    private String _PUNTO = "";
    
    @XmlElement(name = "CORREO")
    private String _CORREO = "";
    
    @XmlElement(name = "CAMPO1")
    private String _CAMPO1 = "";
    
    @XmlElement(name = "CAMPO2")
    private String _CAMPO2 = "";
    
    @XmlElement(name = "CAMPO3")
    private String _CAMPO3 = "";
    
    @XmlElement(name = "CAMPO4")
    private String _CAMPO4 = "";
    
    @XmlElement(name = "PADRE")
    private String _PADRE = "";
    
    @XmlElement(name = "UNIFICACLI")
    private String _UNIFICACLI = "";
    
    @XmlElement(name = "RECOGEOFICINA")
    private String _RECOGEOFICINA = "";

    @XmlElement(name = "DEPARTAMENTO")
    private E_Departamento _DEPARTAMENTO = new E_Departamento();
    
    @XmlElement(name = "MUNICIPIO")
    private E_Municipio _MUNICIPIO = new E_Municipio();
    
    @XmlElement(name = "COBERTURA")
    private E_PuntoCobertura __COBERTURA = new E_PuntoCobertura();

    public E_Cliente(String punto, String ubicacion) {
        this._PUNTO = punto;
        this._UBICACION = ubicacion;
    }

    public E_Cliente() {
    }
    
    public E_Cliente(E_Credenciales credenciales) {
        this._PADRE = credenciales.getPadre();
        this._CODCOB = credenciales.getCodcob();
        this._CODIGO = credenciales.getCodigo();
    }

    public E_Cliente(String padre, String codcob, String codigo) {
        this._PADRE = padre;
        this._CODCOB = codcob;
        this._CODIGO = codigo;
    }
    
    public boolean isSELECCIONADO() {
        return _SELECCIONADO;
    }

    public E_PuntoCobertura getCOBERTURA() {
        return __COBERTURA;
    }

    public void setCOBERTURA(E_PuntoCobertura __COBERTURA) {
        this.__COBERTURA = __COBERTURA;
    }

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

    public void setSELECCIONADO(boolean _SELECCIONADO) {
        this._SELECCIONADO = _SELECCIONADO;
    }

    public String getRECOGEOFICINA() {
        return _RECOGEOFICINA;
    }

    public void setRECOGEOFICINA(String _RECOGEOFICINA) {
        this._RECOGEOFICINA = _RECOGEOFICINA;
    }

    public String getUNIFICACLI() {
        return _UNIFICACLI;
    }

    public void setUNIFICACLI(String _UNIFICACLI) {
        if (_UNIFICACLI == null || _UNIFICACLI.isEmpty()) {
            _UNIFICACLI = "N";
        }
        this._UNIFICACLI = _UNIFICACLI;
    }

    public String getCODCOB() {
        return _CODCOB;
    }

    public void setCODCOB(String _CODCOB) {
        this._CODCOB = _CODCOB;
    }

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        this._CODIGO = _CODIGO;
    }

    public String getNIT() {
        return _NIT;
    }

    public void setNIT(String _NIT) {
        this._NIT = _NIT;
    }

    public String getTELEFONO() {
        return _TELEFONO;
    }

    public void setTELEFONO(String _TELEFONO) {
        this._TELEFONO = _TELEFONO;
    }

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        this._NOMBRE = _NOMBRE;
    }

    public String getDIRECCION() {
        return _DIRECCION;
    }

    public void setDIRECCION(String _DIRECCION) {
        this._DIRECCION = _DIRECCION;
    }

    public String getCONTACTO() {
        return _CONTACTO;
    }

    public void setCONTACTO(String _CONTACTO) {
        this._CONTACTO = _CONTACTO;
    }

    public String getUBICACION() {
        return _UBICACION;
    }

    public void setUBICACION(String _UBICACION) {
        this._UBICACION = _UBICACION;
    }

    public String getPUNTO() {
        return _PUNTO;
    }

    public void setPUNTO(String _PUNTO) {
        this._PUNTO = _PUNTO;
    }

    public String getCORREO() {
        return _CORREO;
    }

    public void setCORREO(String _CORREO) {
        this._CORREO = _CORREO;
    }

    public String getCAMPO1() {
        return _CAMPO1;
    }

    public void setCAMPO1(String _CAMPO1) {
        this._CAMPO1 = _CAMPO1;
    }

    public String getCAMPO2() {
        return _CAMPO2;
    }

    public void setCAMPO2(String _CAMPO2) {
        this._CAMPO2 = _CAMPO2;
    }

    public String getCAMPO3() {
        return _CAMPO3;
    }

    public void setCAMPO3(String _CAMPO3) {
        this._CAMPO3 = _CAMPO3;
    }

    public String getCAMPO4() {
        return _CAMPO4;
    }

    public void setCAMPO4(String _CAMPO4) {
        this._CAMPO4 = _CAMPO4;
    }

    public String getPADRE() {
        return _PADRE;
    }

    public void setPADRE(String _PADRE) {
        this._PADRE = _PADRE;
    }

    @Override
    public String toString() {
        return "E_Cliente{" + "_CODCOB=" + _CODCOB + ", _CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ", _UBICACION=" + _UBICACION + ", _PUNTO=" + _PUNTO + ", _PADRE=" + _PADRE + ", _UNIFICACLI=" + _UNIFICACLI + ", __COBERTURA=" + __COBERTURA + ", _DEPARTAMENTO=" + _DEPARTAMENTO + ", _MUNICIPIO=" + _MUNICIPIO + '}';
    }
}
