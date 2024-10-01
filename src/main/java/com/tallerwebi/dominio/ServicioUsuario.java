package com.tallerwebi.dominio;

public interface ServicioUsuario {

    Usuario obtenerUsuarioPorEmail(String email);
    void modificarContraseniaUsuario(Usuario usuarioActual, String currentPassword, String newPassword, String confirmPassword) throws Exception;
    void modificarDatosPerfil(Usuario usuarioActual, Usuario datosPerfil) throws Exception;
}