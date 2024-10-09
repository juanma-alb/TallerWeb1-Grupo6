package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ServicioHome;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioHomeImpl implements ServicioHome {

    @Autowired
    private RepositorioHome repositorioHome;

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
    
    public List<Receta> buscarRecetas(String query) {
        return repositorioHome.buscarPorCriterio(query);
    }

}
