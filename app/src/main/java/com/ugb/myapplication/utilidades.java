package com.ugb.myapplication;

import java.util.Base64;

public class utilidades {
    static String urlConsulta = "http://192.168.83.59:5984/tienda/_design/tienda/_view/new";
    static String urlMto = "http://192.168.83.59:5984/tienda";
    static String user = "amdin";
    static String passwd = "12345678";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}