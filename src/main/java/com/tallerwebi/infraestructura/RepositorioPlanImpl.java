package com.tallerwebi.infraestructura;

import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RepositorioPlanImpl implements RepositorioPlan {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Plan buscarPorId(Long id) {
        return entityManager.find(Plan.class, id);
    }

    @Override
    public List<Plan> obtenerTodosLosPlanes() {
        return entityManager.createQuery("FROM Plan", Plan.class).getResultList();
    }

    @Override
    public void actualizarPlanUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }
}
