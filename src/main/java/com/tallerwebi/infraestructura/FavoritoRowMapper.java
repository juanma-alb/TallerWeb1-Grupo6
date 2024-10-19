package com.tallerwebi.infraestructura;

import org.springframework.jdbc.core.RowMapper;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoritoRowMapper implements RowMapper<Favorito> {

    @Override
    public Favorito mapRow(ResultSet rs, int rowNum) throws SQLException {
        Favorito favorito = new Favorito();
        Usuario usuario = new Usuario();
        Receta receta = new Receta();

        usuario.setId(rs.getLong("usuario_id"));
        receta.setId(rs.getLong("receta_id"));

        favorito.setUsuario(usuario);
        favorito.setReceta(receta);

        return favorito;
    }
}

