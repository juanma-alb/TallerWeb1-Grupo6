package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioRecetaImpl implements RepositorioReceta {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioRecetaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Receta receta) {
        sessionFactory.getCurrentSession().saveOrUpdate(receta); // Cambiado a saveOrUpdate para que sirva también para actualizar
    }

    @Override
    public List<Receta> findByUsuarioId(Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.usuario.id = :usuarioId", Receta.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public List<Receta> listarTodasLasRecetas() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta", Receta.class)
                .getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorNombreRecetas(String filtro) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.nombre LIKE :filtro", Receta.class)
                .setParameter("filtro", "%" + filtro + "%")
                .getResultList();
    }

    @Override
    public Receta buscarRecetaPorId(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.id = :id", Receta.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void actualizar(Receta receta) {
        sessionFactory.getCurrentSession().update(receta);
    }

    @Override
    public void eliminar(Long id) {
        Receta receta = buscarRecetaPorId(id);
        if (receta != null) {
            sessionFactory.getCurrentSession().delete(receta);
        }
    }

}

