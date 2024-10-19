package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFavorito {

    void guardar(Favorito favorito);

    List<Favorito> buscarPorUsuario(Long usuarioId);

    boolean existePorUsuarioIdYRecetaId(Long usuarioId, Long recetaId);

    void eliminarPorUsuarioIdYRecetaId(Long usuarioId, Long recetaId);
}

