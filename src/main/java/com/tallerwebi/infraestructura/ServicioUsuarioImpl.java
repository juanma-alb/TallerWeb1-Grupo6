package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {


    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return repositorioUsuario.buscar(email);
    }

    @Override
    public void modificarContraseniaUsuario(Usuario usuarioActual, String currentPassword, String newPassword, String confirmPassword) throws Exception {
        if (currentPassword == null || !usuarioActual.getPassword().equals(currentPassword)) {
            throw new Exception("La contraseña actual no es válida.");
        }

        if (newPassword == null || newPassword.isEmpty()) {
            throw new Exception("Debe ingresar una nueva contraseña.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("Las nuevas contraseñas no coinciden.");
        }

        if (newPassword.equals(usuarioActual.getPassword())) {
            throw new Exception("La nueva contraseña no puede ser la misma que la contraseña actual.");
        }

        if (newPassword.length() < 5) {
            throw new Exception("La nueva contraseña debe tener al menos 5 caracteres.");
        }

        if (!newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
            throw new Exception("La nueva contraseña debe contener al menos una letra y un número.");
        }

        usuarioActual.setPassword(newPassword);
        repositorioUsuario.modificar(usuarioActual);
    }


    @Override
    public void modificarDatosPerfil(Usuario usuarioActual, Usuario datosPerfil) throws Exception {
        if (datosPerfil.getNombre() != null && !datosPerfil.getNombre().isEmpty()) {
            usuarioActual.setNombre(datosPerfil.getNombre());
        }

        if (datosPerfil.getDescripcion() != null && !datosPerfil.getDescripcion().isEmpty()) {
            usuarioActual.setDescripcion(datosPerfil.getDescripcion());
        }

        if (datosPerfil.getCiudad() != null && !datosPerfil.getCiudad().isEmpty()) {
            usuarioActual.setCiudad(datosPerfil.getCiudad());
        }

        repositorioUsuario.modificar(usuarioActual);
    }

}