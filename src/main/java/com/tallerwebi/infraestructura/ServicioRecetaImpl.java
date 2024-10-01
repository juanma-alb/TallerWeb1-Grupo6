package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    public void crearReceta(Receta receta) {
        repositorioReceta.save(receta);
    }



/*
    @Override
    public void guardarFoto(Receta receta, MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            String rutaDestino = "src/main/webapp/resources/core/Imagenes/imagenReceta";
            File directorio = new File(rutaDestino);

            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            File archivoDestino = new File(directorio, nombreArchivo);

            try {
                archivo.transferTo(archivoDestino);
                receta.setFoto(nombreArchivo);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la foto", e);
            }
        }
    }

*/


}
