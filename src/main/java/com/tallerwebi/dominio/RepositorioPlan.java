package com.tallerwebi.dominio;
import com.tallerwebi.dominio.*;
import java.util.List;
import java.util.Optional;

public interface RepositorioPlan {

    List<Plan> obtenerTodosLosPlanes();
    Plan buscarPorId(Long id);
    void actualizarPlanUsuario(Usuario usuario);
}

