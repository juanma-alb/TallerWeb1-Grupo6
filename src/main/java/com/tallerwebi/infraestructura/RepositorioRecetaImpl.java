package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import org.hibernate.SessionFactory;
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
        sessionFactory.getCurrentSession().save(receta);
    }

    @Override
    public List<Receta> findByUsuarioId(Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.usuario.id = :usuarioId", Receta.class)
                .setParameter("usuarioId", usuarioId)
                .list(); // Retorna una lista de recetas
    }


}
