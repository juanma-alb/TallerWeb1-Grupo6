package com.tallerwebi.dominio;

import java.util.List;
//prueba para obtener las recetas
public interface RepositorioDescubreRecetas {
    void save2(Receta receta);
    List<Receta> listarRecetas();  
    List<Receta> filtrarPorCategoria(String categoria, String subcategoria);
}

