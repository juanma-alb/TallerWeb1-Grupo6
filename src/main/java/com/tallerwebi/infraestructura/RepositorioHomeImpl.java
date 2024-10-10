package com.tallerwebi.infraestructura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioHome;

@Repository
public class RepositorioHomeImpl implements RepositorioHome {

    private final JdbcTemplate jdbcTemplate;

    public RepositorioHomeImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save2(Receta receta) {
        String sql = "INSERT INTO receta (nombre, descripcion, calorias, tiempoPreparacion, comensales, foto, categoria, subcategoria, calificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, receta.getNombre(), receta.getDescripcion(), receta.getCalorias(), receta.getTiempoPreparacion(), receta.getComensales(), receta.getFoto(), receta.getCategoria(), receta.getSubcategoria(), receta.getCalificacion());
    }

    @Override
    public List<Receta> listarRecetas() {
        String sql = "SELECT * FROM receta";
        return jdbcTemplate.query(sql, this::mapRowToReceta);
    }

     // metodo para buscar recetas en la barra del nav
     @Override
     public List<Receta> buscarPorCriterio(String query) {
         String sql = "SELECT * FROM receta WHERE lower(nombre) LIKE ? OR lower(descripcion) LIKE ? OR lower(categoria) LIKE ? OR lower(subcategoria) LIKE ?";
         String formattedQuery = "%" + query.toLowerCase() + "%";
         return jdbcTemplate.query(sql, this::mapRowToReceta, formattedQuery, formattedQuery, formattedQuery, formattedQuery);
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