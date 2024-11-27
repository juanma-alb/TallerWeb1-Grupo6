package com.tallerwebi.dominio.excepcion;

public class UsuarioExistente extends Exception {

    public UsuarioExistente(String mensaje) {
        super(mensaje);
    }
}

