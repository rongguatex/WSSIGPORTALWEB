/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.sig.utils;

import com.guatex.sig.entidades.E_Credenciales;
import com.guatex.sig.entidades.RespuestaGeneral;

/**
 *
 * @author RGALICIA
 */
public class ValidacionCredenciales {

    public RespuestaGeneral validar(E_Credenciales cadenaxml) {
        
        

//		if (credencial.getRespuesta().getCodigo().equals("S")) {
//			Respuesta respuesta = new Respuesta();
//			ValidaCredenciales vali = new ValidaCredenciales();
//			respuesta = vali.validar(credencial.getCodigous() + "/" + credencial.getUsuario(), credencial.getPassword());
//			System.out.println("res login " + respuesta);
//			String codigo = "";
//			String descripcion = "";
//			String DESCRIPCION_ERROR = "El usuario o contraseña son inválidos";
//			String CODIGO_OK = "S";
//			String DESCRIPCION_OK = "Usuario y contraseña validos";
//			try {
//				if (respuesta.getCodigo().trim().length() > 1) {
//					codigo = respuesta.getCodigo().substring(0, 1);
//
//					System.out.println("Codigo de repuesta: " + codigo);
//					if (codigo.equals(CODIGO_OK)) {
//						descripcion = DESCRIPCION_OK;
//						return "CREDENCIALES_VALIDAS";
//					} else {
//						codigo = "N";
//						descripcion = DESCRIPCION_ERROR;
//						result = gen.genValidarCredenciales(codigo, descripcion);
//					}
//				} else {
//					codigo = "N";
//					descripcion = DESCRIPCION_ERROR;
//					result = gen.genValidarCredenciales(codigo, descripcion);
//				}
//			} catch (Exception e) {
//				codigo = "N";
//				descripcion = DESCRIPCION_ERROR;
//				result = gen.genValidarCredenciales(codigo, descripcion);
//			}
//
//			if (!codigo.equals("S")) {
//				result = gen.genValidarCredenciales(codigo, descripcion);
//			}
//
//			return result.toUpperCase();
//		} else {
//			credencial.getRespuesta().setCodigo("N");
//			credencial.getRespuesta().setDescripcion("El formato del xml de entrada es inválido");
//
//		}
//
//		result = gen.genValidarCredenciales(credencial.getRespuesta().getCodigo(), credencial.getRespuesta().getDescripcion());
//
//		return result.toUpperCase();
        return new RespuestaGeneral("0000", "Validación exitosa");
    }
    
}
