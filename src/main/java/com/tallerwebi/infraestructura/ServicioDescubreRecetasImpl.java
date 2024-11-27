package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioDescubreRecetas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDescubreRecetasImpl implements ServicioDescubreRecetas {

    private RepositorioDescubreRecetas repositorioDescubreRecetas;

    @Autowired
    public ServicioDescubreRecetasImpl(RepositorioDescubreRecetas repositorioDescubreRecetas) {
        this.repositorioDescubreRecetas = repositorioDescubreRecetas;
    }


    @Override
    public void crearReceta2(Receta receta) {
        repositorioDescubreRecetas.save2(receta); 
    }

    @Override
    public List<Receta> listarRecetas() {
    return repositorioDescubreRecetas.listarRecetasPredefinidas();  
    }


    @Override
    public List<Receta> obtenerRecetasParaCarrusel() {
        return repositorioDescubreRecetas.listarRecetasPredefinidas();  
    }

    @Override
    public List<Receta> filtrarPorCategoria(String categoria, String subcategoria) {
        return repositorioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria);
    }

    @Override
    public Receta obtenerRecetaPorId(Long id) {
        return repositorioDescubreRecetas.obtenerRecetaPorId(id);
    }

    @Override
    public void eliminarReceta(Long id) {
        repositorioDescubreRecetas.eliminarReceta(id);
    }

    @Override
    public void actualizarReceta(Receta receta) {
        
        repositorioDescubreRecetas.actualizar(receta); 
    }

}
