package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public List<Receta> findByUsuarioIdAndNoPredefinidas(Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Receta r WHERE r.usuario.id = :usuarioId AND r.predefinida = false", Receta.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public List<Receta> findByUsuarioId(Long usuarioId) {
        return sessionFactory.getCurrentSession()

                .createNativeQuery("SELECT * FROM receta WHERE usuario_id = :usuarioId", Receta.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

//    @Override
//    public List<Receta> listarRecetasGuardas(Long usuarioId) {
//        return List.of();
//    }


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

    @Override
    public List<Receta> listarRecetasGuardadasPorUsuario(Long usuarioId) {
        String query = "FROM Receta r WHERE (r.usuario.id = :usuarioId OR r.predefinida = true) AND r.guardada = true";
        return sessionFactory.getCurrentSession()
                .createQuery(query, Receta.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }
    
    @Override
    public int contarRecetasGuardadasPorUsuarioYTipo(Long usuarioId, String tipoComida) {
        String query = "SELECT COUNT(r) FROM Receta r " +
                       "WHERE (r.usuario.id = :usuarioId OR r.predefinida = true) " +
                       "AND r.tipoComida = :tipoComida AND r.guardada = true";
        Long count = sessionFactory.getCurrentSession()
                .createQuery(query, Long.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("tipoComida", tipoComida)
                .getSingleResult();
        return count.intValue();
    }
    
    @Override
    public String encontrarTipoComidaFavorito(Long usuarioId) {
        String query = "SELECT r.tipoComida, COUNT(r) AS total FROM Receta r " +
                       "WHERE (r.usuario.id = :usuarioId OR r.predefinida = true) AND r.guardada = true " +
                       "GROUP BY r.tipoComida " +
                       "ORDER BY total DESC";
        List<Object[]> result = sessionFactory.getCurrentSession()
                .createQuery(query, Object[].class)
                .setParameter("usuarioId", usuarioId)
                .setMaxResults(1)
                .getResultList();
    
        if (!result.isEmpty()) {
            return (String) result.get(0)[0];
        } else {
            return "No se encontraron recetas";
        }
    }
    
    @Override
    public List<Receta> encontrarRecetasPorTipo(String tipoComida) {
        String query = "FROM Receta r WHERE r.tipoComida = :tipoComida AND (r.predefinida = true OR r.guardada = true)";
        return sessionFactory.getCurrentSession()
                .createQuery(query, Receta.class)
                .setParameter("tipoComida", tipoComida)
                .getResultList();
    }
    

}

