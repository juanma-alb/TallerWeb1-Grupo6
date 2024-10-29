package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Autowired
    private RepositorioUsuario repositorioUsuario;


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

        // Aquí puedes agregar la lógica para marcar una receta como guardada
        receta.setGuardada(true);
        receta.setUsuario(usuario);  // Asegúrate de que la receta esté asociada al usuario
        repositorioReceta.save(receta);
    }

    // Método para contar recetas guardadas por tipo de comida
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
        // Suponiendo que el máximo es 10 recetas guardadas para obtener un 100% de interés
        int maxRecetas = 10; // Puedes ajustar este valor según tus necesidades
        if (cantidad <= 0) {
            return 0; // Si no hay recetas, el porcentaje es 0
        } else if (cantidad >= maxRecetas) {
            return 100; // Si se alcanzan o superan las 10 recetas, el interés es 100%
        } else {
            return (cantidad * 100) / maxRecetas; // Cálculo del porcentaje
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
    }



