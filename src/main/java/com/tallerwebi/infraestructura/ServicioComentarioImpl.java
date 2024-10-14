package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

    @Autowired
    private RepositorioComentario repositorioComentario;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Override
    public void agregarComentario(Long recetaId, Long usuarioId, String contenido, Integer calificacion) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
        Receta receta = repositorioReceta.buscarRecetaPorId(recetaId);

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setReceta(receta);
        comentario.setContenido(contenido);
        comentario.setCalificacion(calificacion);
        comentario.setFecha(LocalDateTime.now());

        // Guardar el nuevo comentario
        repositorioComentario.guardar(comentario);

        // Recalcular la calificación promedio de la receta
        actualizarCalificacionPromedio(receta);
    }

    @Override
    public void agregarComentario(Long recetaId, Long usuarioId, String contenido) {

    }

    @Override
    public List<Comentario> listarComentariosPorReceta(Long recetaId) {
        return repositorioComentario.buscarPorRecetaId(recetaId);
    }

    @Override
    public List<Comentario> listarComentariosPorUsuario(Long id) {
        return repositorioComentario.buscarUsuarioPorId(id);
    }
    @Override
    public void actualizarCalificacionPromedio(Receta receta) {
        List<Comentario> comentarios = repositorioComentario.obtenerComentariosPorReceta(receta.getId());
        int totalCalificacion = 0;

        for (Comentario c : comentarios) {
            totalCalificacion += c.getCalificacion();
        }

        int promedioCalificacion = comentarios.size() > 0 ? totalCalificacion / comentarios.size() : 0;
        receta.setCalificacion(promedioCalificacion);

        // Guardar la receta con la nueva calificación
        repositorioReceta.save(receta);
    }
}
