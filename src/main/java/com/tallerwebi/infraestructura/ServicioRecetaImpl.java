package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    private static final String RUTA_IMAGENES = "src/main/webapp/resources/core/Imagenes/imagenReceta";

    public void crearReceta(Receta receta) {
        repositorioReceta.save(receta);
    }

    /*
    @Override
    public String guardarFoto(MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            throw new RuntimeException("el archivo esta vacio");
        }

        try{
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename(); //nombre unico
            Path rutaDestino = Paths.get(RUTA_IMAGENES).resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            return nombreArchivo;
        }catch (Exception e){
            throw new RuntimeException("error al guardar la foto", e);
        }
    }
     */

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


}

