package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.ValidacionesIncorrectas;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario,DatosRegistro datosRegistro) throws UsuarioExistente, ValidacionesIncorrectas;

}
