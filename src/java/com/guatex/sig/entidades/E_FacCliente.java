/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

/**
 *
 * @author RGALICIA
 */
public class E_FacCliente {

    private String _CODIGO;
    private String _NOMBRE;
    private String _CLIENTE;
    private String _TELEFONO;
    private String _CORREO;
    private String _MUNICIPIO;
    private String _LOCALIDAD;
    private String _NIT;
    private String _PUNTO;
    private String _PADRE;
    private String _DIRECCION;
    private String _LXCOBRAR;
    private String _LCREDITO;
    private String _LCONTADO;
    private String _LCOD;
    private String _LPREPAGO;
    private String _SEABREPAQUETE;
    private String _TARIFANORMAL;
    private String _TARIFAEXTRA;
    private String _TARIFAUNICA;
    private String _TARIFAESPECIAL;
    private String _UNIFICACLI;
    private String _IMPRIMEGUIAS;
    private String _MUESTRASEGURO;
    private String _MUESTRARECOOFI;

    public String getTARIFANORMAL() {
        return _TARIFANORMAL;
    }

    public void setTARIFANORMAL(String _TARIFANORMAL) {
        this._TARIFANORMAL = _TARIFANORMAL;
    }

    public String getTARIFAEXTRA() {
        return _TARIFAEXTRA;
    }

    public void setTARIFAEXTRA(String _TARIFAEXTRA) {
        this._TARIFAEXTRA = _TARIFAEXTRA;
    }

    public String getTARIFAUNICA() {
        return _TARIFAUNICA;
    }

    public void setTARIFAUNICA(String _TARIFAUNICA) {
        this._TARIFAUNICA = _TARIFAUNICA;
    }

    public String getTARIFAESPECIAL() {
        return _TARIFAESPECIAL;
    }

    public void setTARIFAESPECIAL(String _TARIFAESPECIAL) {
        this._TARIFAESPECIAL = _TARIFAESPECIAL;
    }

    public String getUNIFICACLI() {
        return _UNIFICACLI;
    }

    public void setUNIFICACLI(String _UNIFICACLI) {
        this._UNIFICACLI = _UNIFICACLI;
    }

    public String getIMPRIMEGUIAS() {
        return _IMPRIMEGUIAS;
    }

    public void setIMPRIMEGUIAS(String _IMPRIMEGUIAS) {
        this._IMPRIMEGUIAS = _IMPRIMEGUIAS;
    }

    public String getMUESTRASEGURO() {
        return _MUESTRASEGURO;
    }

    public void setMUESTRASEGURO(String _MUESTRASEGURO) {
        this._MUESTRASEGURO = _MUESTRASEGURO;
    }

    public String getMUESTRARECOOFI() {
        return _MUESTRARECOOFI;
    }

    public void setMUESTRARECOOFI(String _MUESTRARECOOFI) {
        this._MUESTRARECOOFI = _MUESTRARECOOFI;
    }

    public String getLCOD() {
        return _LCOD;
    }

    public void setLCOD(String _LCOD) {
        this._LCOD = _LCOD;
    }

    public String getLPREPAGO() {
        return _LPREPAGO;
    }

    public void setLPREPAGO(String _LPREPAGO) {
        this._LPREPAGO = _LPREPAGO;
    }

    public String getSEABREPAQUETE() {
        return _SEABREPAQUETE;
    }

    public void setSEABREPAQUETE(String _SEABREPAQUETE) {
        this._SEABREPAQUETE = _SEABREPAQUETE;
    }

    public String getCODIGO() {
        return _CODIGO;
    }

    public void setCODIGO(String _CODIGO) {
        if (_CODIGO != null) {
            this._CODIGO = _CODIGO.trim();
        } else {
            this._CODIGO = _CODIGO;
        }
    }

    public String getNOMBRE() {
        return _NOMBRE;
    }

    public void setNOMBRE(String _NOMBRE) {
        if (_NOMBRE != null) {
            this._NOMBRE = _NOMBRE.trim();
        } else {
            this._NOMBRE = _NOMBRE;
        }
    }

    public String getCLIENTE() {
        return _CLIENTE;
    }

    public void setCLIENTE(String _CLIENTE) {
        if (_CLIENTE != null) {
            this._CLIENTE = _CLIENTE.trim();
        } else {
            this._CLIENTE = _CLIENTE;
        }

    }

    public String getTELEFONO() {
        return _TELEFONO;
    }

    public void setTELEFONO(String _TELEFONO) {
        if (_TELEFONO != null) {
            this._TELEFONO = _TELEFONO.trim();
        } else {
            this._TELEFONO = _TELEFONO;
        }
    }

    public String getCORREO() {
        return _CORREO;
    }

    public void setCORREO(String _CORREO) {
        if (_CORREO != null) {
            this._CORREO = _CORREO.trim();
        } else {
            this._CORREO = _CORREO;
        }
    }

    public String getMUNICIPIO() {
        return _MUNICIPIO;
    }

    public void setMUNICIPIO(String _MUNICIPIO) {
        if (_MUNICIPIO != null) {
            this._MUNICIPIO = _MUNICIPIO.trim();
        } else {
            this._MUNICIPIO = _MUNICIPIO;
        }
    }

    public String getNIT() {
        return _NIT;
    }

    public void setNIT(String _NIT) {
        if (_NIT != null) {
            this._NIT = _NIT.trim();
        } else {
            this._NIT = _NIT;
        }
    }

    public String getPUNTO() {
        return _PUNTO;
    }

    public void setPUNTO(String _PUNTO) {
        if (_PUNTO != null) {
            this._PUNTO = _PUNTO.trim();
        } else {
            this._PUNTO = _PUNTO;
        }
    }

    public String getPADRE() {
        return _PADRE;
    }

    public void setPADRE(String _PADRE) {
        if (_PADRE != null) {
            this._PADRE = _PADRE.trim();
        } else {
            this._PADRE = _PADRE;
        }
    }

    public String getLOCALIDAD() {
        return _LOCALIDAD;
    }

    public void setLOCALIDAD(String _LOCALIDAD) {
        if (_LOCALIDAD != null) {
            this._LOCALIDAD = _LOCALIDAD.trim();
        } else {
            this._LOCALIDAD = _LOCALIDAD;
        }
    }

    public String getDIRECCION() {
        return _DIRECCION;
    }

    public void setDIRECCION(String _DIRECCION) {
        if (_DIRECCION != null) {
            this._DIRECCION = _DIRECCION.trim();
        } else {
            this._DIRECCION = _DIRECCION;
        }
    }

    public void setLXCOBRAR(String _LXCOBRAR) {
        if (_LXCOBRAR != null) {
            this._LXCOBRAR = _LXCOBRAR.trim();
        }
    }

    public String getLXCOBRAR() {
        return _LXCOBRAR;
    }

    public String getLCREDITO() {
        return _LCREDITO;
    }

    public void setLCREDITO(String _LCREDITO) {
        if (_LCREDITO != null) {
            this._LCREDITO = _LCREDITO.trim();
        }
    }

    public String getLCONTADO() {
        return _LCONTADO;
    }

    public void setLCONTADO(String _LCONTADO) {
        if (_LCONTADO != null) {
            this._LCONTADO = _LCONTADO.trim();
        }
    }

    @Override
    public String toString() {
        return "E_FacCliente{" + "_CODIGO=" + _CODIGO + ", _NOMBRE=" + _NOMBRE + ",  \n"
                + "_CLIENTE=" + _CLIENTE + ", _TELEFONO=" + _TELEFONO + ", _CORREO=" + _CORREO + ", \n "
                + "_MUNICIPIO=" + _MUNICIPIO + ", _LOCALIDAD=" + _LOCALIDAD + ", _NIT=" + _NIT + ", \n "
                + "_PUNTO=" + _PUNTO + ", _PADRE=" + _PADRE + ", _DIRECCION=" + _DIRECCION + ", \n "
                + "_LXCOBRAR=" + _LXCOBRAR + ", _LCREDITO=" + _LCREDITO + ", _LCONTADO=" + _LCONTADO + ", \n "
                + "_LCOD=" + _LCOD + ", _LPREPAGO=" + _LPREPAGO + ", _SEABREPAQUETE=" + _SEABREPAQUETE + ", \n "
                + "_TARIFANORMAL=" + _TARIFANORMAL + ", _TARIFAEXTRA=" + _TARIFAEXTRA + ", \n "
                + "_TARIFAUNICA=" + _TARIFAUNICA + ", _TARIFAESPECIAL=" + _TARIFAESPECIAL + ", \n "
                + "_UNIFICACLI=" + _UNIFICACLI + ", _IMPRIMEGUIAS=" + _IMPRIMEGUIAS + ", \n "
                + "_MUESTRASEGURO=" + _MUESTRASEGURO + ", _MUESTRARECOOFI=" + _MUESTRARECOOFI + '}';
    }

}
