/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import com.guatex.sig.utils.Utils;

/**
 *
 * @author PJUNIOR-3
 */
public class EReporteClientes {

    Utils util = new Utils();

    private String CODIGO = "";
    private String CODCOB = "";
    private String NOMBRE = "";
    private String CONTACTO = "";
    private String DIRECCION = "";
    private String COMPLEMENTODIRECCION = "";
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

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getCODCOB() {
        return CODCOB;
    }

    public void setCODCOB(String CODCOB) {
        this.CODCOB = CODCOB;
    }

    public String getNOMBRE() {
        return util.validarLogitud(NOMBRE, 100);
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = util.validarLogitud(NOMBRE, 100);
    }

    public String getCONTACTO() {
        return util.validarLogitud(CONTACTO, 200);
    }

    public void setCONTACTO(String CONTACTO) {
        this.CONTACTO = util.validarLogitud(CONTACTO, 200);
    }

    public String getDIRECCION() {
        return util.validarLogitud(DIRECCION, 200);
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = util.validarLogitud(DIRECCION, 200);
//        if (DIRECCION != null) {
//            if (DIRECCION.length() > 100) {
//                this.DIRECCION = DIRECCION.trim().substring(0, 100);
//                this.COMPLEMENTODIRECCION = DIRECCION.trim().substring(100, DIRECCION.length());
//            } else {
//                this.DIRECCION = DIRECCION.trim();
//            }
//        }
    }

    public String getCOMPLEMENTODIRECCION() {
        return util.validarLogitud(COMPLEMENTODIRECCION, 100);
    }

    public void setCOMPLEMENTODIRECCION(String COMPLEMENTODIRECCION) {
        this.COMPLEMENTODIRECCION = util.validarLogitud(COMPLEMENTODIRECCION, 100);
    }

    public String getMUNICIPIO() {
        return MUNICIPIO;
    }

    public void setMUNICIPIO(String MUNICIPIO) {
        this.MUNICIPIO = MUNICIPIO;
    }

    public String getPUNTO() {
        return PUNTO;
    }

    public void setPUNTO(String PUNTO) {
        this.PUNTO = PUNTO;
    }

    public String getEMAIL() {
        return util.validarLogitud(EMAIL, 200);
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = util.validarLogitud(EMAIL, 200);
    }

    public String getTELEFONO() {
        return util.validarLogitud(TELEFONO, 20);
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = util.validarLogitud(TELEFONO, 20);
    }

    public String getNIT() {
        return util.validarLogitud(NIT, 25);
    }

    public void setNIT(String NIT) {
        this.NIT = util.validarLogitud(NIT, 25);
    }

    public String getCAMPO1() {
        return util.validarLogitud(CAMPO1, 200);
    }

    public void setCAMPO1(String CAMPO1) {
        this.CAMPO1 = util.validarLogitud(CAMPO1, 200);
    }

    public String getCAMPO2() {
        return util.validarLogitud(CAMPO2, 200);
    }

    public void setCAMPO2(String CAMPO2) {
        this.CAMPO2 = util.validarLogitud(CAMPO2, 200);
    }

    public String getCAMPO3() {
        return util.validarLogitud(CAMPO3, 200);
    }

    public void setCAMPO3(String CAMPO3) {
        this.CAMPO3 = util.validarLogitud(CAMPO3, 200);
    }

    public String getCAMPO4() {
        return util.validarLogitud(CAMPO4, 200);
    }

    public void setCAMPO4(String CAMPO4) {
        this.CAMPO4 = util.validarLogitud(CAMPO4, 200);
    }

    public String getRECOGEOFICINA() {
        return RECOGEOFICINA;
    }

    public void setRECOGEOFICINA(String RECOGEOFICINA) {
        this.RECOGEOFICINA = RECOGEOFICINA;
    }

    public String getPADRE() {
        return PADRE;
    }

    public void setPADRE(String PADRE) {
        this.PADRE = PADRE;
    }

    public String getDEPTODES() {
        return DEPTODES;
    }

    public void setDEPTODES(String DEPTODES) {
        this.DEPTODES = DEPTODES;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

}
