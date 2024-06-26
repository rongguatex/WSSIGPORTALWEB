/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.controllers;

import com.guatex.sig.datos.D_Clientes;
import com.guatex.sig.datos.D_FacCliente;
import com.guatex.sig.datos.D_Tarifa;
import com.guatex.sig.datos.D_TarifaEnvio;
import com.guatex.sig.datos.D_ValoresCOD;
import com.guatex.sig.entidades.E_Cliente;
import com.guatex.sig.entidades.E_DatosGuiaMasiva;
import com.guatex.sig.entidades.E_DetalleLinea;
import com.guatex.sig.entidades.E_FacCliente;
import com.guatex.sig.entidades.E_GuiasMasivas;
import com.guatex.sig.entidades.E_PuntoCobertura;
import com.guatex.sig.entidades.E_TarifaEnvio;
import com.guatex.sig.entidades.E_TarifaMuni;
import com.guatex.sig.entidades.E_ValoresCOD;
import com.guatex.sig.entidades.E_respuestaClientes;
import com.guatex.sig.entidades.RespuestaGeneral;
import com.guatex.sig.entidades.RespuestaTomaServicio;
import com.guatex.sig.entidadesRespuesta.E_RespuestaGuiasMasivas;
import com.guatex.sig.utils.ConvertidorXML;
import com.guatex.sig.utils.QuitaApostrofo;
import com.guatex.sig.utils.ValidacionCredenciales;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
            //valida las credenciales del cliente
            RespuestaGeneral respuestaValidaCredenciales = new ValidacionCredenciales().validar(datos.getCredenciales());
            if ("0000".equals(respuestaValidaCredenciales.getCodigo())) {
                E_FacCliente parametrosRemitente = new D_FacCliente().obtenerFacCliente(datos.getCredenciales().getPadre(), datos.getCredenciales().getCodcob());

                //preparación de datos globales en entidad de cliente
                E_Cliente cliente = new E_Cliente();
                cliente.setCODIGO(datos.getCredenciales().getCodcob());
                cliente.setPADRE(datos.getCredenciales().getPadre());
                cliente.setCODCOB(datos.getCredenciales().getCodcob());
                cliente.setUNIFICACLI(parametrosRemitente.getUNIFICACLI());

                //se obtiene datos de cliente, en tarifaOrigen se usa punto y ubicación que se obtienen de este método.
                cliente = new D_Clientes().ObtenerCliente(cliente).getDATOS_CLIENTES().get(0);

                //obtener tarifa origen del remitente y valores parametrizados para COD
                E_TarifaMuni tarifaOrigen = new D_Tarifa().buscarTarifaMunicipio(cliente);
                E_ValoresCOD valorCod = new D_ValoresCOD().ObtengoValoresCOD(datos.getCredenciales().getCodcob());

                /**
                 * valida los datos del cliente x el código [código, nombre,
                 * teléfono, dirección, coddes, mncpdes] si código es válido
                 * reemplaza la información con la que existe en la bd.
                 */
                datos.setListaDatosGuia(validarClientexCodigo(datos.getListaDatosGuia(), cliente));

                List<E_DetalleLinea> lineasdetalle = new LinkedList<>();

                boolean existenErrores = false;

                /**
                 * For principal que realiza todas las validaciones.
                 */
                for (E_DatosGuiaMasiva dato : datos.getListaDatosGuia()) {
                    /**
                     * se asigna el código del cliente que se va a validar.
                     * se agrega datos del remitente.
                     */
                    System.out.println("\n------------------------------------------ Nuevo objeto -------------------------------------------------");
                    System.out.println("anterior [" + cliente.getCODIGO() + "] nuevo [" + dato.getCODIGO() + "]");
                    cliente.setCODIGO(dato.getCODIGO());
                    dato.setCODCOB(datos.getCredenciales().getCodcob());
                    dato.setCLIENTE(cliente);
                    dato.setTARIFA_ORIGEN(tarifaOrigen);

                    boolean errUbicacion = false;
                    dato.getESTADO().clear();

                    if (dato.getCODIGO().isEmpty()) {
                        if (dato.getCODIGO_DESTINATARIO().isEmpty()) {
                            dato.AddStateLastPosition("Campo CÓDIGO DESTINATARIO --> Campo vacío.");
                            errUbicacion = true;
                        }

                        if (dato.getMUNICIPIO_DESTINATARIO().isEmpty()) {
                            dato.AddStateLastPosition("Campo MUNICIPIO DESTINATARIO --> Campo vacío. ");
                            errUbicacion = true;
                        }
                    }

                    if (!errUbicacion) {
                        if (!dato.getCODIGO().isEmpty()) {
                            E_PuntoCobertura ubicacion = new D_Clientes().obtenerUbicacionCliCliente(datos.getCredenciales().getPadre(), dato.getCODIGO());
                            if (!ubicacion.getPUNTO().isEmpty() && !ubicacion.getUBICACION().isEmpty()) {
                                dato.setCODIGO_DESTINATARIO(ubicacion.getPUNTO());
                                dato.setMUNICIPIO_DESTINATARIO(ubicacion.getUBICACION());
                                System.out.println("validando ando: coddes [" + dato.getCODIGO_DESTINATARIO() + "] mncpdest [" + dato.getMUNICIPIO_DESTINATARIO() + "]");
                            } else {
                                dato.AddStateLastPosition("-Campo CÓDIGO Y MUNICIPIO DESTINATARIO --> Datos inválidos.");
                            }
                        }

                        E_TarifaMuni destino = new D_Tarifa().buscarTarifaMunicipio(new E_Cliente(dato.getCODIGO_DESTINATARIO(), dato.getMUNICIPIO_DESTINATARIO()));
                        if (destino == null) {
                            dato.AddStateLastPosition("Campo CÓDIGO Y MUNICIPIO DESTINATARIO --> Datos inválidos.");
                        } else {
                            dato.setPTODES(destino.getCODIGOCOBERTURA());
                        }

                    }

                    System.out.println("Antes de ingresar viene - TIPO-PIEZA-PESO " + dato.getTIPO_PIEZA_PESO());
                    /**
                     * Validación de TIPO PIEZA y PESO. parsea el valor tipo
                     * pieza y peso que viene en formato 1-1-1, 1-1-1... n
                     */
                    lineasdetalle = parsearTPP(QuitaApostrofo.QuitaApostrofoNumerosExcel(quitaNulo(dato.getTIPO_PIEZA_PESO())));
                    dato.setDETALLE(lineasdetalle);

                    /**
                     * Validación de campos descripción y llave.
                     */
                    if (dato.getDESCRIPCION().isEmpty()) {
                        dato.AddStateLastPosition("El campo DESCRIPCION vacío.");
                    }

                    /**
                     * Validacion de lineas detalle.
                     */
                    if (lineasdetalle != null) {
                        if (!lineasdetalle.isEmpty()) {
                            RespuestaGeneral respuesta = validarLineasDetalle(dato, lineasdetalle, datos.getCredenciales().getCodcob(), tarifaOrigen, parametrosRemitente);
                            if (respuesta.getCodigo().equals("9999")) {
                                dato.AddStatesErrorList(respuesta.getErrores());
                            }
                        } else {
                            System.out.println("Campo TIPO-PIEZA-PESO --> Campo vacío o inválido");
                            dato.AddStateLastPosition("Campo TIPO-PIEZA-PESO --> Campo vacío o inválido..");
                        }
                    } else {
                        System.out.println("Campo TIPO-PIEZA-PESO --> Campo inválido");
                        dato.AddStateLastPosition("Campo TIPO-PIEZA-PESO --> Campo inválido.");
                    }

                    /**
                     * Verifica si el cliente tiene COD Validación de campo COD
                     * y PRECIO.
                     */
                    if (!dato.getCOD().isEmpty()) {
                        if (quitaNulo(parametrosRemitente.getLCOD()).equalsIgnoreCase("N") || quitaNulo(parametrosRemitente.getLCOD()).equalsIgnoreCase("S")) {
                            RespuestaGeneral respuesta = validaCOD(parametrosRemitente, dato, valorCod);
                            if (respuesta.getCodigo().equals("9999")) {
                                dato.AddStatesErrorList(respuesta.getErrores());
                            }
                        } else {
                            dato.AddStateLastPosition("Campo COD --> Valor inválido.");
                        }
                    } else {
                        dato.AddStateLastPosition("Campo COD --> Campo vació.");
                    }

                    if (dato.getESTADO().size() > 0) {
                        dato.setESTADO(Arrays.asList(concatenarEstados(dato.getESTADO())));
                        existenErrores = true;
                    }
                    System.out.println("-------------> VALIDACION COMPLETA  Errores: [" + dato.getESTADO().size() + "] " + dato.getESTADO().toString());
                }

                /**
                 * Toma el servicio.
                 *
                 * Final de las validaciones para devolver la respuesta y llamar
                 * al web service de toma de servicio.
                 */
                String respuesta = "";
                if (!existenErrores) {
                    List<RespuestaTomaServicio> respuestaTomaServicio = tomadeServicio(datos);
                    respuesta = parseoRespuestaXML(new RespuestaGeneral("200", "Ok"), datos.getListaDatosGuia(), respuestaTomaServicio);
                } else {
                    respuesta = parseoRespuestaXML(new RespuestaGeneral("9999", "Existen errores en el archivo excel."), datos.getListaDatosGuia());
                }

                return respuesta;
            }
        } else {
            System.out.println("Credenciales inválidas.");
            return new ConvertidorXML().BadRequest();
        }
        return xml;
    }

    public List<RespuestaTomaServicio> tomadeServicio(E_GuiasMasivas objeto) {
        List<RespuestaTomaServicio> respuesta = new ArrayList<>();
        int nofila = 1;
        for (E_DatosGuiaMasiva datos : objeto.getListaDatosGuia()) {
            String XML
                    = "<TOMA_SERVICIO>"
                    + "	<USUARIO>" + objeto.getCredenciales().getUsuario() + "</USUARIO>"
                    + "	<PASSWORD>" + objeto.getCredenciales().getPassword() + "</PASSWORD>"
                    + "	<CODIGO_COBRO>" + objeto.getCredenciales().getCodcob() + "</CODIGO_COBRO>";
            String llevaCOD = "<COD_VALORACOBRAR />";
            String seabrepaquete = "<SEABREPAQUETE />";

            if (datos.getCOD().equalsIgnoreCase("S")) {
                llevaCOD = "<COD_VALORACOBRAR>S</COD_VALORACOBRAR>";
                seabrepaquete = "<SEABREPAQUETE>S</SEABREPAQUETE >";
            }

            XML
                    += "       	<SERVICIO>"
                    + "		<CONTACTO></CONTACTO>"
                    + "		<TIPO_USUARIO>C</TIPO_USUARIO>"
                    + "		<NOMBRE_REMITENTE>" + datos.getNOMBRE() + "</NOMBRE_REMITENTE>"
                    + "		<TELEFONO_REMITENTE>" + datos.getTELREM() + "</TELEFONO_REMITENTE>"
                    + "		<DIRECCION_REMITENTE>" + datos.getDIRREM() + "</DIRECCION_REMITENTE>"
                    + "		<MUNICIPIO_ORIGEN>A</MUNICIPIO_ORIGEN>"
                    + "		<PUNTO_ORIGEN>A</PUNTO_ORIGEN>"
                    + "		<ESTA_LISTO>S</ESTA_LISTO>"
                    + "		<CODORIGEN>" + datos.getCLIENTE().getCOBERTURA().getCODIGOPUNTO() + "</CODORIGEN>"
                    + "		<GUIA>"
                    + "			<LLAVE_CLIENTE>" + datos.getLLAVE() + "</LLAVE_CLIENTE>"
                    + "			" + llevaCOD + " "
                    + "			" + seabrepaquete + ""
                    + "			<CODIGO_COBRO_GUIA>" + objeto.getCredenciales().getCodcob() + "</CODIGO_COBRO_GUIA>"
                    + "			<NOMBRE_DESTINATARIO>" + datos.getNOMBRE() + "</NOMBRE_DESTINATARIO>"
                    + "			<TELEFONO_DESTINATARIO>" + datos.getTELEFONO() + "</TELEFONO_DESTINATARIO>"
                    + "			<DIRECCION_DESTINATARIO>" + datos.getDIRECCION() + "</DIRECCION_DESTINATARIO>"
                    + "			<MUNICIPIO_DESTINO>A</MUNICIPIO_DESTINO>"
                    + "			<PUNTO_DESTINO>A</PUNTO_DESTINO>"
                    + "			<DESCRIPCION_ENVIO>" + datos.getDESCRIPCION() + "</DESCRIPCION_ENVIO>"
                    + "			<OBSERVACIONES></OBSERVACIONES>"
                    + "			<RECOGE_OFICINA>N</RECOGE_OFICINA>"
                    + "			<CODDESTINO>" + datos.getPTODES() + "</CODDESTINO>";

            int linea = 1;
            for (E_DetalleLinea lineaDetalle : datos.getDETALLE()) {
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
                    + "			<CAMPO1 />"
                    + "			<CAMPO2 />"
                    + "			<CAMPO3 />"
                    + "			<CAMPO4 />"
                    + "		</GUIA>"
                    + "	</SERVICIO>";

            XML += "</TOMA_SERVICIO>";

            respuesta.add(new RespuestaTomaServicio(nofila, linea - 1, new ConvertidorXML().parseoRespuestaTomaServicio(tomaServicio(XML))));
            nofila++;
        }

        return respuesta;
    }

    /**
     * Método valida que si el cliente usa COD con el CODCOB seleccionado.
     *
     * @param parametrosRemitente
     * @param dato
     * @param valorCod
     * @return - Respuesta general con código, mensaje y listado de errores
     * encntrados en las validaciones.
     */
    public RespuestaGeneral validaCOD(E_FacCliente parametrosRemitente, E_DatosGuiaMasiva dato, E_ValoresCOD valorCod) {
        List<String> Errores = new ArrayList<>();
        String precioCOD = "";

        try {
            precioCOD = QuitaApostrofo.QuitaApostrofoPrecio(dato.getPRECIO().trim());
        } catch (Exception e) {
            precioCOD = "0";
            e.printStackTrace();
            System.err.println("Ocurrio un error " + e.getMessage());
        }

        if (!precioCOD.isEmpty()) {
            dato.setPRECIO(precioCOD);
        }

        if (dato.getCOD().equalsIgnoreCase("S")) {
            if (parametrosRemitente.getLCOD().equalsIgnoreCase("S")) {
                try {
                    double precio = dato.getPRECIO().equals("") ? 0 : Double.parseDouble(dato.getPRECIO());
                    if (precio < valorCod.getValorCODMin()) {
                        Errores.add("Campo PRECIO --> El valor COD mínimo admitido es: [Q." + valorCod.getValorCODMin() + "]");
                    }

                    if (precio > valorCod.getValorCODMax()) {
                        Errores.add("Campo PRECIO --> El valor COD máximo admitido es: [Q." + valorCod.getValorCODMax() + "]");
                    }
                } catch (NumberFormatException e) {
                    Errores.add("Campo PRECIO --> Campo inválido.");
                }
            } else {
                Errores.add("Campo COD --> CODCOB no tiene habilitado usar COD.marcar con N y dejar vacío el campo PRECIO.");
            }
        } else {
            if (quitaNulo(parametrosRemitente.getLCOD()).equalsIgnoreCase("N")) {
                try {
                    double precio = dato.getPRECIO().equals("") ? 0 : Double.parseDouble(dato.getPRECIO());
                    if (precio > 0) {
                        Errores.add("Campo PRECIO --> CODCOB no tiene habilitado usar COD, Campo PRECIO debe de estar vacío o marcar con 0 (cero).");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error en el parseo del dato [" + dato.getPRECIO() + " a double, se muestra el mensaje de excepción pero el proceso continua normal y se envía mensaje de error en el estado]");
                    e.printStackTrace();
                    Errores.add("Campo PRECIO --> Campo inválido.");
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
     * @param cliente
     * @return - Listado de E_DatosGuiaMasiva con cada uno de sus errores si es
     * que encuentra.
     */
    public List<E_DatosGuiaMasiva> validarClientexCodigo(List<E_DatosGuiaMasiva> datos, E_Cliente cliente) {
        for (E_DatosGuiaMasiva datoFila : datos) {
            if (!quitaNulo(datoFila.getCODIGO()).isEmpty()) {
                cliente.setCODIGO(datoFila.getCODIGO());
                E_respuestaClientes respuestaCliente = new D_Clientes().ObtenerCliente(cliente);
                if (respuestaCliente.getCODIGO().equalsIgnoreCase("200")) {
                    for (E_Cliente cliCliente : respuestaCliente.getDATOS_CLIENTES()) {

                        //reemplaza datos de archivo excel con los provenientes de la db.
                        datoFila.setNOMBRE(quitaNulo(cliCliente.getNOMBRE()));
                        datoFila.setTELEFONO(quitaNulo(cliCliente.getTELEFONO()));
                        datoFila.setDIRECCION(quitaNulo(cliCliente.getDIRECCION()));
                        datoFila.setCODIGO_DESTINATARIO(quitaNulo(cliCliente.getCOBERTURA().getPUNTO()));
                        datoFila.setMUNICIPIO_DESTINATARIO(quitaNulo(cliCliente.getCOBERTURA().getUBICACION()));
                    }
                } else {
                    datoFila.AddStateFirstPosition("Campo CÓDIGO --> Código de cliente no existe o es inválido");
                }
            } else {
                if (datoFila.getNOMBRE().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo NOMBRE --> Campo vacío.");
                }
                if (datoFila.getTELEFONO().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo TELÉFONO --> Campo vacío.");
                }
                if (datoFila.getDIRECCION().isEmpty()) {
                    datoFila.AddStateLastPosition("Campo DIRECCIÓN --> Campo vacío.");
                }
            }
        }
        return datos;
    }

    /**
     * Valida cada linea de detalle, su tipo, pieza y peso son validados dentro
     * del objeto E_DetalleLinea.
     *
     * @param dato
     * @param lineaDetalle
     * @param codcob
     * @param tarifaOrigen
     * @return - Respuesta general con código, mensaje y listado de errores
     * encntrados en las validaciones.
     */
    public RespuestaGeneral validarLineasDetalle(E_DatosGuiaMasiva dato, List<E_DetalleLinea> lineaDetalle, String codcob, E_TarifaMuni tarifaOrigen, E_FacCliente parametrosRemitente) {
        List<String> Errores = new ArrayList<>();
        int index = 1;
        for (E_DetalleLinea detalle : lineaDetalle) {
            String codigoTarifa = obtenerCodigoTarifa(dato, codcob, tarifaOrigen, parametrosRemitente);
            
            if (codigoTarifa != null) {
                E_TarifaEnvio tarifaEnvio = new D_TarifaEnvio().BuscarTipoEnvio(codigoTarifa, detalle.getTIPOENVIO());
                if (tarifaEnvio == null) {
                    Errores.add("Campo TIPO-PIEZA-PESO --> En linea detalle " + index + " el tipo de pieza es inválido.");
                }
            }

            if (detalle.getPIEZAS() <= 0) {
                Errores.add("Campo TIPO-PIEZA-PESO --> En linea detalle " + index + " la cantidad de piezas es inválido.");
            }

            if (Double.parseDouble(detalle.getPESO()) <= 0) {
                Errores.add("Campo TIPO-PIEZA-PESO --> En linea detalle " + index + " el peso es inválido.");
            }
            index++;
        }

        System.out.println("Cantidad Errores en validación de guías detalle: " + Errores.size() + " Errores: " + Errores.toString());

        if (Errores.size() > 0) {
            return new RespuestaGeneral("9999", "Existen " + Errores.size() + " errores en las lineas de detalle", Errores);
        }
        return new RespuestaGeneral("0000", "No hay errores en las lineas de detalle");
    }

    /**
     * Obtiene el código de la tárifa que se usará esto para validar cada tipo
     * de pieza en validarLineasDetalle.
     *
     * @param dato
     * @param codcob
     * @param tarifaOrigen
     * @return - String con el códgio de la tárifa del envío.
     */
    public String obtenerCodigoTarifa(E_DatosGuiaMasiva dato, String codcob, E_TarifaMuni tarifaOrigen, E_FacCliente tarifaCliente) {
        String cobex = "N";

        /**
         * validación de cobertura extra
         */
        if (tarifaOrigen != null) {
            if (tarifaOrigen.getCOBERTURAEXTRA().equalsIgnoreCase("S")) {
                cobex = "S";
            }
        }

        E_TarifaMuni tarifaDestino = new D_Tarifa().buscarTarifaMunicipio(new E_Cliente(dato.getCODIGO_DESTINATARIO(), dato.getMUNICIPIO_DESTINATARIO()));
        if (tarifaDestino != null) {
            if (tarifaDestino.getCOBERTURAEXTRA().equalsIgnoreCase("S")) {
                cobex = "S";
            }
        }

        if (tarifaCliente != null) {
            String codigoTarifa;
            if (!quitaNulo(tarifaCliente.getTARIFAUNICA()).isEmpty()) {
                codigoTarifa = quitaNulo(tarifaCliente.getTARIFAUNICA());
            } else if (cobex.equalsIgnoreCase("S")) {
                codigoTarifa = quitaNulo(tarifaCliente.getTARIFAEXTRA());
            } else {
                codigoTarifa = quitaNulo(tarifaCliente.getTARIFANORMAL());
            }
            System.out.println("Código de tarifa [" + codigoTarifa + "]  cobex [" + cobex + "]");
            return codigoTarifa;
        }
        return null;
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

//    private String obtenerEstadosConcatenados(List<E_DatosGuiaMasiva> datosGuia) {
//        StringBuilder estadosConcatenados = new StringBuilder();
//        datosGuia.forEach((dato) -> {
//            dato.getESTADO().forEach((estado) -> {
//                estadosConcatenados.append(estado).append(",");
//            });
//        });
//        return estadosConcatenados.toString().trim();
//    }
//    private void asignarEstadosConcatenados(List<E_DatosGuiaMasiva> datosGuia, String estadosConcatenados) {
//        for (E_DatosGuiaMasiva dato : datosGuia) {
//            dato.setESTADO(Arrays.asList(estadosConcatenados.split(",")));
//        }
//    }
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
    public String parseoRespuestaXML(RespuestaGeneral respuesta, List<E_DatosGuiaMasiva> datos, List<RespuestaTomaServicio> respuestaTomaServicio) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(E_RespuestaGuiasMasivas.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(new E_RespuestaGuiasMasivas(respuesta, datos, respuestaTomaServicio), stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            System.err.println("Ocurrio un error " + e.getMessage());
        }
        return null;
    }

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
