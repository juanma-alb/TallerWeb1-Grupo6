package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ServicioReceta {
    void crearReceta(Receta receta);
    //void guardarFoto(Receta receta, MultipartFile archivo);
}
