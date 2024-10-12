package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReceta {
    void save(Receta receta);
    List<Receta> findByUsuarioId(Long usuarioId);
    List<Receta> listarTodasLasRecetas();
    List<Receta> buscarRecetasPorNombreRecetas(String filtro);
    Receta buscarRecetaPorId(Long id);
    void actualizar(Receta receta); // Nuevo método para actualizar recetas
    void eliminar(Long id); // Nuevo método para eliminar recetas
}
