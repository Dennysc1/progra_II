package com.ugb.myapplication;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://192.168.1.8:5984/fernando/_design/fernando/_view/dennys";
    static String url_mto = "http://192.168.1.8:5984/fernando";
    static String user = "amdin";
    static String passwd = "12345678";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}