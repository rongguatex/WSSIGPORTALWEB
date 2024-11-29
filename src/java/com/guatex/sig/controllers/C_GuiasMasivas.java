/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.controllers;

import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.datos.D_FacCliente;
import com.guatex.sig.datos.D_PuntoCobertura;
import com.guatex.sig.datos.D_TarifaEnvio;
import com.guatex.sig.entidades.ETomaServicio;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.E_DatosGuiaMasiva;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_GuiasMasivas;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.entidades.RespuestaTomaServicio;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuiasMasivas;
import com.guatex.sig.utils.Pair;
import com.guatex.sig.utils.ParseadorXML;
import com.guatex.sig.utils.QuitaApostrofo;
import com.guatex.sig.utils.Utils;
import com.guatex.sig.utils.ValidacionCredenciales;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author RGALICIA
 */
public class C_GuiasMasivas {

    /**
     * Valida la información ingresada desde la cadena XML y si no hubiera
     * errores crea una las guías solicitadas por cada fila u objeto que se
     * envía en la cadena.
     *
     * @param xml
     * @return - Devuelve una cadena en formato XML con el resultado de las
     * validaciones realizadas.
     */
    public String creacionGuiasMasivas(String xml) {
        E_GuiasMasivas datos = parseoXML(xml);
        if (datos.getCredenciales() != null) {
            boolean existenErrores = false;

            RespuestaGeneral respuestaValidaCredenciales = new ValidacionCredenciales().validar(datos.getCredenciales());
            if ("0000".equals(respuestaValidaCredenciales.getCodigo())) {

                E_Credenciales credenciales = datos.getCredenciales();

                //obtiene los datos del REMITENTE
                E_Cliente remitente = new E_Cliente(credenciales);
                E_FacCliente parametrosRemitente = new D_FacCliente().obtenerFacCliente(remitente.getPADRE(), remitente.getCODCOB());

                //asigna datos adicionales del remitente
                remitente = asignarDatosRemitente(remitente);

                List<Pair<String, String>> codigosTipoPieza = new D_TarifaEnvio().obtenerTiposEnvio(parametrosRemitente);

                /**
                 * valida los datos del cliente por el código [código, nombre,
                 * teléfono, dirección, coddes, mncpdes] si código es válido
                 * reemplaza la información con la que existe en la bd.
                 */
                datos.setListaDatosGuia(validarClientexCodigo(datos.getListaDatosGuia(), credenciales.getCodcob(), credenciales.getPadre()));

                for (E_DatosGuiaMasiva dato : datos.getListaDatosGuia()) {
                    /**
                     * Validación de TIPO PIEZA y PESO. parsea el valor tipo
                     * pieza y peso que viene en formato 1-1-1, 1-1-1... n
                     */
                    List<E_DetalleLinea> lineasdetalle = parsearTPP(QuitaApostrofo.QuitaApostrofoNumerosExcel(quitaNulo(dato.getTIPO_PIEZA_PESO())));
                    dato.setDETALLE(lineasdetalle);

                    /**
                     * Validación de campos descripción
                     */
                    if (dato.getDESCRIPCION().isEmpty()) {
                        dato.AddStateLastPosition("Campo DESCRIPCION está vacío.");
                    }

                    /**
                     * Validacion de lineas detalle.
                     */
                    if (lineasdetalle != null) {
                        if (!lineasdetalle.isEmpty()) {
                            RespuestaGeneral respuesta = validarLineasDetalle(lineasdetalle, codigosTipoPieza);
                            if (respuesta.getCodigo().equals("9999")) {
                                dato.AddStatesErrorList(respuesta.getErrores());
                            } else {
                                lineasdetalle.forEach((detalle) -> {
                                    for (Pair<String, String> pair : codigosTipoPieza) {
                                        if (detalle.getTIPOENVIO().equals(pair.getKey())) {
                                            Double pesofijo = Utils.convertirADouble(pair.getValue()).orElse((double) 0);
                                            if (pesofijo > 0) {
                                                detalle.setPESO(pesofijo + "");
                                            } else {
                                                Double peso = Utils.convertirADouble(detalle.getPESO()).orElse((double) 0);
                                                if (peso > parametrosRemitente.getMAXPESO()) {
                                                    dato.AddStateLastPosition("Campo TIPO-PIEZA-PESO: El peso máximo permitido es " + parametrosRemitente.getMAXPESO());
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            dato.AddStateLastPosition("Campo TIPO-PIEZA-PESO vacío o inválido.");
                        }
                    } else {
                        dato.AddStateLastPosition("Campo TIPO-PIEZA-PESO inválido.");
                    }

                    /**
                     * Verifica si el cliente tiene COD Validación de campo COD
                     * y PRECIO.
                     */
                    String campoCOD = Utils.validaCampo(dato.getCOD().trim());
                    if (quitaNulo(dato.getCOD()).equalsIgnoreCase("N") || quitaNulo(dato.getCOD()).equalsIgnoreCase("S")) {
                        RespuestaGeneral respuesta = validaCOD(parametrosRemitente, campoCOD, dato.getPRECIO().trim());
                        if (respuesta.getCodigo().equals("9999")) {
                            dato.AddStatesErrorList(respuesta.getErrores());
                        }
                    } else {
                        dato.AddStateLastPosition("Campo COD inválido: colocar S o N, según el tipo de código de cobro seleccionado.");
                    }

                    if (dato.getESTADO().size() > 0) {
                        dato.setESTADO(Arrays.asList(concatenarEstados(dato.getESTADO())));
                        existenErrores = true;
                    }
                }

                /**
                 * Toma el servicio.
                 *
                 * Final de las validaciones para devolver la respuesta y llamar
                 * al web service de toma de servicio. code 200 = Guías creadas
                 * correctamente code 404 = Ocurrió un error al genera guías,
                 * por favor. code 400 = Ocurrió algún error en la generación de
                 * guías, code 202 = Existen errores en el archivo excel.
                 */
                if (!existenErrores) {
                    RespuestaGeneral resTomaServicio = tomadeServicio(credenciales, parametrosRemitente, remitente, datos.getListaDatosGuia());
                    String respuestaFinal = "<WSSIGCLIENTES>" + new ParseadorXML().parseoObj(resTomaServicio, RespuestaGeneral.class) + "</WSSIGCLIENTES>";
                    return respuestaFinal;
                } else {
                    String xmlrespuesta = parseoRespuestaXML(new RespuestaGeneral("202", "Existen errores en el archivo excel."), datos.getListaDatosGuia());
                    return xmlrespuesta;
                }
            }
        }

        return "<WSSIGCLIENTES>" + new ParseadorXML().parseoObj(new RespuestaGeneral("500", "Existe un problema con los datos obtenidos, por favor verifique que la información esté correcta."), RespuestaGeneral.class) + "</WSSIGCLIENTES>";
    }

    private E_Cliente asignarDatosRemitente(E_Cliente remitente) {
        E_respuestaClientes obtieneDatosRemitente = new D_Clientes().ObtenerCliente(remitente);

        if (obtieneDatosRemitente.getCODIGO().equals("200")) {
            E_Cliente c = obtieneDatosRemitente.getDATOS_CLIENTES().get(0);
            remitente.setNOMBRE(c.getNOMBRE());
            remitente.setTELEFONO(c.getTELEFONO());
            remitente.setDIRECCION(c.getDIRECCION());
            remitente.setCONTACTO(c.getCONTACTO());
            remitente.setNIT(c.getNIT());
            remitente.setCORREO(c.getCORREO());
            remitente.setCAMPO1(c.getCAMPO1());
            remitente.setCAMPO2(c.getCAMPO2());
            remitente.setCAMPO3(c.getCAMPO3());
            remitente.setCAMPO4(c.getCAMPO4());
            remitente.setUBICACION(c.getUBICACION());
            remitente.setPUNTO(c.getPUNTO());
            remitente.setDEPARTAMENTO(c.getDEPARTAMENTO());
            remitente.setMUNICIPIO(c.getMUNICIPIO());
            remitente.setCOBERTURA(c.getCOBERTURA());

            return remitente;
        }
        return null;
    }

    public RespuestaGeneral tomadeServicio(E_Credenciales credenciales, E_FacCliente paramsrem, E_Cliente remitente, List<E_DatosGuiaMasiva> datos) {
        List<RespuestaTomaServicio> listadoErrores = new ArrayList<>();
        int nofila = 1;

        for (E_DatosGuiaMasiva dato : datos) {
            String XML
                    = "<TOMA_SERVICIO>"
                    + "	<USUARIO>" + credenciales.getUsuario() + "</USUARIO>"
                    + "	<PASSWORD>" + credenciales.getPassword() + "</PASSWORD>"
                    + "	<CODIGO_COBRO>" + credenciales.getPadre() + "</CODIGO_COBRO>";
            String valorCOD = "<COD_VALORACOBRAR />";
            String seabrepaquete = "<SEABREPAQUETE />";
            String dirremitente = "";
            String dirdestinatario = "";

            if (paramsrem.getLCOD().equalsIgnoreCase("S")) {
                valorCOD = "<COD_VALORACOBRAR>" + dato.getPRECIO() + "</COD_VALORACOBRAR>";
                seabrepaquete = "<SEABREPAQUETE>" + paramsrem.getSEABREPAQUETE() + "</SEABREPAQUETE >";
            }

            if (remitente.getDIRECCION().trim().length() > 100) {
                dirremitente = "<DIRECCION_REMITENTE>" + remitente.getDIRECCION().substring(0, 99) + "</DIRECCION_REMITENTE>"
                        + "<COMPLEMENTODIRREM>" + remitente.getDIRECCION().substring(100, remitente.getDIRECCION().length() - 1) + "</COMPLEMENTODIRREM>";
            } else {
                dirremitente = "<DIRECCION_REMITENTE>" + remitente.getDIRECCION() + "</DIRECCION_REMITENTE>";
            }

            if (dato.getDIRECCION().trim().length() > 100) {
                dirdestinatario = "<DIRECCION_DESTINATARIO>" + dato.getDIRECCION().substring(0, 99) + "</DIRECCION_DESTINATARIO>"
                        + "<COMPLEMENTODIRDES>" + dato.getDIRECCION().substring(100, dato.getDIRECCION().length() - 1) + "</COMPLEMENTODIRDES>";
            } else {
                dirdestinatario = "<DIRECCION_DESTINATARIO>" + dato.getDIRECCION() + "</DIRECCION_DESTINATARIO>";
            }

            XML
                    += "       	<SERVICIO>"
                    + "		<TIPO_USUARIO>C</TIPO_USUARIO>" //El tipo de usuario está configurado como C en el ws toma servicios por lo que 
                    + "		<ESTA_LISTO>S</ESTA_LISTO>"
                    + "		<NOMBRE_REMITENTE>" + remitente.getNOMBRE() + "</NOMBRE_REMITENTE>"
                    + "		<TELEFONO_REMITENTE>" + remitente.getTELEFONO() + "</TELEFONO_REMITENTE>"
                    + "                             " + dirremitente
                    + "		<MUNICIPIO_ORIGEN>" + remitente.getCOBERTURA().getMUNICIPIO() + "</MUNICIPIO_ORIGEN>"
                    + "		<PUNTO_ORIGEN>" + remitente.getCOBERTURA().getPUNTO() + "</PUNTO_ORIGEN>"
                    + "		<CODORIGEN>" + remitente.getCOBERTURA().getCODIGOPUNTO() + "</CODORIGEN>"
                    + "		<CONTACTO>" + remitente.getCONTACTO() + "</CONTACTO>"
                    + "		<GUIA>"
                    + "			<LLAVE_CLIENTE>" + dato.getLLAVE() + "</LLAVE_CLIENTE>"
                    + "			" + valorCOD
                    + "			" + seabrepaquete
                    + "			<CODIGO_COBRO_GUIA>" + credenciales.getCodcob() + "</CODIGO_COBRO_GUIA>"
                    + "			<NOMBRE_DESTINATARIO>" + dato.getNOMBRE() + "</NOMBRE_DESTINATARIO>"
                    + "			<TELEFONO_DESTINATARIO>" + dato.getTELEFONO() + "</TELEFONO_DESTINATARIO>"
                    + "                                                " + dirdestinatario
                    + "			<MUNICIPIO_DESTINO>" + dato.getMUNICIPIO_DESTINATARIO() + "</MUNICIPIO_DESTINO>"
                    + "			<PUNTO_DESTINO>" + dato.getCODIGO_DESTINATARIO() + "</PUNTO_DESTINO>"
                    + "			<DESCRIPCION_ENVIO>" + dato.getDESCRIPCION() + "</DESCRIPCION_ENVIO>"
                    + "			<OBSERVACIONES />"
                    + "			<RECOGE_OFICINA>N</RECOGE_OFICINA>"
                    + "			<CODDESTINO>" + dato.getCODIGODESTINO() + "</CODDESTINO>";

            int linea = 1;
            for (E_DetalleLinea lineaDetalle : dato.getDETALLE()) {
                XML += "                                              <DETALLE_GUIA>"
                        + "				<LINEA_DETALLE_GUIA>"
                        + "					<PIEZAS_DETALLE>" + lineaDetalle.getPIEZAS() + "</PIEZAS_DETALLE>"
                        + "					<TIPO_ENVIO_DETALLE>" + lineaDetalle.getTIPOENVIO() + "</TIPO_ENVIO_DETALLE>"
                        + "					<PESO_DETALLE>" + lineaDetalle.getPESO() + "</PESO_DETALLE>"
                        + "				</LINEA_DETALLE_GUIA>"
                        + "			</DETALLE_GUIA>";
                linea++;
            }

            XML += "			<OBSERVACIONES_ENTREGA></OBSERVACIONES_ENTREGA>"
                    + "			<IMPRIME_GUIA>G</IMPRIME_GUIA>"
                    + "			<CAMPO1>" + quitaNulo(dato.getCAMPO1()) + "</CAMPO1>"
                    + "			<CAMPO2>" + quitaNulo(dato.getCAMPO2()) + "</CAMPO2>"
                    + "			<CAMPO3>" + quitaNulo(dato.getCAMPO3()) + "</CAMPO3>"
                    + "			<CAMPO4>" + quitaNulo(dato.getCAMPO4()) + "</CAMPO4>"
                    + "		</GUIA>"
                    + "	</SERVICIO>";

            XML += "</TOMA_SERVICIO>";

            String tomaservicio = tomaServicio(XML);
            ETomaServicio restomaservicio = (ETomaServicio) new ParseadorXML().parseoXML(tomaservicio, ETomaServicio.class);
            nofila++;

            if (restomaservicio.getERROR() != null) {
                ETomaServicio.Error error = restomaservicio.getERROR();
                listadoErrores.add(new RespuestaTomaServicio(nofila, new RespuestaGeneral(error.getCODIGO(), error.getDESCRIPCION())));
            }

            if (restomaservicio.getSERVICIO() != null) {
                ETomaServicio.EServicio servicio = restomaservicio.getSERVICIO();
                if (quitaNulo(servicio.getCODIGO()).isEmpty()) {
                    List<ETomaServicio.ErrorGuia> error = servicio.getCORRELATIVOERROR();
                    for (ETomaServicio.ErrorGuia e : error) {
                        listadoErrores.add(new RespuestaTomaServicio(new RespuestaGeneral(e.getCODIGO(), e.getDESCRIPCION())));
                    }
                }
            }
        }

        RespuestaGeneral respuesta = new RespuestaGeneral();
        respuesta.setDetalles(listadoErrores);

        if (!listadoErrores.isEmpty()) {
            respuesta.setCodigo("201");
            respuesta.setMensaje("Algunas guías no han sido creadas, por favor revise el listado de errores.");
        } else {
            respuesta.setCodigo("200");
            respuesta.setMensaje("Guías creadas correctamente.");
        }

        return respuesta;
    }

    /**
     * Método valida que si el cliente usa COD con el CODCOB seleccionado.
     *
     * @param codcob
     * @param campoCOD
     * @param precioStr
     * @return - Respuesta general con código, mensaje y listado de errores
     * encntrados en las validaciones.
     */
    public RespuestaGeneral validaCOD(E_FacCliente codcob, String campoCOD, String precioStr) {
        Double precio = 0.0;
        List<String> Errores = new ArrayList<>();

        try {
            precio = Utils.convertirADouble(QuitaApostrofo.QuitaApostrofoPrecio(precioStr)).orElse((double) 0);
        } catch (Exception e) {
            System.err.println(e);
            Errores.add("Campo PRECIO inválido.");
        }

        if (codcob.getLCOD().equalsIgnoreCase("S")) {
            if (campoCOD.equalsIgnoreCase("S")) {
                if (precio < codcob.getCOD_MINMONTO()) {
                    Errores.add("Campo PRECIO: El valor COD mínimo admitido es: [Q." + codcob.getCOD_MINMONTO() + "]");
                }
                if (precio > codcob.getCOD_MAXMONTO()) {
                    Errores.add("Campo PRECIO: El valor COD máximo admitido es: [Q." + codcob.getCOD_MAXMONTO() + "]");
                }
            } else if (campoCOD.equalsIgnoreCase("N")) {
                Errores.add("Campo COD: código de cobro [" + codcob.getCODIGO() + "] tiene habilitado usar COD, marcar con S y colocar el valor COD a cobrar en el campo PRECIO.");
            }
        } else if (codcob.getLCOD().equalsIgnoreCase("N")) {
            if (campoCOD.equalsIgnoreCase("S")) {
                Errores.add("Campo COD: código de cobro [" + codcob.getCODIGO() + "] NO tiene habilitado usar COD, marcar con N o dejar vacío y colocar el valor COD vacío o marcar con 0 (cero).");
            } else if (campoCOD.equalsIgnoreCase("N")) {
                if (precio > 0) {
                    Errores.add("Campo PRECIO: código de cobro no tiene habilitado usar COD, el campo PRECIO debe de estar vacío o marcar con 0 (cero).");
                }
            }
        }

        if (Errores.size() > 0) {
            return new RespuestaGeneral("9999", "Existen " + Errores.size() + " errores", Errores);
        }
        return new RespuestaGeneral("0000", "No hay errores");
    }

    /**
     * Valida que el cliente traiga código, sino valida que traiga los demás
     * campos de información del destinatario y si no trae va marcando errores
     * en listado de estados.
     *
     * @param datos
     * @param codcob
     * @param padre
     * @return - Listado de E_DatosGuiaMasiva con cada uno de sus errores si es
     * que encuentra.
     */
    public List<E_DatosGuiaMasiva> validarClientexCodigo(List<E_DatosGuiaMasiva> datos, String codcob, String padre) {
        for (E_DatosGuiaMasiva datoFila : datos) {
            datoFila.getESTADO().clear();
            if (!quitaNulo(datoFila.getCODIGO()).isEmpty()) {
                E_Cliente cliente = new E_Cliente(padre, codcob, datoFila.getCODIGO());
                E_respuestaClientes respuestaCliente = new D_Clientes().ObtenerCliente(cliente);
                if (respuestaCliente.getCODIGO().equalsIgnoreCase("200")) {
                    for (E_Cliente cliCliente : respuestaCliente.getDATOS_CLIENTES()) {

                        //reemplaza datos de archivo excel con los provenientes de la db.
                        datoFila.setNOMBRE(quitaNulo(cliCliente.getNOMBRE()));
                        datoFila.setTELEFONO(quitaNulo(cliCliente.getTELEFONO()));
                        datoFila.setDIRECCION(quitaNulo(cliCliente.getDIRECCION()));
                        datoFila.setCODIGO_DESTINATARIO(quitaNulo(cliCliente.getCOBERTURA().getPUNTO()));
                        datoFila.setMUNICIPIO_DESTINATARIO(quitaNulo(cliCliente.getCOBERTURA().getUBICACION()));
                        datoFila.setCODIGODESTINO(quitaNulo(cliCliente.getCOBERTURA().getCODIGOPUNTO()));
                        datoFila.setCAMPO1(quitaNulo(cliCliente.getCAMPO1()));
                        datoFila.setCAMPO2(quitaNulo(cliCliente.getCAMPO2()));
                        datoFila.setCAMPO3(quitaNulo(cliCliente.getCAMPO3()));
                        datoFila.setCAMPO4(quitaNulo(cliCliente.getCAMPO4()));

                        validaDatosCliente(datoFila);

                        E_PuntoCobertura puntoCobertura = new D_PuntoCobertura().BuscarUbicacionEspecifica(datoFila.getCODIGO_DESTINATARIO(), datoFila.getMUNICIPIO_DESTINATARIO().trim());
                        if (puntoCobertura == null) {
                            datoFila.AddStateLastPosition("Campos CÓDIGO Y MUNICIPIO DESTINATARIO inválidos.");
                        } else {
                            datoFila.setCODIGODESTINO(puntoCobertura.getCODIGOPUNTO());
                        }
                    }
                } else {
                    datoFila.AddStateFirstPosition("Campo CÓDIGO: Código de cliente no existe o es inválido");
                }
            } else {
                if (datoFila.getNOMBRE().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo NOMBRE vacío.");
                }
                if (datoFila.getTELEFONO().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo TELÉFONO vacío.");
                }
                if (datoFila.getDIRECCION().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo DIRECCIÓN vacío.");
                }
                if (datoFila.getCODIGO_DESTINATARIO().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo CÓDIGO DESTINATARIO vacío.");
                }
                if (datoFila.getMUNICIPIO_DESTINATARIO().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo MUNICIPIO DESTINATARIO vacío. ");
                }
            }
        }
        return datos;
    }

    private void validaDatosCliente(E_DatosGuiaMasiva dato) {
        if (quitaNulo(dato.getNOMBRE()).isEmpty()) {
            dato.AddStateLastPosition("Cliente no tiene asignado un nombre, Favor actualizar en la pantalla de mantenimiento de clientes.");
        }
        if (quitaNulo(dato.getDIRECCION()).isEmpty()) {
            dato.AddStateLastPosition("Cliente no tiene asignada una dirección, Favor actualizar en la pantalla de mantenimiento de clientes.");
        }
        if (quitaNulo(dato.getCODIGO_DESTINATARIO()).isEmpty()) {
            dato.AddStateLastPosition("Cliente no tiene asignado punto de cobertura, Favor actualizar en la pantalla de mantenimiento de clientes.");
        }
        if (quitaNulo(dato.getMUNICIPIO_DESTINATARIO()).isEmpty()) {
            dato.AddStateLastPosition("Cliente no tiene asignado municipio de destino, Favor actualizar en la pantalla de mantenimiento de clientes.");
        }
        if (quitaNulo(dato.getCODIGODESTINO()).isEmpty()) {
            dato.AddStateLastPosition("Cliente no tiene asignado código de destino, Favor verificar punto de destino en la pantalla de mantenimiento de clientes.");
        }
    }

    /**
     * Valida cada linea de detalle, su tipo, pieza y peso son validados dentro
     * del objeto E_DetalleLinea.
     *
     * @param lineaDetalle
     * @param codigosTipoPieza
     * @return
     */
    public RespuestaGeneral validarLineasDetalle(List<E_DetalleLinea> lineaDetalle, List<Pair<String, String>> codigosTipoPieza) {
        List<String> Errores = new ArrayList<>();
        int index = 1;
        for (E_DetalleLinea detalle : lineaDetalle) {

            boolean encontrado = false;
            for (Pair<String, String> pair : codigosTipoPieza) {
                if (detalle.getTIPOENVIO().equals(pair.getKey())) {
                    encontrado = true;
                }
            }

            if (!encontrado) {
                Errores.add("Campo TIPO-PIEZA-PESO: En linea detalle " + index + " el tipo de pieza es inválido o no se encuentra habilitado para este usuario.");
            }

            if (detalle.getPIEZAS() <= 0) {
                Errores.add("Campo TIPO-PIEZA-PESO: En linea detalle " + index + " la cantidad de piezas es inválido.");
            }

            if (Double.parseDouble(detalle.getPESO()) <= 0) {
                Errores.add("Campo TIPO-PIEZA-PESO: En linea detalle " + index + " el peso es inválido.");
            }
            index++;
        }

        if (Errores.size() > 0) {
            return new RespuestaGeneral("9999", "Existen " + Errores.size() + " errores en las lineas de detalle", Errores);
        }
        return new RespuestaGeneral("0000", "No hay errores en las lineas de detalle");
    }

    /**
     * Parsea el formato de tpp que viene en formato de 1-1-1, 1-1-1.......N.
     * TPP = TIPO, PIEZA, PESO.
     *
     * @param tppString
     * @return - Listado de E_DetalleLinea encontrado, es decir, cada una de las
     * lineas detalle en el objeto E_DetalleLinea.
     */
    public List<E_DetalleLinea> parsearTPP(String tppString) {
        List<E_DetalleLinea> resultado = new ArrayList<>();
        if (tppString == null || tppString.isEmpty()) {
            return resultado;
        }

        E_DetalleLinea datos = new E_DetalleLinea();
        if (tppString.contains(",")) {
            String[] elementos = tppString.split(",");
            for (String elemento : elementos) {
                datos = parseaElementos(elemento.trim());
                if (datos != null) {
                    System.out.println("detalle de piezas:    tipo" + datos.getTIPOENVIO() + " pieza " + datos.getPIEZAS() + " peso " + datos.getPESO());
                    resultado.add(datos);
                } else {
                    return null;
                }
            }
        } else {
            datos = parseaElementos(tppString.trim());
            if (datos != null) {
                System.out.println("detalle de piezas:    tipo" + datos.getTIPOENVIO() + " pieza " + datos.getPIEZAS() + " peso " + datos.getPESO());
                resultado.add(datos);
            } else {
                return null;
            }
        }
        return resultado;
    }

    /**
     * Parsea los elementos envíados en formato de solo un elemento [1-1-1] Lo
     * que hace es que parte 1-1-1 en su respectivo campo de E_DetalleLinea. En
     * el ejemplo [1-1-1] sería: tipo = 1, pieza = 1, peso = 1.
     *
     * @param elemento
     * @return - Objeto E_DetalleLinea tipo = 1, pieza = 1, peso = 1, en caso de
     * error retorna null.
     */
    public E_DetalleLinea parseaElementos(String elemento) {
        E_DetalleLinea tipoPiezaPeso = new E_DetalleLinea();
        String[] partes = elemento.split("-");

        if (partes.length != 3) {
            return null;
        }

        tipoPiezaPeso.setTIPOENVIO(partes[0]);
        tipoPiezaPeso.setPIEZAS(Integer.parseInt(partes[1]));
        tipoPiezaPeso.setPESO(partes[2]);

        return tipoPiezaPeso;
    }

    /**
     * Quita los valores que vengan null y les setea un valor vacío, si viene un
     * dato le quita los escapcios vacios al final
     *
     * @param var
     * @return - valor vacío o valor.trim()
     */
    public String quitaNulo(String var) {
        return var == null ? "" : var.trim();
    }

    /**
     * Deserializa la cadena XML y la combierte en un objeto de tipo
     * E_GuiasMasivas.
     *
     * @param xml
     * @return - Objeto E_GuiasMasivas.
     */
    public E_GuiasMasivas parseoXML(String xml) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(E_GuiasMasivas.class);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();
            return (E_GuiasMasivas) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Serializa el objeto E_GuiasMasivas y lo convierte en una cadena con un
     * formato tipo XML. Este formato se realiza en base a la calse
     * E_RespuestaGuiasMasivas para así devolver código, mensaje y los dato
     * obtenidos.
     *
     * @param respuesta
     * @param datos
     * @return - Cadena XML en formato de la clase E_RespuestaGuiasMasivas.
     */
//    public String parseoRespuestaXML(RespuestaGeneral respuesta, List<E_DatosGuiaMasiva> datos, List<RespuestaTomaServicio> respuestaTomaServicio) {
//        try {
//            JAXBContext contexto = JAXBContext.newInstance(E_RespuestaGuiasMasivas.class);
//            Marshaller marshaller = contexto.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            StringWriter stringWriter = new StringWriter();
//            marshaller.marshal(new E_RespuestaGuiasMasivas(respuesta, datos, respuestaTomaServicio), stringWriter);
//            return stringWriter.toString();
//        } catch (JAXBException e) {
//            System.err.println("Ocurrio un error " + e.getMessage());
//        }
//        return null;
//    }
    public String parseoRespuestaXML(RespuestaGeneral respuesta, List<E_DatosGuiaMasiva> datos) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(E_RespuestaGuiasMasivas.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(new E_RespuestaGuiasMasivas(respuesta, datos), stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            System.err.println("Ocurrio un error " + e.getMessage());
        }
        return null;
    }

    public static String concatenarEstados(List<String> estados) {
        return estados.stream().collect(Collectors.joining(", "));
    }

    private static String tomaServicio(java.lang.String parameter) {
        com.guatex.tomaservicio.service.WSPGTomaServicio_Service service = new com.guatex.tomaservicio.service.WSPGTomaServicio_Service();
        com.guatex.tomaservicio.service.WSPGTomaServicio port = service.getWSPGTomaServicioPort();
        return port.tomaServicio(parameter);
    }
}
