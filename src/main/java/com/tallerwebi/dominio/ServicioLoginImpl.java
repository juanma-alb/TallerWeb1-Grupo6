package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.ValidacionesIncorrectas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario, DatosRegistro datosRegistro) throws UsuarioExistente,ValidacionesIncorrectas {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente("ya existe ese usuario");
        }

        if (!validarContraseña(datosRegistro.getPassword())) {
            throw new ValidacionesIncorrectas("La contraseña no cumple con los requisitos.");
        }

        if (!validarNombreSoloLetras(datosRegistro.getNombre())) {
            throw new ValidacionesIncorrectas("El nombre solo puede contener letras.");
        }

        repositorioUsuario.guardar(usuario);



    }

    public boolean validarContraseña(String contraseña) {
        if (contraseña == null || contraseña.length() <= 5) {
            return false;
        }

        boolean tieneLetra = false;
        boolean tieneNumero = false;


        for (char c : contraseña.toCharArray()) {
            if (Character.isLetter(c)) {
                tieneLetra = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }

        return tieneLetra && tieneNumero;
    }

    private boolean validarNombreSoloLetras(String nombre) {
        if(nombre != null && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
            return true;
        }
        return false;
    }
}

