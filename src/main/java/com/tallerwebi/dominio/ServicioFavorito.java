package com.tallerwebi.dominio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioFavorito {

    @Autowired
    private RepositorioFavorito repositorioFavorito;

    public void agregarAFavoritos(Usuario usuario, Receta receta) {
        if (!repositorioFavorito.existePorUsuarioIdYRecetaId(usuario.getId(), receta.getId())) {
            Favorito favorito = new Favorito(usuario, receta);
            repositorioFavorito.guardar(favorito);
        }
    }

    public List<Receta> obtenerRecetasFavoritas(Long usuarioId) {
        List<Favorito> favorito = repositorioFavorito.buscarPorUsuario(usuarioId);
        return favorito.stream().map(Favorito::getReceta).collect(Collectors.toList());
    }

    public void eliminarDeFavoritos(Long usuarioId, Long recetaId) {
        repositorioFavorito.eliminarPorUsuarioIdYRecetaId(usuarioId, recetaId);
    }

    public boolean esFavorito(Long usuarioId, Long recetaId) {
        return repositorioFavorito.existePorUsuarioIdYRecetaId(usuarioId, recetaId);
    }
}
