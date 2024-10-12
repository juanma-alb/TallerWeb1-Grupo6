package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
/*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
*/
import java.util.List; 


@Service
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Override
    public void crearReceta(Receta receta) {
        repositorioReceta.save(receta);
    }

    @Override
    public List<Receta> listarRecetasPorUsuario(Long usuarioId) {
        return repositorioReceta.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Receta> listarTodasLasRecetas(long l) {
        return repositorioReceta.listarTodasLasRecetas();
    }

    @Override
    public List<Receta> buscarRecetasPorNombreRecetas(String filtro) {
        return repositorioReceta.buscarRecetasPorNombreRecetas(filtro);
    }

    @Override
    public Receta buscarRecetaPorId(Long recetaId) {
        return repositorioReceta.buscarRecetaPorId(recetaId);
    }

    @Override
    public void actualizarReceta(Receta receta) {
        repositorioReceta.actualizar(receta); 
    }

    @Override
    public void eliminarReceta(Long id) {
        repositorioReceta.eliminar(id); 
    }
}
