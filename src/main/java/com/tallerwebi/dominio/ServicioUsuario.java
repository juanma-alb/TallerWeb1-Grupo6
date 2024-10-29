package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioUsuario {

    Usuario obtenerUsuarioPorEmail(String email);
    void modificarContraseniaUsuario(Usuario usuarioActual, String currentPassword, String newPassword, String confirmPassword) throws Exception;
    void modificarDatosPerfil(Usuario usuarioActual, Usuario datosPerfil) throws Exception;
    void eliminar(Long id);

    List<Receta> obtenerRecetasGuardadas(Long usuarioId);


}