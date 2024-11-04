package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioReceta {
    void crearReceta(Receta receta);
    List<Receta> listarRecetasPorUsuario(Long usuarioId);
    List<Receta> listarTodasLasRecetas(long l);
    List<Receta> buscarRecetasPorNombreRecetas(String filtro);
    Receta buscarRecetaPorId(Long recetaId);
    void actualizarReceta(Receta receta); 
    void eliminarReceta(Long id);

    List<Receta> listarRecetasGuardadasPorUsuario(Long id);


    void guardarReceta(Long recetaId, Long usuarioId);


    int contarRecetasGuardadasPorTipo(Long usuarioId, String tipoComida);

    String calcularNivelInteres(int cantidad);

    int calcularPorcentaje(int cantidad);

    void eliminarRecetaGuardada(Long id, Long id1);

    String obtenerTipoComidaFavorito(Long usuarioId);

    List<Receta> recomendarRecetasPorTipo(String tipoComida);
}
