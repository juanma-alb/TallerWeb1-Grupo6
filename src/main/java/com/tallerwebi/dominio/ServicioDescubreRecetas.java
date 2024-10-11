package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioDescubreRecetas {
    void crearReceta2(Receta receta);
    List<Receta> listarRecetas();
    List<Receta> obtenerRecetasParaCarrusel();
    List<Receta> filtrarPorCategoria(String categoria, String subcategoria);  
    Receta obtenerRecetaPorId(Long id);
    void eliminarReceta(Long id);
    void actualizarReceta(Receta receta);
}
