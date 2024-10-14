package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioHomeImpl implements ServicioHome {

    @Autowired
    private RepositorioHome repositorioHome;

    @Autowired
    private RepositorioComentario repositorioComentario;

    @Override
    public void crearReceta2(Receta receta) {
        repositorioHome.save2(receta);
    }

    @Override
    public List<Receta> listarRecetas() {
        return repositorioHome.listarRecetas();  
    }

    @Override
    public List<Receta> obtenerRecetasParaCarrusel() {
        return repositorioHome.listarRecetas();  
    }
    @Override
    public List<Receta> buscarRecetas(String query) {
        return repositorioHome.buscarPorCriterio(query);
    }

    @Override
    public List<Receta> obtenerRecetasOrdenadasPorCalificacion() {
        List<Receta> recetas = repositorioHome.listarRecetas();
        for (Receta receta : recetas) {
            List<Comentario> comentarios = repositorioHome.obtenerComentariosPorReceta(receta.getId());
            if (!comentarios.isEmpty()) {
                int totalCalificacion = 0;
                for (Comentario comentario : comentarios) {
                    totalCalificacion += comentario.getCalificacion();
                }
                int promedioCalificacion = totalCalificacion / comentarios.size();
                receta.setCalificacion(promedioCalificacion); // Asigna la calificación promedio a la receta
            } else {
                receta.setCalificacion(0); // Si no hay comentarios, la calificación es 0
            }
        }
        // Ordena las recetas por calificación de mayor a menor
        recetas.sort((r1, r2) -> Integer.compare(r2.getCalificacion(), r1.getCalificacion()));
        return recetas.stream().limit(10).collect(Collectors.toList()); // Devuelve solo las top 10
    }

    }