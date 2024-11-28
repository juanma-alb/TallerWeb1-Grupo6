package com.tallerwebi.infraestructura;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.*;

@Service
public class ServicioPlan {

    @Autowired
    private RepositorioPlan repositorioPlan;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Transactional
    public Plan obtenerPlanPorId(Long planId) {
        return repositorioPlan.buscarPorId(planId);
    }

    @Transactional
    public void actualizarPlan(Usuario usuario, Plan plan) {
        usuario.setPlan(plan);
        repositorioPlan.actualizarPlanUsuario(usuario);
    }

    public List<Plan> obtenerTodosLosPlanes() {
        return repositorioPlan.obtenerTodosLosPlanes();
    }

    @Transactional
    public void verificarPlanesExpirados() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        for (Usuario usuario : usuarios) {
            Plan plan = usuario.getPlan();
            if (plan != null && plan.getFechaVencimiento().isBefore(LocalDateTime.now())) {
                Plan planBasico = obtenerPlanPorId(1L); 
                usuario.setPlan(planBasico);
                repositorioUsuario.guardar(usuario);
            }
        }
    }
}

