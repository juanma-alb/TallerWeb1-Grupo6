package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReceta {
    void save(Receta receta);

    List<Receta> findByUsuarioIdAndNoPredefinidas(Long usuarioId);

    List<Receta> listarTodasLasRecetas();

    List<Receta> buscarRecetasPorNombreRecetas(String filtro);

    Receta buscarRecetaPorId(Long id);

    void actualizar(Receta receta);

    void eliminar(Long id);

    public List<Receta> findByUsuarioId(Long usuarioId);


    List<Receta> listarRecetasGuardadasPorUsuario(Long usuarioId);

    int contarRecetasGuardadasPorUsuarioYTipo(Long usuarioId, String tipoComida);

    String encontrarTipoComidaFavorito(Long usuarioId);

    List<Receta> encontrarRecetasPorTipo(String tipoComida);
}
