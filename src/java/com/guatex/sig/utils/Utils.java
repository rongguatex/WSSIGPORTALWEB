/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.utils;

import java.util.Optional;

/**
 *
 * @author RGALICIA
 */
public class Utils {

    public static String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }

    /**
     * Este método sirve para obtener solamente el valor del campo1,2, 3 y 4. El
     * fin es quitar todo lo que venga antes del caracter "/" y obtener
     * solamente el código.
     *
     * @param campo
     * @return - código seteado en el campo 1, 2, 3, y 4.
     */
    public String obtenerCodigo(String campo) {
        return campo.substring(campo.indexOf("/") + 1, campo.length());
    }

    /**
     * Convierte cualquier valor tipo string a entero, al ingresar un valor nulo
     * o un valor no válido devuelve un Opcional vacío.
     *
     * @param valor
     * @return valor convertido en tipo entero ó un Opcional vacío de no ser un
     * valor válido.
     */
    public static Optional<Integer> convertirAEntero(String valor) {
        return Optional.ofNullable(valor)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                });
    }

    public static Optional<Double> convertirADouble(String valor) {
        return Optional.ofNullable(valor)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try {
                        return Double.parseDouble(s);
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                });
    }

    /**
     * Retorna el valor N si el valor ingresado está vacio.
     *
     * @param valor
     * @return
     */
    public static String validaCampo(String valor) {
        return valor.isEmpty() ? "N" : valor.trim();
    }

    public static String codificaCaracteres(String input) {
        if (input == null) {
            return null;
        }
        return input
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("\'", "&apos;");
    }

    public static String decodificaCaracteres(String input) {
        if (input == null) {
            return null;
        }
        return input
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&apos;", "\'");
    }

}
