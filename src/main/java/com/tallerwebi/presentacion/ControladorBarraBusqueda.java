package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioHome;

//controlador global, corresponde a la barra de busqueda del nav

@Controller
public class ControladorBarraBusqueda {

    @Autowired
    private ServicioHome servicioHome;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/buscar")
    public String buscarRecetas(@RequestParam("query") String query, Model model) {
        List<Receta> recetasEncontradas = servicioHome.buscarRecetas(query);
        List<Usuario> usuariosEncontrados = servicioUsuario.buscarUsuariosPorNombre(query);
        model.addAttribute("recetas", recetasEncontradas);
        model.addAttribute("usuarios", usuariosEncontrados);

        return "filtrar-recetas";  
    }

}
