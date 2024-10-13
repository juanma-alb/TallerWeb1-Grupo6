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

        repositorioComentario.guardar(comentario);
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
}
