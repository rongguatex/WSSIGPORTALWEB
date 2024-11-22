/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.entidades;

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
@XmlRootElement(name = "RESPUESTA")
@XmlAccessorType(XmlAccessType.FIELD)
public class ETomaServicio {
    
    @XmlElement(name = "SERVICIO")
    private EServicio SERVICIO;
    
    @XmlElement(name = "ERROR")
    private Error ERROR;

    public EServicio getSERVICIO() {
        return SERVICIO;
    }

    public void setSERVICIO(EServicio SERVICIO) {
        this.SERVICIO = SERVICIO;
    }

    public Error getERROR() {
        return ERROR;
    }

    public void setERROR(Error ERROR) {
        this.ERROR = ERROR;
    }

    @Override
    public String toString() {
        return "ETomaServicio{" + "SERVICIO=" + SERVICIO + ", ERROR=" + ERROR + '}';
    }

//    @XmlRootElement(name = "SERVICIO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class EServicio {

        @XmlElement(name = "CODIGO")
        private String CODIGO;
        
        @XmlElement(name = "URLGUIAS")
        private String URLGUIAS;

        @XmlElementWrapper(name = "GUIAS")
        @XmlElement(name = "CORRELATIVOGUIA1")
        private List<ErrorGuia> CORRELATIVOERROR = new LinkedList<>();
        
        public String getCODIGO() {
            return CODIGO;
        }

        public void setCODIGO(String CODIGO) {
            this.CODIGO = CODIGO;
        }

        public String getURLGUIAS() {
            return URLGUIAS;
        }

        public void setURLGUIAS(String URLGUIAS) {
            this.URLGUIAS = URLGUIAS;
        }

        public List<ErrorGuia> getCORRELATIVOERROR() {
            return CORRELATIVOERROR;
        }

        public void setCORRELATIVOERROR(List<ErrorGuia> CORRELATIVOERROR) {
            this.CORRELATIVOERROR = CORRELATIVOERROR;
        }

        @Override
        public String toString() {
            return "EServicio{" + "CODIGO=" + CODIGO + ", URLGUIAS=" + URLGUIAS + ", CORRELATIVOERROR=" + CORRELATIVOERROR + '}';
        }
        
    }

//    @XmlRootElement(name = "CORRELATIVOGUIA1")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ErrorGuia {

        @XmlElement(name = "CODIGO")
        private String CODIGO;

        @XmlElement(name = "DESCRIPCION")
        private String DESCRIPCION;

        public String getCODIGO() {
            return CODIGO;
        }

        public void setCODIGO(String CODIGO) {
            this.CODIGO = CODIGO;
        }

        public String getDESCRIPCION() {
            return DESCRIPCION;
        }

        public void setDESCRIPCION(String DESCRIPCION) {
            this.DESCRIPCION = DESCRIPCION;
        }

        @Override
        public String toString() {
            return "EGuia{" + "CODIGO=" + CODIGO + ", DESCRIPCION=" + DESCRIPCION + '}';
        }

    }
    
//    @XmlRootElement(name = "ERROR")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Error {
        @XmlElement(name = "CODIGO")
        private String CODIGO;

        @XmlElement(name = "DESCRIPCION")
        private String DESCRIPCION;

        public String getCODIGO() {
            return CODIGO;
        }

        public void setCODIGO(String CODIGO) {
            this.CODIGO = CODIGO;
        }

        public String getDESCRIPCION() {
            return DESCRIPCION;
        }

        public void setDESCRIPCION(String DESCRIPCION) {
            this.DESCRIPCION = DESCRIPCION;
        }

        @Override
        public String toString() {
            return "Error{" + "CODIGO=" + CODIGO + ", DESCRIPCION=" + DESCRIPCION + '}';
        }
        
    }
}
