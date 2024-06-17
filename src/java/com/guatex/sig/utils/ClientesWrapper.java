/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.utils;

import com.guatex.sig.entidades.EReporteClienteXML;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PJUNIOR-3
 */
@XmlRootElement(name = "LISTADOCLIENTES")
public class ClientesWrapper {
     private List<EReporteClienteXML> clientes;

    @XmlElement(name = "CLIENTE")
    public List<EReporteClienteXML> getClientes() {
        return clientes;
    }

    public void setClientes(List<EReporteClienteXML> clientes) {
        this.clientes = clientes;
    }
}
