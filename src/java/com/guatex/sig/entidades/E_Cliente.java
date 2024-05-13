package com.guatex.sig.entidades;

public class E_Cliente {

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
}
