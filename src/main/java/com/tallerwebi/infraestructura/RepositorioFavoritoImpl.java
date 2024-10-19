package com.tallerwebi.infraestructura;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.RepositorioFavorito;

@Repository
public class RepositorioFavoritoImpl implements RepositorioFavorito {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void guardar(Favorito favorito) {
        String sql = "INSERT INTO favorito (usuario_id, receta_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, favorito.getUsuario().getId(), favorito.getReceta().getId());
    }

    @Override
    public List<Favorito> buscarPorUsuario(Long usuarioId) {
        String sql = "SELECT * FROM favorito WHERE usuario_id = ?";
        return jdbcTemplate.query(sql, new Object[]{usuarioId}, new FavoritoRowMapper());
    }

    @Override
    public boolean existePorUsuarioIdYRecetaId(Long usuarioId, Long recetaId) {
        String sql = "SELECT COUNT(*) FROM favorito WHERE usuario_id = ? AND receta_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{usuarioId, recetaId}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void eliminarPorUsuarioIdYRecetaId(Long usuarioId, Long recetaId) {
        String sql = "DELETE FROM favorito WHERE usuario_id = ? AND receta_id = ?";
        jdbcTemplate.update(sql, usuarioId, recetaId);
    }
}

