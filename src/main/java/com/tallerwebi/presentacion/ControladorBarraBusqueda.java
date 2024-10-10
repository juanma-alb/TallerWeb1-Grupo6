package com.tallerwebi.presentacion;
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

    @GetMapping("/buscar")
    public String buscarRecetas(@RequestParam("query") String query, Model model) {
        List<Receta> recetasEncontradas = servicioHome.buscarRecetas(query);
        model.addAttribute("recetas", recetasEncontradas);
        return "filtrar-recetas";  
    }
}
