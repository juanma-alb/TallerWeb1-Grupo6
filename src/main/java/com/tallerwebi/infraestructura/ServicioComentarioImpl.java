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
    public void agregarComentario(Long recetaId, Long usuarioId, String contenido) {
        // Recupera el usuario de la base de datos
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);

        // Recupera la receta de la base de datos (si es necesario)
        Receta receta = repositorioReceta.buscarRecetaPorId(recetaId);

        // Crea el comentario
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setReceta(receta);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDateTime.now());

        // Guarda el comentario en el repositorio
        repositorioComentario.guardar(comentario);
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
