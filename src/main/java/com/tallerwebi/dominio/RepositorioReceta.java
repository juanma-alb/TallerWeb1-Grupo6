package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReceta {

    void save(Receta receta);

    List<Receta> findByUsuarioId(Long usuarioId);

    List<Receta> listarTodasLasRecetas();

    List<Receta> buscarRecetasPorNombreRecetas(String filtro);

    Receta buscarRecetaPorId(Long id);


}
