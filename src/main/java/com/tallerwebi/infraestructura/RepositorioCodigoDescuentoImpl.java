package com.tallerwebi.infraestructura;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.CodigoDescuento;
import com.tallerwebi.dominio.RepositorioCodigoDescuento;

@Repository
public class RepositorioCodigoDescuentoImpl implements RepositorioCodigoDescuento {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CodigoDescuento guardar(CodigoDescuento codigoDescuento) {
        entityManager.persist(codigoDescuento);
        return codigoDescuento;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CodigoDescuento> buscarPorCodigoYNoUsado(String codigo) {
        String jpql = "SELECT c FROM CodigoDescuento c WHERE c.codigo = :codigo AND c.usado = false";
        try {
            CodigoDescuento codigoDescuento = entityManager.createQuery(jpql, CodigoDescuento.class)
                .setParameter("codigo", codigo)
                .getSingleResult();
            return Optional.of(codigoDescuento);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodigoDescuento> buscarPorUsuarioId(Long usuarioId) {
        String jpql = "SELECT c FROM CodigoDescuento c WHERE c.usuario.id = :usuarioId";
        return entityManager.createQuery(jpql, CodigoDescuento.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    @Transactional
    public void actualizar(CodigoDescuento codigoDescuento) {
        entityManager.merge(codigoDescuento);
    }
}
