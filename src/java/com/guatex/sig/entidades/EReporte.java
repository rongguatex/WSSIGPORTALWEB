/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PJUNIOR-3
 */
public class EReporte {

    public String CODCOB = "";
    public String PADRE = "";
    public String UNIFICA = "";
    
    private List<EReporteClientes> GUIACLIENTE= new LinkedList<>();

    public List<EReporteClientes> getGUIACLIENTE() {
        return GUIACLIENTE;
    }

    public void setGUIACLIENTE(List<EReporteClientes> GUIACLIENTE) {
        this.GUIACLIENTE = GUIACLIENTE;
    }

  
    public String getCODCOB() {
        return CODCOB;
    }

    public void setCODCOB(String CODCOB) {
        this.CODCOB = CODCOB;
    }

    public String getPADRE() {
        return PADRE;
    }

    public void setPADRE(String PADRE) {
        this.PADRE = PADRE;
    }

    public String getUNIFICA() {
        return UNIFICA;
    }

    public void setUNIFICA(String UNIFICA) {
        this.UNIFICA = UNIFICA;
    }

}
