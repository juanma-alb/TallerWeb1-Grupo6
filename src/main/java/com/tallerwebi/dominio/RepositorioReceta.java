package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReceta {

    void save(Receta receta);

    List<Receta> findByUsuarioId(Long usuarioId);
}
