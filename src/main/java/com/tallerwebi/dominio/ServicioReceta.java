package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioReceta {
    void crearReceta(Receta receta);

    List<Receta> listarRecetasPorUsuario(Long usuarioId);
}
