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

    /**
     * Quita el valor nulo y caracteres especiales.
     *
     * @param var
     * @return
     */
    public String limpiaStr(String var) {
        if (var == null) {
            return "";
        }
        var = var.replaceAll("null", "").replaceAll("NULL", "");
        String strLimpio = var.replaceAll("[^a-zA-Z0-9._\\- /,*´#=:;]", "");
        return strLimpio.trim();
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
    public Optional<Integer> convertirAEntero(String valor) {
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
}
