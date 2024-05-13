
package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_PuntoCobertura;


/**
 *
 * @author JLAM
 */
public class ConvertirDiasAFrecuencia {

    public String Convertir(int i, String Cadena, E_PuntoCobertura PuntoCobertura) {
        switch (i) {
            case 1:
                if (PuntoCobertura.getLUNES() != ' ') {
                    Cadena += "Lunes";
                    return Convertir(2, Cadena, PuntoCobertura);
                }
                break;
            case 2:
                if (PuntoCobertura.getMARTES() != ' ') {
                    if (Cadena.length() != 0) {
                        Cadena += ", ";
                        Cadena += "Martes";
                        return Convertir(3, Cadena, PuntoCobertura);
                    } else {
                        Cadena += "Martes";
                        return Convertir(3, Cadena, PuntoCobertura);
                    }
                }
            case 3:
                if (PuntoCobertura.getMIERCOLES() != ' ') {

                    if (Cadena.length() != 0) {
                        Cadena += ", ";
                        Cadena += "Miércoles";
                        return Convertir(4, Cadena, PuntoCobertura);
                    } else {
                        Cadena += "Miércoles";
                        return Convertir(4, Cadena, PuntoCobertura);
                    }
                }
            case 4:
                if (PuntoCobertura.getJUEVES() != ' ') {

                    if (Cadena.length() != 0) {
                        Cadena += ", ";
                        Cadena += "Jueves";
                        return Convertir(5, Cadena, PuntoCobertura);
                    } else {
                        Cadena += "Jueves";
                        return Convertir(5, Cadena, PuntoCobertura);
                    }
                }
            case 5:
                if (PuntoCobertura.getVIERNES() != ' ') {
                    if (Cadena.length() != 0) {
                        Cadena += ", ";
                        Cadena += "Viernes";
                        return Convertir(6, Cadena, PuntoCobertura);
                    } else {
                        Cadena += "Viernes";
                        return Convertir(6, Cadena, PuntoCobertura);
                    }
                }
            case 6:
                if (PuntoCobertura.getSABADO() != ' ') {
                    if (Cadena.length() != 0) {
                        Cadena += ", ";
                        Cadena += "Sábado.";
                        return Cadena;
                    } else {
                        Cadena += "Sábado.";
                        return Cadena;
                    }
                } else {
                    return Cadena + ".";
                }

            default:
                return Cadena + ".";

        }

        if (i <= 7) {
            return Convertir((i + 1), Cadena, PuntoCobertura);
        } else {
            return Cadena + ".";
        }
        
    }
}
