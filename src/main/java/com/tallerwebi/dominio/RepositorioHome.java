package com.tallerwebi.dominio;

import java.util.List;
//prueba para obtener las recetas
public interface RepositorioHome {
    void save2(Receta receta);
    List<Receta> listarRecetas();  
    
}