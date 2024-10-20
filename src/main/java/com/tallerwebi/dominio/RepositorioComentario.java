package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioComentario {
    void guardar(Comentario comentario);

    List<Comentario> buscarPorRecetaId(Long recetaId);
    List<Comentario> buscarUsuarioPorId(Long usuarioId);
    void eliminar(Comentario comentario);

    List<Comentario> obtenerComentariosPorReceta(Long id);
}
