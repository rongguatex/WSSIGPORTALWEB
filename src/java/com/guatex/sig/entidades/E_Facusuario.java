/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

/**
 *
 * @author RGALICIA
 */
public class E_Facusuario {

    private String USUARIO;
    private String UEGUIAS;
    private String PADRE;

    public E_Facusuario(String UEGUIAS, String PADRE) {
        this.UEGUIAS = UEGUIAS;
        this.PADRE = PADRE;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getUEGUIAS() {
        return UEGUIAS;
    }

    public void setUEGUIAS(String UEGUIAS) {
        this.UEGUIAS = UEGUIAS;
    }

    public String getPADRE() {
        return PADRE;
    }

    public void setPADRE(String PADRE) {
        this.PADRE = PADRE;
    }

    @Override
    public String toString() {
        return "E_Facusuario{" + "USUARIO=" + USUARIO + ", UEGUIAS=" + UEGUIAS + ", PADRE=" + PADRE + '}';
    }

}
