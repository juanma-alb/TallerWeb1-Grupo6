package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.ServicioRecetaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorMisRecetas {

    @Autowired
    private ServicioReceta  servicioReceta;

    @GetMapping("/misRecetas")
    public String listarRecetas(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
        List<Receta> recetas;

        if (filtro != null && !filtro.isEmpty()) {
            recetas = servicioReceta.buscarRecetasPorNombreRecetas(filtro); // Filtra por nombre de receta
        } else {
            recetas = servicioReceta.listarTodasLasRecetas(1L); // Devuelve todas las recetas del usuario
        }

        model.addAttribute("recetas", recetas);
        return "misRecetas";
    }

//    @GetMapping("/mis-recetas")
//    public ModelAndView mostrarMisRecetas(@RequestParam(required = false) String filtro, Model model, ModelMap modelMap) {
//        List<Receta> recetas;
//        ModelMap modelMap2 = new ModelMap();
//
//        if (filtro != null && !filtro.trim().isEmpty()) {
//            recetas = servicioReceta.buscarRecetasPorNombreRecetas(filtro);
//        } else {
//            recetas = servicioReceta.listarTodasLasRecetas(); // O la lógica que tengas para listar todas las recetas
//        }
//
//        modelMap2.put("recetas", recetas);
//        modelMap2.put("filtro", filtro);
//        return new ModelAndView("misRecetas" , modelMap2 ); // Nombre de tu vista
//    }



}
