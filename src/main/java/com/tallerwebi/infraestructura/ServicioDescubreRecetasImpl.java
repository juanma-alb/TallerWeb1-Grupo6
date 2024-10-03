package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioDescubreRecetas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDescubreRecetasImpl implements ServicioDescubreRecetas {

    @Autowired
    private RepositorioDescubreRecetas repositorioDescubreRecetas;

    @Override
    public void crearReceta2(Receta receta) {
        repositorioDescubreRecetas.save2(receta); 
    }

    @Override
    public List<Receta> listarRecetas() {
        return repositorioDescubreRecetas.listarRecetas(); 
    }

    @Override
    public List<Receta> obtenerRecetasParaCarrusel() {
        return repositorioDescubreRecetas.listarRecetas();  
    }

    @Override
    public List<Receta> filtrarPorCategoria(String categoria, String subcategoria) {
        return repositorioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria);
    }
}
