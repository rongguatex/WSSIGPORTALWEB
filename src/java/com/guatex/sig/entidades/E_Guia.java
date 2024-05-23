/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class E_Guia {

    private String _CODCOB;
    private String _IDGUIA;
    private String _NOGUIA;
    private String _FECHA;
    private String _CODREM;
    private String _NOMREM;
    private String _TELREM;
    private String _DIRREM;
    private String _CODDES;
    private String _NOMDES;
    private String _TELDES;
    private String _DIRDES;
    private String _PTOORI;
    private String _PTODES;
    private String _DECLARADO;
    private String _SEGURO;
    private String _CONTSEG;
    private String _FECOPE;
    private String _HORAOPE;
    private String _DESCRENV;
    private String _RECOGEOFICINA;
    private String _MNCPORI;
    private String _MNCPDES;
    private String _COMPLEMENTODIRREM;
    private String _COMPLEMENTODIRDES;
    private String _CONTACTO;
    private String _LLAVECLIENTE;
    private String _EMAIL;
    private String _IDSERVICIO;
    private String _TIPOUSUARIO;
    private String _DATOSMINIMOS;
    private String _IMPRESO;
    private String _CAMPO1;
    private String _CAMPO2;
    private String _CAMPO3;
    private String _CAMPO4;
    private String _COBEX;
    private String _TIPTAR;
    private String _RUTAZONA;
    private String _EMAILENVIADO;
    private String _IMPOPER;
    private String _TIPOENVIOS;
    private int _PIEZAS;
    private int _NUMERO_PIEZA;
    private String _PESO;
    private List<E_DetalleLinea> DETALLE = new LinkedList<>();
    private boolean _SELECCIONADO;
    private String I_MPRIMECOD;
    private String _PRECIOCOD;
    private String _SEABREPAQUETE = "";
    private String _FECHA_INICIAL = "";
    private String _FECHA_FINAL = "";
    private String _TABLA = "";

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
        return _CODREM;
    }

    public void setCODREM(String _CODREM) {
        if (_CODREM != null) {
            this._CODREM = _CODREM.trim();
        }
    }

    public String getNOMREM() {
        return _NOMREM;
    }

    public void setNOMREM(String _NOMREM) {
        if (_NOMREM != null) {
            this._NOMREM = _NOMREM.trim();
        }
    }

    public String getTELREM() {
        return _TELREM;
    }

    public void setTELREM(String _TELREM) {
        if (_TELREM != null) {
            this._TELREM = _TELREM.trim();
        }
    }

    public String getDIRREM() {
        return _DIRREM;
    }

    public void setDIRREM(String _DIRREM) {
        if (_DIRREM != null) {
            if (_DIRREM.length() > 100) {
                this._DIRREM = _DIRREM.substring(0, 100).trim();
                this._COMPLEMENTODIRREM = _DIRREM.substring(101, _DIRREM.length());
            } else {
                this._DIRREM = _DIRREM.trim();
            }
        }
    }

    public String getCODDES() {
        return _CODDES;
    }

    public void setCODDES(String _CODDES) {
        if (_CODDES != null) {
            this._CODDES = _CODDES.trim();
        }
    }

    public String getNOMDES() {
        return _NOMDES;
    }

    public void setNOMDES(String _NOMDES) {
        if (_NOMDES != null) {
            this._NOMDES = _NOMDES.trim();
        }
    }

    public String getTELDES() {
        return _TELDES;
    }

    public void setTELDES(String _TELDES) {
        if (_TELDES != null) {
            this._TELDES = _TELDES.trim();
        }
    }

    public String getDIRDES() {
        return _DIRDES;
    }

    public void setDIRDES(String _DIRDES) {
        if (_DIRDES != null) {
            if (_DIRDES.length() > 100) {
                this._DIRDES = _DIRDES.substring(0, 100).trim();
                this._COMPLEMENTODIRDES = _DIRDES.substring(101, _DIRDES.length());
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
        return _DESCRENV;
    }

    public void setDESCRENV(String _DESCRENV) {
        if (_DESCRENV != null) {
            this._DESCRENV = _DESCRENV.trim();
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
        return _COMPLEMENTODIRREM;
    }

    public void setCOMPLEMENTODIRREM(String _COMPLEMENTODIRREM) {
        if (_COMPLEMENTODIRREM != null) {
            this._COMPLEMENTODIRREM = _COMPLEMENTODIRREM.trim();
        }
    }

    public String getCOMPLEMENTODIRDES() {
        return _COMPLEMENTODIRDES;
    }

    public void setCOMPLEMENTODIRDES(String _COMPLEMENTODIRDES) {
        if (_COMPLEMENTODIRDES != null) {
            this._COMPLEMENTODIRDES = _COMPLEMENTODIRDES.trim();
        }
    }

    public String getCONTACTO() {
        return _CONTACTO;
    }

    public void setCONTACTO(String _CONTACTO) {
        if (_CONTACTO != null) {
            this._CONTACTO = _CONTACTO.trim();
        }
    }

    public String getLLAVECLIENTE() {
        return _LLAVECLIENTE;
    }

    public void setLLAVECLIENTE(String _LLAVECLIENTE) {
        if (_LLAVECLIENTE != null) {
            this._LLAVECLIENTE = _LLAVECLIENTE.trim();
        }
    }

    public String getEMAIL() {
        return _EMAIL;
    }

    public void setEMAIL(String _EMAIL) {
        if (_EMAIL != null) {
            this._EMAIL = _EMAIL.trim();
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

    public List<E_DetalleLinea> getDETALLE() {
        return DETALLE;
    }

    public void setDETALLE(List<E_DetalleLinea> DETALLE) {
        this.DETALLE = DETALLE;
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

    public String getI_MPRIMECOD() {
        return I_MPRIMECOD;
    }

    public void setI_MPRIMECOD(String I_MPRIMECOD) {
        this.I_MPRIMECOD = I_MPRIMECOD;
    }

    public String getSEABREPAQUETE() {
        return _SEABREPAQUETE;
    }

    public void setSEABREPAQUETE(String _SEABREPAQUETE) {
        this._SEABREPAQUETE = _SEABREPAQUETE;
    }
}
