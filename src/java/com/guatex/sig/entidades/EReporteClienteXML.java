/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PJUNIOR-3
 */
@XmlRootElement
public class EReporteClienteXML {

    private String CODIGO = "";
    private String CODCOB = "";
    private String NOMBRE = "";
    private String CONTACTO = "";
    private String DIRECCION = "";
    private String MUNICIPIO = "";
    private String PUNTO = "";
    private String EMAIL = "";
    private String TELEFONO = "";
    private String NIT = "";
    private String CAMPO1 = "";
    private String CAMPO2 = "";
    private String CAMPO3 = "";
    private String CAMPO4 = "";
    private String RECOGEOFICINA = "";
    private String DEPTODES = "";
    private String PADRE = "";
    
     private String ESTADO = "";

    @XmlElement(name = "CODCOB")
    public String getCODCOB() {
        return CODCOB;
    }

    public void setCODCOB(String CODCOB) {
        this.CODCOB = CODCOB;
    }

    @XmlElement(name = "CODIGO")
    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    @XmlElement(name = "NOMBRE")
    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    @XmlElement(name = "CONTACTO")
    public String getCONTACTO() {
        return CONTACTO;
    }

    public void setCONTACTO(String CONTACTO) {
        this.CONTACTO = CONTACTO;
    }

    @XmlElement(name = "NIT")
    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    @XmlElement(name = "EMAIL")
    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    @XmlElement(name = "TELEFONO")
    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    @XmlElement(name = "DIRECCION")
    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    @XmlElement(name = "CAMPO1")
    public String getCAMPO1() {
        return CAMPO1;
    }

    public void setCAMPO1(String CAMPO1) {
        this.CAMPO1 = CAMPO1;
    }

    @XmlElement(name = "CAMPO2")
    public String getCAMPO2() {
        return CAMPO2;
    }

    public void setCAMPO2(String CAMPO2) {
        this.CAMPO2 = CAMPO2;
    }

    @XmlElement(name = "CAMPO3")
    public String getCAMPO3() {
        return CAMPO3;
    }

    public void setCAMPO3(String CAMPO3) {
        this.CAMPO3 = CAMPO3;
    }

    @XmlElement(name = "CAMPO4")
    public String getCAMPO4() {
        return CAMPO4;
    }

    public void setCAMPO4(String CAMPO4) {
        this.CAMPO4 = CAMPO4;
    }

    @XmlElement(name = "MUNICIPIO")
    public String getMUNICIPIO() {
        return MUNICIPIO;
    }

    public void setMUNICIPIO(String MUNICIPIO) {
        this.MUNICIPIO = MUNICIPIO;
    }

    @XmlElement(name = "PUNTO")
    public String getPUNTO() {
        return PUNTO;
    }

    public void setPUNTO(String PUNTO) {
        this.PUNTO = PUNTO;
    }

    @XmlElement(name = "RECOGEOFICINA")
    public String getRECOGEOFICINA() {
        return RECOGEOFICINA;
    }

    public void setRECOGEOFICINA(String RECOGEOFICINA) {
        this.RECOGEOFICINA = RECOGEOFICINA;
    }

    @XmlElement(name = "DEPTODES")
    public String getDEPTODES() {
        return DEPTODES;
    }

    public void setDEPTODES(String DEPTODES) {
        this.DEPTODES = DEPTODES;
    }

    @XmlElement(name = "PADRE")
    public String getPADRE() {
        return PADRE;
    }

    public void setPADRE(String PADRE) {
        this.PADRE = PADRE;
    }
 @XmlElement(name = "ESTADO")
    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

}
