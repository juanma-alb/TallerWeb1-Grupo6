package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.*;

@Service
public class ServicioPlan {

    @Autowired
    private RepositorioPlan repositorioPlan;

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
}

