package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioHome {
    void crearReceta2(Receta receta);
    List<Receta> listarRecetas();
    List<Receta> obtenerRecetasParaCarrusel();
    List<Receta> buscarRecetas(String query);    
}