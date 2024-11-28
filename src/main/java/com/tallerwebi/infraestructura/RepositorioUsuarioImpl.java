package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.TipoPlan;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate){
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    //
    @Override
    public Usuario buscarPorId(Long id) {
        return (Usuario) sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM Usuario u " +
                        "LEFT JOIN FETCH u.interesComidas " +
                        "LEFT JOIN FETCH u.recetas " +
                        "LEFT JOIN FETCH u.comentarios " +
                        "WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public void eliminar(Usuario usuario) {
        sessionFactory.getCurrentSession().delete(usuario);
    }


    //CONSULTAS POSIBLES PARA USUARIOS

    @Override
    public List<Receta> obtenerRecetasGuardadas(Long usuarioId) {
        // Consulta para obtener recetas guardadas por el usuario
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = buscarPorId(usuarioId);
        return (List<Receta>) usuario.getRecetasGuardadas();
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Usuario.class)
                .add(Restrictions.ilike("nombre", "%" + nombre + "%"))
                .list();
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public Usuario buscarPorCodigoActivacion(String codigo) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("activationCode", codigo))
                .uniqueResult();
    }


}

