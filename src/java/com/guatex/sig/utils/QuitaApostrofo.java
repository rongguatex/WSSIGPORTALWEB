/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RGALICIA
 */
public class QuitaApostrofo {
     public static String QuitaApostrofoDireccion(String dato) {

        Pattern patron = Pattern.compile("[^a-zA-Z0-9 áéíóúÁÉÍÓÚüÜ.,$#%()=;:+-_@Ññ]*");
        Matcher encaja = patron.matcher(dato);
        String resultado = encaja.replaceAll("");
        String resultado1 = resultado.replace("/", "");

        System.out.println("resultado1: " + resultado1);
        return resultado1;
    }

    public static String QuitaApostrofoNumeros(String dato) {
        Pattern patron = Pattern.compile("[^0-9.-]");
        Matcher encaja = patron.matcher(dato);
        String resultado = encaja.replaceAll("");
        return resultado;
    }

    public static String QuitaApostrofoNumerosExcel(String dato) {
        Pattern patron = Pattern.compile("[^0-9,-]");
        Matcher encaja = patron.matcher(dato);
        String resultado = encaja.replaceAll("");
        return resultado;
    }

    public static String QuitaApostrofoPrecio(String dato) {
        Pattern patron = Pattern.compile("[^.0-9]");
        Matcher encaja = patron.matcher(dato);
        String resultado = encaja.replaceAll("");
        return resultado;
    }
}
