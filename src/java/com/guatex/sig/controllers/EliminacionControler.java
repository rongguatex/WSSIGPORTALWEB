/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.controllers;

import com.guatex.sig.datos.Conexion;
import com.guatex.sig.datos.D_Eliminacion;
import com.guatex.sig.datos.D_FacCliente;
import com.guatex.sig.datos.D_Facusuarios;
import com.guatex.sig.datos.D_Guia;
import com.guatex.sig.entidades.EWSSIGCLIENTES;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Facusuario;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_Solicitud;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.services.WSSIGCLIENTES;
import com.guatex.sig.utils.ConvertidorXML;
import com.guatex.sig.utils.ParseadorXML;
import com.guatex.sig.utils.Utils;
import com.guatex.sig.utils.ValidacionCredenciales;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RGALICIA
 */
public class EliminacionControler {

    Utils util = new Utils();

    public String obtenerGuiasAEliminar(String xml) {
        return null;
    }

    /**
     * Realiza la eliminación de guías multiples.
     *
     * @param XML
     * @return
     */
    public String EliminacionDeGuias(String XML) {

        EWSSIGCLIENTES<E_Solicitud> parseoXML = (EWSSIGCLIENTES<E_Solicitud>) new ParseadorXML().parseoXML(XML, EWSSIGCLIENTES.class, E_Solicitud.class);

        E_Credenciales credenciales = parseoXML.getCredenciales();
        List<E_ImpresionSIG> datos = parseoXML.getDatosEntrada().getListadoGuiaImpresion();

        if (credenciales == null || datos == null || datos.isEmpty()) {
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Existe un problema con los datos obtenidos, por favor verifique que la información esté correcta.");
        }

        if (!new ValidacionCredenciales().validar(parseoXML.getCredenciales()).getCodigo().equals("0000")) {
            Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Credenciales inválidas.");
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Credenciales inválidas.");
        }

        E_Facusuario datosUsuario = new D_Facusuarios().obtenerDatosUsuario(credenciales.getUsuarioCompuesto());
        List<String> codcobs = new D_FacCliente().consultaListadoCodcobs(credenciales.getPadre());

        if (codcobs.isEmpty() || datosUsuario == null) {
            Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Codigo cliente no tiene c\u00f3digos de cobro asignados, padre: {0}", credenciales.getPadre());
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Codigo cliente no tiene códigos de cobro asignados.");
        }

        try (Connection con = new Conexion().AbrirConexion()) {
            con.setAutoCommit(false);

            //valida que la guía no haya sido recolectada.
            if (!new D_Guia().validaRecoleccion(con, datos).equals("200")) {
                return new ConvertidorXML().RespuestaGeneralSIG("201", "Alguna de las guías seleccionadas ya ha sido recolectada, por favor, vuelva a intentarlo.");
            }

            for (E_ImpresionSIG dato : datos) {
                //valida que guía no sea vacía o sea null
                if (util.quitaNulo(dato.getNOGUIA()).isEmpty()) {
                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Ocurrió un error en la eliminación de guías, no viene el número de guía.");
                    return new ConvertidorXML().RespuestaGeneralSIG("500", "Ocurrió un error al eliminar guías. Los datos están incompletos, por favor,  intente de nuevo.");
                }

                //valida que la guia exista y obtiene el codcob para compararlo con los codcobs pertenecientes al usuario.
                E_Guia guia = new D_Guia().obtieneDatosGuia(con, dato.getNOGUIA());
                dato.setCODCOB(guia.getCODCOB());

                if (util.quitaNulo(guia.getCODCOB()).isEmpty()) {
                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Alguna de las guías seleccionadas ya ha sido eliminada anteriormente o no existe " + dato.getNOGUIA());
                    return new ConvertidorXML().RespuestaGeneralSIG("201", "Alguna de las guías seleccionadas ya ha sido eliminada anteriormente o no existe.");
                }

                //se valida que la guia pertenezca al usuario comparando el Ueguias del usuario y los codcobs pertenecientes al código padre.
                String guiaUeguia = guia.getNOGUIA().substring(0, 3);
                for (String codcob : codcobs) {
                    if (!guia.getCODCOB().equalsIgnoreCase(codcob) && !guiaUeguia.equalsIgnoreCase(datosUsuario.getUEGUIAS())) {
                        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "guía " + dato.getNOGUIA() + " no pertenece al padre " + credenciales.getPadre());
                        return new ConvertidorXML().RespuestaGeneralSIG("201", "Alguna de las guías seleccionadas no pertenece a sus credenciales de acceso.");
                    }
                }
            }

            if (new D_Eliminacion().eliminacionMultipleGuias(con, datos)) {
                con.commit();

                String message = "Guía eliminada exitosamente.";
                if (datos.size() > 1) {
                    message = "Guías eliminadas exitosamente.";
                }

                new D_Eliminacion().insertaBitacoraEliminacion(con, credenciales, datos);
                
                new D_Eliminacion().cambiarEstado(datos, credenciales);    

                Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Eliminación exitosa.");
                return new ConvertidorXML().RespuestaGeneralSIG("200", message);
            } else {
                if (con != null) {
                    try {
                        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.WARNING, "Ocurrió un error en la eliminación de guías se procede a realizar rollback.");
                        con.rollback();
                    } catch (SQLException exrollback) {
                        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, "Error al realizar rollback.", exrollback);
                    }
                }
                return new ConvertidorXML().RespuestaGeneralSIG("500", "Ocurrió un error al eliminar las guías seleccionadas, por favor, intente de nuevo.");
            }
        } catch (SQLException e) {
            Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, null, e);
            return new ConvertidorXML().RespuestaGeneralSIG("500", "Ocurrió una excepción al eliminar las guías, por favor, intente de nuevo,");
        }

    }
}
