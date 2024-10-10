package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.RepositorioComentario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositorioComentarioImpl implements RepositorioComentario {

    private final SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public List<Comentario> buscarUsuarioPorId(Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Comentario c WHERE c.usuario.id = :usuarioId", Comentario.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public void eliminar(Comentario comentario) {
        if (entityManager.contains(comentario)) {
            entityManager.remove(comentario);
        } else {
            Comentario managedComentario = entityManager.find(Comentario.class, comentario.getId());
            if (managedComentario != null) {
                entityManager.remove(managedComentario);
            }
        }
    }


}
