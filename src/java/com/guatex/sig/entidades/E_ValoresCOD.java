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
public class E_ValoresCOD {

    private double ValorCODMax = 0.0;
    private double ValorCODMin = 0.0;
    private int GuiasCODMax = 0;

    public double getValorCODMax() {
        return ValorCODMax;
    }

    public void setValorCODMax(double ValorCODMax) {
        this.ValorCODMax = ValorCODMax;
    }

    public double getValorCODMin() {
        return ValorCODMin;
    }

    public void setValorCODMin(double ValorCODMin) {
        this.ValorCODMin = ValorCODMin;
    }

    public int getGuiasCODMax() {
        return GuiasCODMax;
    }

    public void setGuiasCODMax(int GuiasCODMax) {
        this.GuiasCODMax = GuiasCODMax;
    }

    @Override
    public String toString() {
        return "\n\nE_ValoresCOD{" + "ValorCODMax=" + ValorCODMax + ", ValorCODMin=" + ValorCODMin + ", GuiasCODMax=" + GuiasCODMax + '}';
    }
}
