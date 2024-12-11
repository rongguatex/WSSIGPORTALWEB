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

    private String PADRE;
    private String USUARIO;
    private String PASSWRD;
    private String UEGUIAS;

    public E_Facusuario() {
    }
    
    public E_Facusuario(E_Credenciales usuario) {
        this.PADRE = usuario.getPadre();
        this.USUARIO = usuario.getUsuario();
        this.PASSWRD = usuario.getPassword();
    }

    public E_Facusuario(String UEGUIAS, String PADRE) {
        this.UEGUIAS = UEGUIAS;
        this.PADRE = PADRE;
    }

    public E_Facusuario(String PADRE, String USUARIO, String PASSWRD) {
        this.PADRE = PADRE;
        this.USUARIO = USUARIO;
        this.PASSWRD = PASSWRD;
    }

    public E_Facusuario(String PADRE, String USUARIO, String PASSWRD, String UEGUIAS) {
        this.PADRE = PADRE;
        this.USUARIO = USUARIO;
        this.PASSWRD = PASSWRD;
        this.UEGUIAS = UEGUIAS;
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

    public String getPASSWRD() {
        return PASSWRD;
    }

    public String getUsuarioCompuesto() {
        return PADRE + "/" + USUARIO;
    }

    public void setPASSWRD(String PASSWRD) {
        this.PASSWRD = PASSWRD;
    }

    @Override
    public String toString() {
        return "E_Facusuario{" + "PADRE=" + PADRE + ", USUARIO=" + USUARIO + ", PASSWRD=" + PASSWRD + ", USUARIO COMPUESTO : " + getUsuarioCompuesto() + "+ UEGUIAS=" + UEGUIAS + '}';
    }

}
