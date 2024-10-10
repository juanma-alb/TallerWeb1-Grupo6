package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioReceta {
    void crearReceta(Receta receta);
    /*String guardarFoto(MultipartFile archivo);*/

    List<Receta> listarRecetasPorUsuario(Long usuarioId);

    List<Receta> listarTodasLasRecetas(long l);
    List<Receta> buscarRecetasPorNombreRecetas(String filtro);
    Receta buscarRecetaPorId(Long recetaId);



//    String guardarFoto(MultipartFile archivoFoto);
}
