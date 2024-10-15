package com.tallerwebi.dominio;
import java.util.List;

public interface RepositorioDescubreRecetas {
    void save2(Receta receta);
    
    List<Receta> filtrarPorCategoria(String categoria, String subcategoria);

    Receta obtenerRecetaPorId(Long id);
    void eliminarReceta(Long id);
    void actualizar(Receta receta);
    List<Receta> listarRecetasPredefinidas();  
}


