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
        // Consulta HQL para obtener recetas por el id de usuario
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.usuario.id = :usuarioId", Receta.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public List<Receta> listarTodasLasRecetas() {
        // Consulta HQL para listar todas las recetas
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta", Receta.class)
                .getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorNombreRecetas(String filtro) {
        // Consulta HQL para buscar recetas cuyo nombre coincida con el filtro
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.nombre LIKE :filtro", Receta.class)
                .setParameter("filtro", "%" + filtro + "%")
                .getResultList();
    }
    @Override
    public Receta buscarRecetaPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Receta.class, id);
    }


}
