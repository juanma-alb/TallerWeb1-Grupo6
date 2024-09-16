package com.tallerwebi.dominio;

public interface ServicioUsuario {

    Usuario obtenerUsuarioPorEmail(String email);
    void modificarUsuario(Usuario usuario);
    boolean validarContraseñaActual(String email, String password);
}
