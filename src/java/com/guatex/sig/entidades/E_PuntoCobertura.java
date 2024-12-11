package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "COBERTURA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_PuntoCobertura {

    @XmlElement(name = "FRECUENCIA")
    private String FRECUENCIA;

    @XmlElement(name = "DEPARTAMENTO")
    private String DEPARTAMENTO;

    @XmlElement(name = "MUNICIPIO")
    private String MUNICIPIO;

    @XmlElement(name = "UBICACION")
    private String UBICACION;

    @XmlElement(name = "PUNTO")
    private String PUNTO;

    @XmlElement(name = "CODIGOPUNTO")
    private String CODIGOPUNTO;

    @XmlElement(name = "RECOGEOFICINA")
    private boolean RECOGEOFICINA = false;

    @XmlElement(name = "LUNES")
    private char LUNES = ' ';

    @XmlElement(name = "MARTES")
    private char MARTES = ' ';

    @XmlElement(name = "MIERCOLES")
    private char MIERCOLES = ' ';

    @XmlElement(name = "JUEVES")
    private char JUEVES = ' ';

    @XmlElement(name = "VIERNES")
    private char VIERNES = ' ';

    @XmlElement(name = "SABADO")
    private char SABADO = ' ';

    public E_PuntoCobertura() {
    }

    public E_PuntoCobertura(String MUNICIPIO, String PUNTO) {
        this.MUNICIPIO = MUNICIPIO;
        this.PUNTO = PUNTO;
    }

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
        return "E_PuntoCobertura{" + " UBICACION=" + UBICACION + ", PUNTO=" + PUNTO + ", CODIGOPUNTO=" + CODIGOPUNTO + ", RECOGEOFICINA=" + RECOGEOFICINA + '}';
    }
}
