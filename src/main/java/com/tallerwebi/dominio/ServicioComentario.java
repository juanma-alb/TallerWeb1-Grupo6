package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioComentario {
    void agregarComentario(Long recetaId, Long usuarioId, String contenido, Integer calificacion);

    void agregarComentario(Long recetaId, Long usuarioId, String contenido);

    List<Comentario> listarComentariosPorReceta(Long recetaId);

    List<Comentario> listarComentariosPorUsuario(Long id);

    void actualizarCalificacionPromedio(Receta receta);
}
