package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public ServicioRecetaImpl(RepositorioReceta repositorioReceta, RepositorioUsuario repositorioUsuario) {
        this.repositorioReceta = repositorioReceta;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void crearReceta(Receta receta) {
        repositorioReceta.save(receta);
    }

    public List<Receta> listarRecetasPorUsuario(Long usuarioId) {
    return repositorioReceta.findByUsuarioIdAndNoPredefinidas(usuarioId);
}

    @Override
    public List<Receta> listarTodasLasRecetas(long l) {
        return repositorioReceta.listarTodasLasRecetas();
    }

    @Override
    public List<Receta> buscarRecetasPorNombreRecetas(String filtro) {
        return repositorioReceta.buscarRecetasPorNombreRecetas(filtro);
    }

    @Override
    public Receta buscarRecetaPorId(Long recetaId) {
        return repositorioReceta.buscarRecetaPorId(recetaId);
    }

    @Override
    public void actualizarReceta(Receta receta) {
        repositorioReceta.actualizar(receta);
    }

    @Override
    public void eliminarReceta(Long id) {
        repositorioReceta.eliminar(id);
    }

    @Override
    public List<Receta> listarRecetasGuardadasPorUsuario(Long usuarioId) {
        return repositorioReceta.listarRecetasGuardadasPorUsuario(usuarioId);
    }

    @Override
    public void guardarReceta(Long recetaId, Long usuarioId) {
        Receta receta = repositorioReceta.buscarRecetaPorId(recetaId);
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);

        receta.setGuardada(true);
        receta.setUsuario(usuario);
        repositorioReceta.save(receta);
    }

    @Override
    public int contarRecetasGuardadasPorTipo(Long usuarioId, String tipoComida) {
        return repositorioReceta.contarRecetasGuardadasPorUsuarioYTipo(usuarioId, tipoComida);
    }



    @Override
    public String calcularNivelInteres(int cantidad) {
        if (cantidad < 3) {
            return "poco";
        } else if (cantidad < 6) {
            return "mucho";
        } else {
            return "en exceso";
        }
    }

    @Override
    public int calcularPorcentaje(int cantidad) {
        int maxRecetas = 10;
        if (cantidad <= 0) {
            return 0;
        } else if (cantidad >= maxRecetas) {
            return 100;
        } else {
            return (cantidad * 100) / maxRecetas;
        }
    }

    @Override
    public void eliminarRecetaGuardada(Long recetaId, Long usuarioId) {
        Receta receta = repositorioReceta.buscarRecetaPorId(recetaId);
        if (receta != null && receta.getUsuario().getId().equals(usuarioId)) {
            receta.setGuardada(false); // Cambia el estado de guardada
            actualizarReceta(receta); // Guarda los cambios
        }
    }

    @Override
    public String obtenerTipoComidaFavorito(Long usuarioId) {
        return repositorioReceta.encontrarTipoComidaFavorito(usuarioId);
    }

    @Override
    public List<Receta> recomendarRecetasPorTipo(String tipoComida) {
        return repositorioReceta.encontrarRecetasPorTipo(tipoComida);
    }


}



