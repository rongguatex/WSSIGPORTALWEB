package com.guatex.sig.entidades;

import com.guatex.sig.utils.Utils;

public class E_Cliente {
    Utils util = new Utils();

    private boolean _SELECCIONADO = false;
    private String _CODCOB = "";
    private String _CODIGO = "";
    private String _NIT = "";
    private String _TELEFONO = "";
    private String _NOMBRE = "";
    private String _DIRECCION = "";
    private String _CONTACTO = "";
    private String _UBICACION = "";
    private String _PUNTO = "";
    private String _CORREO = "";
    private String _CAMPO1 = "";
    private String _CAMPO2 = "";
    private String _CAMPO3 = "";
    private String _CAMPO4 = "";
    private String _PADRE = "";
    private String _UNIFICACLI = "";
    private String _RECOGEOFICINA = "";

    private E_PuntoCobertura __COBERTURA = new E_PuntoCobertura();
    private E_Departamento _DEPARTAMENTO = new E_Departamento();
    private E_Municipio _MUNICIPIO = new E_Municipio();

    public E_Cliente(String punto, String ubicacion) {
        this._PUNTO = punto;
        this._UBICACION = ubicacion;
    }

    public E_Cliente() {
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
        return util.validarLogitud(_CODIGO, 15);
    }

    public void setCODIGO(String _CODIGO) {
            this._CODIGO = util.validarLogitud(_CODIGO, 15);
    }

    public String getNIT() {
        return util.validarLogitud(_NIT, 25);
    }

    public void setNIT(String _NIT) {
        this._NIT = util.validarLogitud(_NIT, 25);
    }

    public String getTELEFONO() {
        return util.validarLogitud(_TELEFONO, 20);
    }

    public void setTELEFONO(String _TELEFONO) {
        this._TELEFONO = util.validarLogitud(_TELEFONO, 20);
    }

    public String getNOMBRE() {
        return util.validarLogitud(_NOMBRE, 100);
    }

    public void setNOMBRE(String _NOMBRE) {
        this._NOMBRE = util.validarLogitud(_NOMBRE, 100);
    }

    public String getDIRECCION() {
        return util.validarLogitud(_DIRECCION, 100);
    }

    public void setDIRECCION(String _DIRECCION) {
        this._DIRECCION = util.validarLogitud(_DIRECCION, 100);
    }

    public String getCONTACTO() {
        return util.validarLogitud(_CONTACTO, 200);
    }

    public void setCONTACTO(String _CONTACTO) {
        this._CONTACTO = util.validarLogitud(_CONTACTO, 200);
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
        return util.validarLogitud(_CORREO, 200);
    }

    public void setCORREO(String _CORREO) {
        this._CORREO = util.validarLogitud(_CORREO, 200);
    }

    public String getCAMPO1() {
        return util.validarLogitud(_CAMPO1, 200);
    }

    public void setCAMPO1(String _CAMPO1) {
        this._CAMPO1 = util.validarLogitud(_CAMPO1, 200);
    }

    public String getCAMPO2() {
        return util.validarLogitud(_CAMPO2, 200);
    }

    public void setCAMPO2(String _CAMPO2) {
        this._CAMPO2 = util.validarLogitud(_CAMPO2, 200);
    }

    public String getCAMPO3() {
        return util.validarLogitud(_CAMPO3, 200);
    }

    public void setCAMPO3(String _CAMPO3) {
        this._CAMPO3 = util.validarLogitud(_CAMPO3, 200);
    }

    public String getCAMPO4() {
        return util.validarLogitud(_CAMPO4, 200);
    }

    public void setCAMPO4(String _CAMPO4) {
        this._CAMPO4 = util.validarLogitud(_CAMPO4, 200);
    }

    public String getPADRE() {
        return _PADRE;
    }

    public void setPADRE(String _PADRE) {
        this._PADRE = _PADRE;
    }
    
    @Override
    public String toString() {
        return "\n\nE_Cliente{" + "_SELECCIONADO=" + _SELECCIONADO + ", _CODCOB=" + _CODCOB + ", _CODIGO=" + _CODIGO + ", _NIT=" + _NIT + ", \n"
                + "_TELEFONO=" + _TELEFONO + ", _NOMBRE=" + _NOMBRE + ", _DIRECCION=" + _DIRECCION + ", _CONTACTO=" + _CONTACTO + ", \n"
                + "_UBICACION=" + _UBICACION + ", _PUNTO=" + _PUNTO + ", _CORREO=" + _CORREO + ", \n"
                + "_CAMPO1=" + _CAMPO1 + ", _CAMPO2=" + _CAMPO2 + ", _CAMPO3=" + _CAMPO3 + ", _CAMPO4=" + _CAMPO4 + ", \n"
                + "_PADRE=" + _PADRE + ", _UNIFICACLI=" + _UNIFICACLI + ", _RECOGEOFICINA=" + _RECOGEOFICINA + ", \n"
                + "__COBERTURA=" + __COBERTURA + ", \n"
                + "_DEPARTAMENTO=" + _DEPARTAMENTO + ",\n"
                + " _MUNICIPIO=" + _MUNICIPIO + '}';
    }
}
