package com.tallerwebi.dominio;

import java.util.Optional;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    void borrarTodo();

    Usuario buscarPorId(Long usuarioId);
}

