package com.tallerwebi.infraestructura;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.tallerwebi.dominio.Receta;

public class RecetaRowMapper implements RowMapper<Receta> {

    @Override
    public Receta mapRow(ResultSet rs, int rowNum) throws SQLException {
        Receta receta = new Receta();
        receta.setId(rs.getLong("id"));
        receta.setNombre(rs.getString("nombre"));
        receta.setDescripcion(rs.getString("descripcion"));
        receta.setCategoria(rs.getString("categoria"));
        receta.setSubcategoria(rs.getString("subcategoria"));
        receta.setFoto(rs.getString("foto"));
        receta.setCalificacion(rs.getInt("calificacion"));
        receta.setContenido(rs.getString("contenido"));
        receta.setPredefinida(rs.getBoolean("predefinida")); 
        return receta;
    }
}

