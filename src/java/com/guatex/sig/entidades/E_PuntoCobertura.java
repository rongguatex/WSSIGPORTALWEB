package com.guatex.sig.entidades;

public class E_PuntoCobertura {

    private String FRECUENCIA;
    private String DEPARTAMENTO;
    private String MUNICIPIO;
    private String UBICACION;
    private String PUNTO;
    private String CODIGOPUNTO;
    private boolean RECOGEOFICINA = false;
    private char LUNES = ' ';
    private char MARTES = ' ';
    private char MIERCOLES = ' ';
    private char JUEVES = ' ';
    private char VIERNES = ' ';
    private char SABADO = ' ';

    public String getFRECUENCIA() {
        return FRECUENCIA;
    }

    public void setFRECUENCIA(String FRECUENCIA) {
        this.FRECUENCIA = FRECUENCIA;
    }

    public String getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(String DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }

    public String getMUNICIPIO() {
        return MUNICIPIO;
    }

    public void setMUNICIPIO(String MUNICIPIO) {
        this.MUNICIPIO = MUNICIPIO;
    }

    public String getUBICACION() {
        return UBICACION;
    }

    public void setUBICACION(String UBICACION) {
        this.UBICACION = UBICACION;
    }

    public String getPUNTO() {
        return PUNTO;
    }

    public void setPUNTO(String PUNTO) {
        this.PUNTO = PUNTO;
    }

    public String getCODIGOPUNTO() {
        return CODIGOPUNTO;
    }

    public void setCODIGOPUNTO(String CODIGOPUNTO) {
        this.CODIGOPUNTO = CODIGOPUNTO;
    }

    public boolean isRECOGEOFICINA() {
        return RECOGEOFICINA;
    }

    public void setRECOGEOFICINA(boolean RECOGEOFICINA) {
        this.RECOGEOFICINA = RECOGEOFICINA;
    }

    public char getLUNES() {
        return LUNES;
    }

    public void setLUNES(char LUNES) {
        this.LUNES = LUNES;
    }

    public char getMARTES() {
        return MARTES;
    }

    public void setMARTES(char MARTES) {
        this.MARTES = MARTES;
    }

    public char getMIERCOLES() {
        return MIERCOLES;
    }

    public void setMIERCOLES(char MIERCOLES) {
        this.MIERCOLES = MIERCOLES;
    }

    public char getJUEVES() {
        return JUEVES;
    }

    public void setJUEVES(char JUEVES) {
        this.JUEVES = JUEVES;
    }

    public char getVIERNES() {
        return VIERNES;
    }

    public void setVIERNES(char VIERNES) {
        this.VIERNES = VIERNES;
    }

    public char getSABADO() {
        return SABADO;
    }

    public void setSABADO(char SABADO) {
        this.SABADO = SABADO;
    }

    @Override
    public String toString() {
        return "E_PuntoCobertura{" + "FRECUENCIA=" + FRECUENCIA + ", DEPARTAMENTO=" + DEPARTAMENTO + ", MUNICIPIO=" + MUNICIPIO + ", \n"
                + "UBICACION=" + UBICACION + ", PUNTO=" + PUNTO + ", CODIGOPUNTO=" + CODIGOPUNTO + ", \n"
                + "RECOGEOFICINA=" + RECOGEOFICINA + ", LUNES=" + LUNES + ", MARTES=" + MARTES + ", MIERCOLES=" + MIERCOLES + ", JUEVES=" + JUEVES + ", VIERNES=" + VIERNES + ", SABADO=" + SABADO + '}';
    }
}
