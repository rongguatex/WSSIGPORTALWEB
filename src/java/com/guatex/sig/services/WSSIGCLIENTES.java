package com.guatex.sig.services;

import com.guatex.sig.controllers.C_GuiasMasivas;
import com.guatex.sig.controllers.ModificarGuiaController;
import com.guatex.sig.datos.Conexion;
import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.datos.D_Detalle;
import com.guatex.sig.datos.D_Eliminacion;
import com.guatex.sig.datos.D_FacCliente;
import com.guatex.sig.datos.D_Facusuarios;
import com.guatex.sig.datos.D_Guia;
import com.guatex.sig.datos.D_ImpresionSIG;
import com.guatex.sig.datos.D_UsuarioOpcion;
import com.guatex.sig.entidades.EWSSIGCLIENTES;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_Departamento;
import com.guatex.sig.entidades.E_Facusuario;
import com.guatex.sig.entidades.E_Guia;
import com.guatex.sig.entidades.E_ImpresionSIG;
import com.guatex.sig.entidades.E_JUsuarioOpcion;
import com.guatex.sig.entidades.E_Municipio;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.entidades.E_Servicio;
import com.guatex.sig.entidades.E_Solicitud;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.entidadesRespuesta.E_RespuestaDetalle;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuia;
import com.guatex.sig.utils.ConvertidorXML;
import com.guatex.sig.utils.ParseadorXML;
import com.guatex.sig.utils.ValidacionCredenciales;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "WSSIGCLIENTES")
public class WSSIGCLIENTES {

    @WebMethod(operationName = "busquedaCliente")
    public String busquedaCliente(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerCliente(cliente);
        return new ConvertidorXML().respuestaXMLDatosCliente(respuesta);
    }

    @WebMethod(operationName = "busquedaClientes")
    public String busquedaClientes(@WebParam(name = "datos") String datos) {
        E_Cliente cliente = new ConvertidorXML().extraerCliente(datos);
        E_respuestaClientes respuesta = new D_Clientes().ObtenerListadoClientes(cliente);
        return new ConvertidorXML().respuestaXMLDatosCliente(respuesta);
    }

    /**
     * busqueda de listado de guias por rango de fechas, tipo de impresión que
     * no hayan sido recolectadas.
     *
     * @param datos
     * @return
     */
    @WebMethod(operationName = "busquedaGuiasxTipoImpresion")
    public String busquedaGuiasxTipoImpresion(@WebParam(name = "datos") String datos) {
        E_Guia datosSolicitud = new ConvertidorXML().solicitudGuiasSinImprimir(datos);
        if (!datosSolicitud.getFECHA_INICIAL().isEmpty()
                && !datosSolicitud.getFECHA_FINAL().isEmpty()
                && !datosSolicitud.getCODCOB().isEmpty()
                && !datosSolicitud.getIMPRESO().isEmpty()) {
            E_RespuestaGuia respuesta = new D_Guia().BuscarRangoFechaJGuiasNoImpresas(datosSolicitud);
            return new ConvertidorXML().respuestaXMLDatosGuia(respuesta, new E_Departamento(), new E_Municipio(), new E_PuntoCobertura());
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    @WebMethod(operationName = "obtenerDetalleGuia")
    public String obtenerDetalleGuia(@WebParam(name = "datos") String noguia) {
        noguia = noguia == null ? "" : noguia.trim();
        if (!noguia.isEmpty()) {
            E_RespuestaDetalle respuesta = new D_Detalle().buscarDetalleGuia(noguia);
            return new ConvertidorXML().respuestaXMLDetalleGuia(respuesta);
        } else {
            return new ConvertidorXML().BadRequest();
        }
    }

    @WebMethod(operationName = "insertarImpresionData")
    public String insertarImpresionData(@WebParam(name = "datos") String XML) {
        List<E_ImpresionSIG> impresiones = new ConvertidorXML().getObjectImpresion(XML);
        return new D_ImpresionSIG().insertaImpresionSIG(impresiones);
    }

    @WebMethod(operationName = "insertaReimpresion")
    public String insertaReimpresion(@WebParam(name = "datos") String XML) {
        List<E_ImpresionSIG> impresiones = new ConvertidorXML().getObjectImpresion(XML);
        return new D_ImpresionSIG().insertaReimpresion(impresiones);
    }

    /**
     * Busca los datos de una guía que no haya sido impresa y tampoco entregada.
     *
     * @param XML
     * @param noguia - número de guía a buscar.
     * @return - xml con datos de la guía encontrada, en caso de error xml con
     * código de respuesta 400
     */
    @WebMethod(operationName = "obtenerDatosxGuiaNoImpresa")
    public String obtenerDatosxGuiaNoImpresa(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new ModificarGuiaController().obtenerDatosGuia(XML);
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "creacionGuiasMasivas")
    public String creacionGuiasMasivas(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new C_GuiasMasivas().creacionGuiasMasivas(XML.trim());
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "modificaGuia")
    public String modificaGuia(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            return new ModificarGuiaController().modificaGuia(XML.trim());
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "obtenerOpciones")
    public String obtenerOpciones(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            EWSSIGCLIENTES<?> parseoXML = (EWSSIGCLIENTES<?>) new ParseadorXML().parseoXML(XML, EWSSIGCLIENTES.class);

            if (parseoXML.getCredenciales() == null) {
                return new ConvertidorXML().BadRequest();
            }

            if (new ValidacionCredenciales().validar(parseoXML.getCredenciales()).getCodigo().equals("0000")) {
                List<E_JUsuarioOpcion> opciones = new D_UsuarioOpcion().obtenerOpciones(parseoXML.getCredenciales().getUsuario());
                if (opciones == null) {
                    return new ConvertidorXML().InternalServerError();
                }

                if (!opciones.isEmpty()) {
                    RespuestaGeneral respuesta = new RespuestaGeneral("200", "OK");

                    String respuestaXML = new ParseadorXML().parseoObj(respuesta, RespuestaGeneral.class);

                    StringBuilder opcionesXML = new StringBuilder("<OPCIONES>");
                    for (E_JUsuarioOpcion opcion : opciones) {
                        opcionesXML.append(new ParseadorXML().parseoObj(opcion, E_JUsuarioOpcion.class));
                    }
                    opcionesXML.append("</OPCIONES>");
//                    System.out.println("<WSSIGCLIENTES>" + respuestaXML + opcionesXML.toString() + "</WSSIGCLIENTES>");
                    return "<WSSIGCLIENTES>" + respuestaXML + opcionesXML.toString() + "</WSSIGCLIENTES>";
                } else {
                    return new ConvertidorXML().NoContent();
                }
            }
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "descargaRotulador")
    public String descargaRotulador(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            EWSSIGCLIENTES<E_Solicitud> parseoXML = (EWSSIGCLIENTES<E_Solicitud>) new ParseadorXML().parseoXML(XML, EWSSIGCLIENTES.class, E_Solicitud.class);

            if (parseoXML.getCredenciales() == null || parseoXML.getDatosEntrada().getListadoGuiaImpresion() == null) {
                return new ConvertidorXML().BadRequest();
            }

            if (new ValidacionCredenciales().validar(parseoXML.getCredenciales()).getCodigo().equals("0000")) {
                List<E_ImpresionSIG> datos = parseoXML.getDatosEntrada().getListadoGuiaImpresion();
                if (!datos.isEmpty()) {
                    System.out.println("tipo [" + parseoXML.getCredenciales().getTipo() + "]");
                    String respuesta = "";

                    if (parseoXML.getCredenciales().getTipo().equalsIgnoreCase("S")) {
                        respuesta = new D_Guia().validaImpresion(datos);

                        if (respuesta.equals("998")) { //verifica si la guía ha sido impresa anteriormente
                            return "<WSSIGCLIENTES>" + new ConvertidorXML().isPrinted() + "</WSSIGCLIENTES>";
                        }
                    }

                    if (parseoXML.getCredenciales().getTipo().equalsIgnoreCase("R")) { //reimpresión de guías
                        respuesta = new D_Guia().validaRecoleccion(datos);

                        if (respuesta.equals("999")) { //verifica si la guía ha sido entregada anteriormente
                            return "<WSSIGCLIENTES>" + new ConvertidorXML().IsDerivered() + "</WSSIGCLIENTES>";
                        }
                    }

                    List<E_Servicio> serviciosId = new D_Guia().obtenerIdServicio(datos);

                    if (serviciosId == null || serviciosId.size() != datos.size()) {
                        return new ConvertidorXML().InternalServerError();
                    }

                    String respuestaXML = new ParseadorXML().parseoObj(new RespuestaGeneral("200", "OK"), RespuestaGeneral.class);
                    StringBuilder servicioXML = new StringBuilder("<SERVICIOS>");

                    for (E_Servicio servicio : serviciosId) {
                        servicio.setURLSERVICIO("https://sig.guatex.gt/WSPGimpresion/GeneraPDF?idservicio=" + servicio.getIDSERVICIO());
                        servicioXML.append(new ParseadorXML().parseoObj(servicio, E_Servicio.class));
                    }

                    servicioXML.append("</SERVICIOS>");

                    System.out.println("<WSSIGCLIENTES>" + respuestaXML + servicioXML.toString() + "</WSSIGCLIENTES>");
                    return "<WSSIGCLIENTES>" + respuestaXML + servicioXML.toString() + "</WSSIGCLIENTES>";
                }
            }
            return new ConvertidorXML().Unauthorized();
        }
        return new ConvertidorXML().BadRequest();
    }

    @WebMethod(operationName = "obtenerGuiasAEliminar")
    public String obtenerGuiasAEliminar(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            XML = XML.replace("&", "&amp;");
            XML = XML.replace("\"", "&quot;");
            XML = XML.replace("\'", "&apos;");
            EWSSIGCLIENTES<?> parseoXML = (EWSSIGCLIENTES<?>) new ParseadorXML().parseoXML(XML, EWSSIGCLIENTES.class);

            if (parseoXML.getCredenciales() != null) {
                E_Credenciales credenciales = parseoXML.getCredenciales();
                E_Facusuario datosUsuario = new D_Facusuarios().obtenerDatosUsuario(credenciales.getUsuario());

                if (datosUsuario == null || datosUsuario.getUEGUIAS().isEmpty() || datosUsuario.getPADRE().isEmpty()) {
                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, "Error al obtener datos del usuario.");
                    return "<WSSIGCLIENTES>"
                            + new ParseadorXML().parseoObj(
                                    new RespuestaGeneral("9999", "Ocurrió un error al obtener datos del usuario,"),
                                    RespuestaGeneral.class)
                            + "</WSSIGCLIENTES>";
                }
                
                E_RespuestaGuia resultado = new D_Guia().obtenerGuiasEliminar(datosUsuario);
                if (!resultado.getCODIGO().equals("200")) {
                    return "<WSSIGCLIENTES>"
                            + new ParseadorXML().parseoObj(
                                    new RespuestaGeneral("9998", resultado.getMENSAJE()),
                                    RespuestaGeneral.class)
                            + "</WSSIGCLIENTES>";
                }

                return "<WSSIGCLIENTES>" + new ParseadorXML().parseoObj(resultado, E_RespuestaGuia.class) + "</WSSIGCLIENTES>";
            }
        }

        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, "Error en el envío de datos al ws.");
        return "<WSSIGCLIENTES>"
                + new ParseadorXML().parseoObj(
                        new RespuestaGeneral("9999", "Error en el envío de datos, por favor, intente de nuevo."),
                        RespuestaGeneral.class)
                + "</WSSIGCLIENTES>";
    }

    @WebMethod(operationName = "eliminacionMultiple")
    public String eliminacionMultiple(@WebParam(name = "datos") String XML) {
        if (!(XML == null ? "" : XML.trim()).isEmpty()) {
            XML = XML.replace("&", "&amp;");
            XML = XML.replace("\"", "&quot;");
            XML = XML.replace("\'", "&apos;");

            EWSSIGCLIENTES<E_Solicitud> parseoXML = (EWSSIGCLIENTES<E_Solicitud>) new ParseadorXML().parseoXML(XML, EWSSIGCLIENTES.class, E_Solicitud.class);

            if (parseoXML.getCredenciales() == null || parseoXML.getDatosEntrada().getListadoGuiaImpresion() == null) {
                return new ConvertidorXML().BadRequest();
            }

            if (new ValidacionCredenciales().validar(parseoXML.getCredenciales()).getCodigo().equals("0000")) {
                E_Credenciales credenciales = parseoXML.getCredenciales();
                List<E_ImpresionSIG> datos = parseoXML.getDatosEntrada().getListadoGuiaImpresion();
                if (!datos.isEmpty()) {
                    try (Connection con = new Conexion().AbrirConexion()) {
                        con.setAutoCommit(false);
                        for (E_ImpresionSIG dato : datos) {
                            if (dato.getNOGUIA() != null && dato.getNOGUIA().length() > 0) {
                                System.out.println("dato [" + dato.getNOGUIA() + "]");
                                if (new D_Guia().validaExistencia(con, dato.getNOGUIA()) == null) {
                                    return "<WSSIGCLIENTES>"
                                            + new ParseadorXML().parseoObj(
                                                    new RespuestaGeneral("9999", "Alguna de las guías seleccionadas ya ha sido eliminada o no es válida."),
                                                    RespuestaGeneral.class)
                                            + "</WSSIGCLIENTES>";
                                }

                                if (!new D_Guia().validaRecoleccion(datos).equals("200")) {
                                    return new ParseadorXML().parseoObj(
                                            new RespuestaGeneral("9999", "Alguna de las guías seleccionadas ya ha sido recolectada, por favor, vuelva a intentarlo."),
                                            RespuestaGeneral.class);
                                }

                                String codcob = new D_FacCliente().consultaCodcob(con, credenciales.getPadre(), dato.getNOGUIA());
                                if (codcob == null || codcob.isEmpty()) {
                                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "guía " + dato.getNOGUIA() + " no pertenece al padre " + credenciales.getPadre());
                                    return "<WSSIGCLIENTES>"
                                            + new ParseadorXML().parseoObj(
                                                    new RespuestaGeneral("9999", "Alguna de las guías seleccionadas no pertenece a sus credenciales de acceso."),
                                                    RespuestaGeneral.class)
                                            + "</WSSIGCLIENTES>";
                                } else {
                                    dato.setCODCOB(codcob);
                                    if (!new D_Facusuarios().validaUsuario(con, credenciales, dato)) {
                                        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "guía " + dato.getNOGUIA() + " no pertenece al usuario " + credenciales.getUsuario());
                                        return "<WSSIGCLIENTES>"
                                                + new ParseadorXML().parseoObj(
                                                        new RespuestaGeneral("9999", "Alguna de las guías seleccionadas no pertenece a sus credenciales de acceso."),
                                                        RespuestaGeneral.class)
                                                + "</WSSIGCLIENTES>";
                                    }
                                }

                            } else {
                                Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Ocurrió un error en la eliminación de guías, no viene el número de guía.");
                                return "<WSSIGCLIENTES>"
                                        + new ParseadorXML().parseoObj(
                                                new RespuestaGeneral("9999", "Ocurrió un error al eliminar guías. Los datos están incompletos, por favor,  intente de nuevo.,"),
                                                RespuestaGeneral.class)
                                        + "</WSSIGCLIENTES>";
                            }
                        }

                        if (new D_Eliminacion().eliminacionMultipleGuias(con, datos)) {
                            con.commit();

                            String message = "Guía eliminada exitosamente..";
                            if (datos.size() > 1) {
                                message = "Guías eliminadas exitosamente..";
                            }

                            new D_Eliminacion().insertaBitacoraEliminacion(con, credenciales, datos);

                            Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Eliminación exitosa.");
                            return "<WSSIGCLIENTES>"
                                    + new ParseadorXML().parseoObj(
                                            new RespuestaGeneral("200", message),
                                            RespuestaGeneral.class)
                                    + "</WSSIGCLIENTES>";
                        } else {
                            if (con != null) {
                                try {
                                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.WARNING, "Ocurrió un error en la eliminación de guías se procede a realizar rollback.");
                                    con.rollback();
                                } catch (SQLException exrollback) {
                                    Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, "Error al realizar rollback.", exrollback);
                                }
                            }
                            return "<WSSIGCLIENTES>"
                                    + new ParseadorXML().parseoObj(
                                            new RespuestaGeneral("9999", "Ocurrió un error al eliminar las guías seleccionadas, por favor, intente de nuevo,"),
                                            RespuestaGeneral.class)
                                    + "</WSSIGCLIENTES>";
                        }
                    } catch (SQLException e) {
                        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, null, e);
                        return "<WSSIGCLIENTES>"
                                + new ParseadorXML().parseoObj(
                                        new RespuestaGeneral("9999", "Ocurrió una excepción al eliminar las guías, por favor, intente de nuevo,"),
                                        RespuestaGeneral.class)
                                + "</WSSIGCLIENTES>";
                    }
                }
                return "<WSSIGCLIENTES>"
                        + new ParseadorXML().parseoObj(
                                new RespuestaGeneral("9999", "Ocurrió un error al eliminar las guías seleccionadas. Los datos son inompletos, por favor, intente de nuevo,"),
                                RespuestaGeneral.class)
                        + "</WSSIGCLIENTES>";

            }
            Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.INFO, "Credenciales inválidas.");
            return "<WSSIGCLIENTES>"
                    + new ParseadorXML().parseoObj(
                            new RespuestaGeneral("9999", "Credenciales inválidas."),
                            RespuestaGeneral.class)
                    + "</WSSIGCLIENTES>";
        }
        Logger.getLogger(WSSIGCLIENTES.class.getName()).log(Level.SEVERE, "Error en el envío de datos al ws.");
        return "<WSSIGCLIENTES>"
                + new ParseadorXML().parseoObj(
                        new RespuestaGeneral("9999", "Error en el envío de datos, por favor, intente de nuevo,"),
                        RespuestaGeneral.class)
                + "</WSSIGCLIENTES>";
    }
}
