/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import com.guatex.sig.utils.Utils;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGALICIA
 */
@XmlRootElement(name = "DATOSGUIA")
@XmlAccessorType(XmlAccessType.FIELD)
public class E_Guia {

    Utils util = new Utils();

    @XmlElement(name = "CODCOB")
    private String _CODCOB;

    @XmlElement(name = "IDGUIA")
    private String _IDGUIA;

    @XmlElement(name = "NOGUIA")
    private String _NOGUIA;

    @XmlElement(name = "FECHA")
    private String _FECHA;

    @XmlElement(name = "CODREM")
    private String _CODREM;

    @XmlElement(name = "NOMREM")
    private String _NOMREM;

    @XmlElement(name = "TELREM")
    private String _TELREM;

    @XmlElement(name = "DIRREM")
    private String _DIRREM;

    @XmlElement(name = "CODDES")
    private String _CODDES;

    @XmlElement(name = "NOMDES")
    private String _NOMDES;

    @XmlElement(name = "TELDES")
    private String _TELDES;

    @XmlElement(name = "DIRDES")
    private String _DIRDES;

    @XmlElement(name = "PTOORI")
    private String _PTOORI;

    @XmlElement(name = "PTODES")
    private String _PTODES;

    @XmlElement(name = "DECLARADO")
    private String _DECLARADO;

    @XmlElement(name = "SEGURO")
    private String _SEGURO;

    @XmlElement(name = "CONTSEG")
    private String _CONTSEG;

    @XmlElement(name = "FECOPE")
    private String _FECOPE;

    @XmlElement(name = "HORAOPE")
    private String _HORAOPE;

    @XmlElement(name = "DESCRENV")
    private String _DESCRENV;

    @XmlElement(name = "RECOGEOFICINA")
    private String _RECOGEOFICINA;

    @XmlElement(name = "MNCPORI")
    private String _MNCPORI;

    @XmlElement(name = "MNCPDES")
    private String _MNCPDES;

    @XmlElement(name = "COMPLEMENTODIRREM")
    private String _COMPLEMENTODIRREM;

    @XmlElement(name = "COMPLEMENTODIRDES")
    private String _COMPLEMENTODIRDES;

    @XmlElement(name = "CONTACTO")
    private String _CONTACTO;

    @XmlElement(name = "LLAVECLIENTE")
    private String _LLAVECLIENTE;

    @XmlElement(name = "EMAIL")
    private String _EMAIL;

    @XmlElement(name = "IDSERVICIO")
    private String _IDSERVICIO;

    @XmlElement(name = "TIPOUSUARIO")
    private String _TIPOUSUARIO;

    @XmlElement(name = "DATOSMINIMOS")
    private String _DATOSMINIMOS;

    @XmlElement(name = "IMPRESO")
    private String _IMPRESO;

    @XmlElement(name = "CAMPO1")
    private String _CAMPO1;

    @XmlElement(name = "CAMPO2")
    private String _CAMPO2;

    @XmlElement(name = "CAMPO3")
    private String _CAMPO3;

    @XmlElement(name = "CAMPO4")
    private String _CAMPO4;

    @XmlElement(name = "COBEX")
    private String _COBEX;

    @XmlElement(name = "TIPTAR")
    private String _TIPTAR;

    @XmlElement(name = "RUTAZONA")
    private String _RUTAZONA;

    @XmlElement(name = "EMAILENVIADO")
    private String _EMAILENVIADO;

    @XmlElement(name = "IMPOPER")
    private String _IMPOPER;

    @XmlElement(name = "TIPOENVIOS")
    private String _TIPOENVIOS;

    @XmlElement(name = "PIEZAS")
    private int _PIEZAS;

    @XmlElement(name = "NUMERO_PIEZA")
    private int _NUMERO_PIEZA;

    @XmlElement(name = "PESO")
    private String _PESO;

    @XmlElementWrapper(name = "LISTADODETALLEGUIA")
    @XmlElement(name = "LINEADETALLE")
    private List<E_DetalleLinea> _DETALLE = new LinkedList<>();

    @XmlElement(name = "SELECCIONADO")
    private boolean _SELECCIONADO;

    @XmlElement(name = "IMPRIMECOD")
    private String _IMPRIMECOD;

    @XmlElement(name = "PRECIOCOD")
    private String _PRECIOCOD;

    @XmlElement(name = "SEABREPAQUETE")
    private String _SEABREPAQUETE = "";

    @XmlElement(name = "FECHA_INICIAL")
    private String _FECHA_INICIAL = "";

    @XmlElement(name = "FECHA_FINAL")
    private String _FECHA_FINAL = "";

    @XmlElement(name = "TABLA")
    private String _TABLA = "";

    @XmlElement(name = "COD_VALORACOBRAR")
    private String _COD_VALORACOBRAR = "";

    @XmlElement(name = "OBSERVACIONES")
    private String _OBSERVACIONES = "";

    @XmlElement(name = "OBSERVACIONESENTRE")
    private String _OBSERVACIONESENTRE = "";

    @XmlElement(name = "CODORIGEN")
    private String _CODORIGEN = "";

    @XmlElement(name = "CODDESTINO")
    private String _CODDESTINO = "";

    @XmlElement(name = "TIPOGUIA")
    private String _TIPOGUIA = "";

    public String getTIPOGUIA() {
        return _TIPOGUIA;
    }

    public void setTIPOGUIA(String _TIPOGUIA) {
        this._TIPOGUIA = _TIPOGUIA;
    }

    public String getCOD_VALORACOBRAR() {
        return _COD_VALORACOBRAR;
    }

    public void setCOD_VALORACOBRAR(String _COD_VALORACOBRAR) {
        this._COD_VALORACOBRAR = _COD_VALORACOBRAR;
    }

    public void setNOGUIA(String _NOGUIA) {
        this._NOGUIA = _NOGUIA;
    }

    public String getNOGUIA() {
        return _NOGUIA;
    }

    /**
     * @return the PRECIOCOD
     */
    public String getPRECIOCOD() {
        this._PRECIOCOD = quitaNulo(this._PRECIOCOD);
        return _PRECIOCOD.equalsIgnoreCase("") ? "0" : this._PRECIOCOD;
    }

    /**
     * @param PRECIOCOD the PRECIOCOD to set
     */
    public void setPRECIOCOD(String PRECIOCOD) {
        PRECIOCOD = quitaNulo(PRECIOCOD);
        this._PRECIOCOD = PRECIOCOD.equalsIgnoreCase("") ? "0" : PRECIOCOD;
    }

    public String getFECHA() {
        return _FECHA;
    }

    public void setFECHA(String _FECHA) {
        if (_FECHA != null) {
            this._FECHA = _FECHA.trim();
        }
    }

    public String getCODREM() {
        return util.validarLogitud(_CODREM, 15);
    }

    public void setCODREM(String _CODREM) {
        this._CODREM = util.validarLogitud(_CODREM, 15);
    }

    public String getNOMREM() {
        return util.validarLogitud(_NOMREM, 100);
    }

    public void setNOMREM(String _NOMREM) {
        this._NOMREM = util.validarLogitud(_NOMREM, 100);
    }

    public String getTELREM() {
        return util.validarLogitud(_TELREM, 20);
    }

    public void setTELREM(String _TELREM) {
        this._TELREM = util.validarLogitud(_TELREM, 20);
    }

    public String getDIRREM() {
        return util.validarLogitud(_DIRREM, 100);
    }

    public void setDIRREM(String _DIRREM) {
        if (_DIRREM != null) {
            if (_DIRREM.length() > 100) {
                this._DIRREM = _DIRREM.substring(0, 100).trim();
                this._COMPLEMENTODIRREM = _DIRREM.substring(100, _DIRREM.length());
            } else {
                this._DIRREM = _DIRREM.trim();
            }
        }
    }

    public String getCODDES() {
        return util.validarLogitud(_CODDES, 15);
    }

    public void setCODDES(String _CODDES) {
        this._CODDES = util.validarLogitud(_CODDES, 15);
    }

    public String getNOMDES() {
        return util.validarLogitud(_NOMDES, 100);
    }

    public void setNOMDES(String _NOMDES) {
        if (_NOMDES != null) {
            this._NOMDES = util.validarLogitud(_NOMDES, 100);
        }
    }

    public String getTELDES() {
        return util.validarLogitud(_TELDES, 20);
    }

    public void setTELDES(String _TELDES) {
        if (_TELDES != null) {
            this._TELDES = util.validarLogitud(_TELDES, 20);
        }
    }

    public String getDIRDES() {
        return util.validarLogitud(_DIRDES, 100);
    }

    public void setDIRDES(String _DIRDES) {
        if (_DIRDES != null) {
            if (_DIRDES.length() > 100) {
                this._DIRDES = _DIRDES.substring(0, 100).trim();
                this._COMPLEMENTODIRDES = _DIRDES.substring(100, _DIRDES.length());
            } else {
                this._DIRDES = _DIRDES.trim();
            }
        }
    }

    public String getPTOORI() {
        return _PTOORI;
    }

    public void setPTOORI(String _PTOORI) {
        if (_PTOORI != null) {
            this._PTOORI = _PTOORI.trim();
        }

    }

    public String getPTODES() {
        return _PTODES;
    }

    public void setPTODES(String _PTODES) {
        if (_PTODES != null) {
            this._PTODES = _PTODES.trim();
        }
    }

    public String getCODCOB() {
        return _CODCOB;
    }

    public void setCODCOB(String _CODCOB) {
        if (_CODCOB != null) {
            this._CODCOB = _CODCOB.trim();
        }
    }

    public String getCONTSEG() {
        return _CONTSEG;
    }

    public void setCONTSEG(String _CONTSEG) {
        if (_CONTSEG != null) {
            this._CONTSEG = _CONTSEG.trim();
        }
    }

    public String getFECOPE() {
        return _FECOPE;
    }

    public void setFECOPE(String _FECOPE) {
        if (_FECOPE != null) {
            this._FECOPE = _FECOPE.trim();
        }
    }

    public String getHORAOPE() {
        return _HORAOPE;
    }

    public void setHORAOPE(String _HORAOPE) {
        if (_HORAOPE != null) {
            this._HORAOPE = _HORAOPE;
        }
    }

    public String getDESCRENV() {
        return util.validarLogitud(_DESCRENV, 100);
    }

    public void setDESCRENV(String _DESCRENV) {
        if (_DESCRENV != null) {
            this._DESCRENV = util.validarLogitud(_DESCRENV, 100);
        }
    }

    public String getRECOGEOFICINA() {
        return _RECOGEOFICINA;
    }

    public void setRECOGEOFICINA(String _RECOGEOFICINA) {
        if (_RECOGEOFICINA != null) {
            this._RECOGEOFICINA = _RECOGEOFICINA.trim();
        }
    }

    public String getMNCPORI() {
        return _MNCPORI;
    }

    public void setMNCPORI(String _MNCPORI) {
        if (_MNCPORI != null) {
            this._MNCPORI = _MNCPORI.trim();
        }
    }

    public String getMNCPDES() {
        return _MNCPDES;
    }

    public void setMNCPDES(String _MNCPDES) {
        if (_MNCPDES != null) {
            this._MNCPDES = _MNCPDES.trim();
        }
    }

    public String getCOMPLEMENTODIRREM() {
        return util.validarLogitud(_COMPLEMENTODIRREM, 100);
    }

    public void setCOMPLEMENTODIRREM(String _COMPLEMENTODIRREM) {
        if (_COMPLEMENTODIRREM != null) {
            this._COMPLEMENTODIRREM = util.validarLogitud(_COMPLEMENTODIRREM, 100);
        }
    }

    public String getCOMPLEMENTODIRDES() {
        return util.validarLogitud(_COMPLEMENTODIRDES, 100);
    }

    public void setCOMPLEMENTODIRDES(String _COMPLEMENTODIRDES) {
        if (_COMPLEMENTODIRDES != null) {
            this._COMPLEMENTODIRDES = util.validarLogitud(_COMPLEMENTODIRDES, 100);
        }
    }

    public String getCONTACTO() {
        return util.validarLogitud(_CONTACTO, 200);
    }

    public void setCONTACTO(String _CONTACTO) {
        if (_CONTACTO != null) {
            this._CONTACTO = util.validarLogitud(_CONTACTO, 200);
        }
    }

    public String getLLAVECLIENTE() {
        return util.validarLogitud(_LLAVECLIENTE, 200);
    }

    public void setLLAVECLIENTE(String _LLAVECLIENTE) {
        if (_LLAVECLIENTE != null) {
            this._LLAVECLIENTE = util.validarLogitud(_LLAVECLIENTE, 200);
        }
    }

    public String getEMAIL() {
        return util.validarLogitud(_EMAIL, 200);
    }

    public void setEMAIL(String _EMAIL) {
        if (_EMAIL != null) {
            this._EMAIL = util.validarLogitud(_EMAIL, 200);
        }
    }

    public String getIDSERVICIO() {
        return _IDSERVICIO;
    }

    public void setIDSERVICIO(String _IDSERVICIO) {
        if (_IDSERVICIO != null) {
            this._IDSERVICIO = _IDSERVICIO.trim();
        }
    }

    public String getTIPOUSUARIO() {
        return _TIPOUSUARIO;
    }

    public void setTIPOUSUARIO(String _TIPOUSUARIO) {
        if (_TIPOUSUARIO != null) {
            this._TIPOUSUARIO = _TIPOUSUARIO.trim();
        }
    }

    public String getDATOSMINIMOS() {
        return _DATOSMINIMOS;
    }

    public void setDATOSMINIMOS(String _DATOSMINIMOS) {
        if (_DATOSMINIMOS != null) {
            this._DATOSMINIMOS = _DATOSMINIMOS.trim();
        }
    }

    public String getIMPRESO() {
        return _IMPRESO;
    }

    public void setIMPRESO(String _IMPRESO) {
        if (_IMPRESO != null) {
            this._IMPRESO = _IMPRESO.trim();
        }
    }

    public String getCAMPO1() {
        return _CAMPO1;
    }

    public void setCAMPO1(String _CAMPO1) {
        if (_CAMPO1 != null) {
            this._CAMPO1 = _CAMPO1.trim();
        }
    }

    public String getCAMPO2() {
        return _CAMPO2;
    }

    public void setCAMPO2(String _CAMPO2) {
        if (_CAMPO2 != null) {
            this._CAMPO2 = _CAMPO2.trim();
        }
    }

    public String getCAMPO3() {
        return _CAMPO3;
    }

    public void setCAMPO3(String _CAMPO3) {
        if (_CAMPO3 != null) {
            this._CAMPO3 = _CAMPO3.trim();
        }
    }

    public String getCAMPO4() {
        return _CAMPO4;
    }

    public void setCAMPO4(String _CAMPO4) {
        if (_CAMPO4 != null) {
            this._CAMPO4 = _CAMPO4.trim();
        }
    }

    public String getCOBEX() {
        return _COBEX;
    }

    public void setCOBEX(String _COBEX) {
        if (_COBEX != null) {
            this._COBEX = _COBEX.trim();
        }
    }

    public String getTIPTAR() {
        return _TIPTAR;
    }

    public void setTIPTAR(String _TIPTAR) {
        if (_TIPTAR != null) {
            this._TIPTAR = _TIPTAR.trim();
        }
    }

    public String getRUTAZONA() {
        return _RUTAZONA;
    }

    public void setRUTAZONA(String _RUTAZONA) {
        if (_RUTAZONA != null) {
            this._RUTAZONA = _RUTAZONA.trim();
        }
    }

    public int getPIEZAS() {
        return _PIEZAS;
    }

    public void setPIEZAS(int _PIEZAS) {
        this._PIEZAS = _PIEZAS;
    }

    public String getEMAILENVIADO() {
        return _EMAILENVIADO;
    }

    public void setEMAILENVIADO(String _EMAILENVIADO) {
        if (_EMAILENVIADO != null) {
            this._EMAILENVIADO = _EMAILENVIADO.trim();
        }
    }

    public String getIMPOPER() {
        return _IMPOPER;
    }

    public void setIMPOPER(String _IMPOPER) {
        if (_IMPOPER != null) {
            this._IMPOPER = _IMPOPER.trim();
        }
    }

    public String getTIPOENVIOS() {
        return _TIPOENVIOS;
    }

    public void setTIPOENVIOS(String _TIPOENVIOS) {
        if (_TIPOENVIOS != null) {
            this._TIPOENVIOS = _TIPOENVIOS.trim();
        }
    }

    private String quitaNulo(String var) {
        return var != null ? var.trim() : "";
    }

    public String getIDGUIA() {
        return _IDGUIA;
    }

    public void setIDGUIA(String _IDGUIA) {
        this._IDGUIA = _IDGUIA;
    }

    public String getDECLARADO() {
        return _DECLARADO;
    }

    public void setDECLARADO(String _DECLARADO) {
        this._DECLARADO = _DECLARADO;
    }

    public String getSEGURO() {
        return _SEGURO;
    }

    public void setSEGURO(String _SEGURO) {
        this._SEGURO = _SEGURO;
    }

    public int getNUMERO_PIEZA() {
        return _NUMERO_PIEZA;
    }

    public void setNUMERO_PIEZA(int _NUMERO_PIEZA) {
        this._NUMERO_PIEZA = _NUMERO_PIEZA;
    }

    public String getPESO() {
        return _PESO;
    }

    public void setPESO(String _PESO) {
        this._PESO = _PESO;
    }

    public String getFECHA_INICIAL() {
        return _FECHA_INICIAL;
    }

    public void setFECHA_INICIAL(String _FECHA_INICIAL) {
        this._FECHA_INICIAL = _FECHA_INICIAL;
    }

    public String getFECHA_FINAL() {
        return _FECHA_FINAL;
    }

    public void setFECHA_FINAL(String _FECHA_FINAL) {
        this._FECHA_FINAL = _FECHA_FINAL;
    }

    public String getTABLA() {
        return _TABLA;
    }

    public void setTABLA(String _TABLA) {
        this._TABLA = _TABLA;
    }

    public boolean isSELECCIONADO() {
        return _SELECCIONADO;
    }

    public void setSELECCIONADO(boolean _SELECCIONADO) {
        this._SELECCIONADO = _SELECCIONADO;
    }

    public String getSEABREPAQUETE() {
        return _SEABREPAQUETE;
    }

    public void setSEABREPAQUETE(String _SEABREPAQUETE) {
        this._SEABREPAQUETE = _SEABREPAQUETE;
    }

    public List<E_DetalleLinea> getDETALLE() {
        return _DETALLE;
    }

    public void setDETALLE(List<E_DetalleLinea> _DETALLE) {
        this._DETALLE = _DETALLE;
    }

    public String getIMPRIMECOD() {
        return _IMPRIMECOD;
    }

    public void setIMPRIMECOD(String _IMPRIMECOD) {
        this._IMPRIMECOD = _IMPRIMECOD;
    }

    public String getOBSERVACIONES() {
        return util.validarLogitud(_OBSERVACIONES, 100);
    }

    public void setOBSERVACIONES(String _OBSERVACIONES) {
        this._OBSERVACIONES = util.validarLogitud(_OBSERVACIONES, 100);
    }

    public String getOBSERVACIONESENTRE() {
        return util.validarLogitud(_OBSERVACIONESENTRE, 100);
    }

    public void setOBSERVACIONESENTRE(String _OBSERVACIONESENTRE) {
        this._OBSERVACIONESENTRE = util.validarLogitud(_OBSERVACIONESENTRE, 100);
    }

    public String getCODORIGEN() {
        return _CODORIGEN;
    }

    public void setCODORIGEN(String _CODORIGEN) {
        this._CODORIGEN = _CODORIGEN;
    }

    public String getCODDESTINO() {
        return _CODDESTINO;
    }

    public void setCODDESTINO(String _CODDESTINO) {
        this._CODDESTINO = _CODDESTINO;
    }

    @Override
    public String toString() {
        return "E_Guia{ \n"
                + "     CODCOB=" + _CODCOB + ", \n"
                + "     NOGUIA=" + _NOGUIA + ", \n"
                + "     NOMREM=" + _NOMREM + ", \n"
                + "     CODDES=" + _CODDES + ",\n "
                + "     PTOORI=" + _PTOORI + ", \n"
                + "     PTODES=" + _PTODES + ", \n "
                + "     MNCPORI=" + _MNCPORI + ", \n"
                + "     MNCPDES=" + _MNCPDES + ", \n"
                + "     IDSERVICIO=" + _IDSERVICIO + ", \n"
                + "     IMPRESO=" + _IMPRESO + ", \n "
                + "DETALLE=" + _DETALLE + '}';
    }

}
