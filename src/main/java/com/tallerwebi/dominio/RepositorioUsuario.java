package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarPorId(Long usuarioId);
    void eliminar(Usuario usuario);

    List<Receta> obtenerRecetasGuardadas(Long usuarioId);


    ;
}

