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
    
        if (receta != null && usuario != null) {
            receta.setGuardada(true);
            receta.setUsuario(usuario);  
            repositorioReceta.save(receta);
        }
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

    if (receta != null) {
        if (receta.isPredefinida()) {
            receta.setGuardada(false);
            actualizarReceta(receta);
        }
        else if (receta.getUsuario() != null && receta.getUsuario().getId().equals(usuarioId)) {
            receta.setGuardada(false);
            actualizarReceta(receta);
        }
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


    @Override
    public void actualizarInteresesUsuario(Long usuarioId, List<String> tiposComida) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
        if (usuario != null) {
            for (String tipo : tiposComida) {
                InteresComida interes = usuario.getInteresComidas()
                    .stream()
                    .filter(i -> i.getTipo().equals(tipo))
                    .findFirst()
                    .orElse(new InteresComida(tipo, "poco", 0));
                
                int nuevoPorcentaje = Math.min(100, interes.getPorcentaje() + 20);
                interes.setPorcentaje(nuevoPorcentaje);
                interes.setNivel(calcularNivelInteres(nuevoPorcentaje / 10)); 
                
                if (!usuario.getInteresComidas().contains(interes)) {
                    usuario.getInteresComidas().add(interes);
                }
            }
            repositorioUsuario.guardar(usuario);
        }
    }

}



