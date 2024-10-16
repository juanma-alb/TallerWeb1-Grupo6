package com.tallerwebi.infraestructura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioDescubreRecetas;


@Repository
public class RepositorioDescubreRecetasImpl implements RepositorioDescubreRecetas {

    private final JdbcTemplate jdbcTemplate;

    public RepositorioDescubreRecetasImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save2(Receta receta) {
        String sql = "INSERT INTO receta (nombre, descripcion, calorias, tiempoPreparacion, comensales, foto, categoria, subcategoria, calificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, receta.getNombre(), receta.getDescripcion(), receta.getCalorias(), receta.getTiempoPreparacion(), receta.getComensales(), receta.getFoto(), receta.getCategoria(), receta.getSubcategoria(), receta.getCalificacion());
    }

 
    @Override
public List<Receta> listarRecetasPredefinidas() {
    String sql = "SELECT * FROM receta WHERE predefinida = true";  // Solo recetas predefinidas
    return jdbcTemplate.query(sql, this::mapRowToReceta);
}

    @Override
    public Receta obtenerRecetaPorId(Long id) {
        String sql = "SELECT * FROM receta WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RecetaRowMapper());
    }

    @Override
    public void eliminarReceta(Long id) {
        String sql = "DELETE FROM receta WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void actualizar(Receta receta) {
        String sql = "UPDATE receta SET nombre = ?, descripcion = ?, calorias = ?, comensales = ?, tiempoPreparacion = ?, calificacion = ?, categoria = ?, subcategoria = ?, foto = ? WHERE id = ?";
        jdbcTemplate.update(sql, receta.getNombre(), receta.getDescripcion(), receta.getCalorias(), receta.getComensales(), receta.getTiempoPreparacion(), receta.getCalificacion(), receta.getCategoria(), receta.getSubcategoria(), receta.getFoto(), receta.getId());
    }

    
    public List<Receta> filtrarPorCategoria(String categoria, String subcategoria) {
    String sql = "SELECT * FROM receta WHERE 1=1";
    List<Object> params = new ArrayList<>();
    
    if (categoria != null && !categoria.isEmpty()) {
        sql += " AND categoria = ?";
        params.add(categoria);
    }

    if (subcategoria != null && !subcategoria.isEmpty()) {
        sql += " AND subcategoria = ?";
        params.add(subcategoria);
    }

    return jdbcTemplate.query(sql, params.toArray(), this::mapRowToReceta);
}

    private Receta mapRowToReceta(ResultSet rs, int rowNum) throws SQLException {
        Receta receta = new Receta();
        receta.setId(rs.getLong("id"));
        receta.setNombre(rs.getString("nombre"));
        receta.setDescripcion(rs.getString("descripcion"));
        receta.setCalorias(rs.getInt("calorias"));
        receta.setTiempoPreparacion(rs.getInt("tiempoPreparacion"));
        receta.setComensales(rs.getInt("comensales"));
        receta.setFoto(rs.getString("foto"));
        receta.setCategoria(rs.getString("categoria"));         
        receta.setSubcategoria(rs.getString("subcategoria"));   
        receta.setCalificacion(rs.getInt("calificacion"));      
        return receta;
    }

    
}


