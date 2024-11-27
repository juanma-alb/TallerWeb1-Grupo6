package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public Plan obtenerPlanPorId(Long id) {
        String sql = "SELECT * FROM Plan WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Plan plan = new Plan();
            plan.setId(rs.getLong("id"));
            plan.setNombrePlan(rs.getString("nombrePlan"));
            plan.setPrecio(rs.getDouble("precio"));
            return plan;
        });
    }

    @Override
    public List<Plan> obtenerTodosLosPlanes() {
        String sql = "SELECT * FROM Plan";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Plan plan = new Plan();
            plan.setId(rs.getLong("id"));
            plan.setNombrePlan(rs.getString("nombrePlan"));
            plan.setPrecio(rs.getDouble("precio"));
            return plan;
        });
    }

    @Override
public void actualizarPlanUsuario(Usuario usuario) {
    String sql = "UPDATE Usuario SET plan_id = ? WHERE id = ?";
    jdbcTemplate.update(sql, usuario.getPlan().getId(), usuario.getId());
}

}

