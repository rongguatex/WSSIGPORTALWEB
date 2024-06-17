/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.controllers;

import com.guatex.sig.datos.D_GeneradorGuia;
import com.guatex.sig.entidades.E_GeneradorGuia;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RGALICIA
 */
public class GeneradorGuia {

    String[] meses = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "O", "N", "D"};
    String[] dias = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V"};

    /**
     * Genera el correlativo de guía madre
     *
     * @param parametros
     * @return
     */
    public String generarGuiaMadre(E_GeneradorGuia parametros) {
        E_GeneradorGuia datos = new D_GeneradorGuia().generadorGuia(parametros);

        if (!quitaNulo(datos.getUEGUIAS()).isEmpty()) {
            //valida si es nuevo día para iniciar con nuevo correlativo.
            if (new D_GeneradorGuia().validarNuevoDia(datos.getUEGUIAS() + estructuraGuia(), "m")) {
                System.out.println("ingresa a guia madre desde cero");
                datos.setGUIAMADRE(datos.getUEGUIAS() + estructuraGuia() + "00000");
            } else {
                System.out.println("ingresa a asignar nuevo correlativo.");
                datos.setGUIAMADRE(datos.getUEGUIAS() + estructuraGuia() + correlativoGuia(datos.getCORRELMADRESETI(), "m"));
            }
        }

        return datos.getGUIAMADRE();
    }

    /**
     * Genera el correlativo de guía hija
     *
     * @param parametros
     * @return
     */
    public String generarGuiaHija(E_GeneradorGuia parametros) {
        E_GeneradorGuia datos = new D_GeneradorGuia().generadorGuia(parametros);

        if (!quitaNulo(datos.getUEGUIAS()).isEmpty()) {
            if (new D_GeneradorGuia().validarNuevoDia("9" + datos.getUEGUIAS() + estructuraGuia(), "h")) {
                System.out.println("Ingresa a guia hija desde cero en un nuevo día.  ");
                datos.setGUIAHIJA("9" + datos.getUEGUIAS() + estructuraGuia() + "0000");
            } else {
                System.out.println("Ingresa a asignar nuevo correlativo de guia hija.  ");
                datos.setGUIAHIJA("9" + datos.getUEGUIAS() + estructuraGuia() + correlativoGuia(datos.getCORRELHIJASETI(), "h"));
            }
        }

        return datos.getGUIAHIJA();
    }

    /**
     * Método de creación de fecha para prefijo de guía ej. X44 (se crea esta
     * parte de la guía) luego viene el correlativo 000XX
     *
     * @return
     */
    public String estructuraGuia() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfm = new SimpleDateFormat("MM");
        SimpleDateFormat sdfd = new SimpleDateFormat("dd");
        String an = sdf.format(new Date());
        String mes = sdfm.format(new Date());
        String dia = sdfd.format(new Date());
        int inme = Integer.parseInt(mes);
        int indi = Integer.parseInt(dia);
        return an.substring(3, 4) + meses[(inme - 1)] + dias[(indi - 1)];
    }

    /**
     * Se crea el correlativo de guías madre e hijas, el correlativo va
     * aumentando de 1 en 1
     *
     * @param noguia número de guía obtenida de consulta en db
     * @param tipoGuia m = madre; h = hija.
     * @return el nuevo correlativo de guia.
     */
    public String correlativoGuia(String noguia, String tipoGuia) {
        String ultimaGuiaGenerada = "";
        int longitud = 0;

        if (tipoGuia.contentEquals("m")) {
            longitud = 5;
            ultimaGuiaGenerada = noguia.substring(noguia.length() - 5, noguia.length());
        } else if (tipoGuia.contentEquals("h")) {
            longitud = 4;
            ultimaGuiaGenerada = noguia.substring(noguia.length() - 4, noguia.length());
        }
        int numGuia = Integer.parseInt(ultimaGuiaGenerada) + 1;
        String nuevoCorrelativoGuia = "" + numGuia;
        while (nuevoCorrelativoGuia.length() < longitud) {
            nuevoCorrelativoGuia = "0" + nuevoCorrelativoGuia;
        }
        return nuevoCorrelativoGuia;
    }

    private String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }

}
