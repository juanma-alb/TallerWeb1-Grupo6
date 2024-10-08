package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.RepositorioComentario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioComentarioImpl implements RepositorioComentario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioComentarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Comentario comentario) {
        sessionFactory.getCurrentSession().save(comentario);
    }

    @Override
    public List<Comentario> buscarPorRecetaId(Long recetaId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Comentario c WHERE c.receta.id = :recetaId", Comentario.class)
                .setParameter("recetaId", recetaId)
                .getResultList();
    }
}
