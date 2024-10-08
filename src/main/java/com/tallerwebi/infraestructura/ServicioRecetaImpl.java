package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    private static final String RUTA_IMAGENES = "src/main/webapp/resources/core/imagenes";

    public void crearReceta(Receta receta) {
        repositorioReceta.save(receta);
    }



//    @Override
//    public String guardarFoto(MultipartFile archivo) {
//        if (archivo.isEmpty()) {
//            throw new RuntimeException("El archivo está vacío");
//        }
//
//        try {
//            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
//            Path rutaDestino = Paths.get(RUTA_IMAGENES).resolve(nombreArchivo);
//            Files.createDirectories(rutaDestino.getParent());
//            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
//            return nombreArchivo;
//        } catch (IOException e) {
//            throw new RuntimeException("Error al guardar la foto: " + e.getMessage(), e);
//        }
//    }

    @Override
    public List<Receta> listarRecetasPorUsuario(Long usuarioId) {
        return repositorioReceta.findByUsuarioId(usuarioId); // Obtiene las recetas del usuario
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



}

