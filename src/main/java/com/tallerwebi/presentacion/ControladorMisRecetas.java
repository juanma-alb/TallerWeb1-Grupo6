package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/*
import com.tallerwebi.infraestructura.ServicioRecetaImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import java.util.stream.Collectors;
import java.security.Principal;
import java.time.LocalDateTime;
 */


@Controller
public class ControladorMisRecetas {

    @Autowired
    private ServicioReceta servicioReceta;

    @Autowired
    private ServicioComentario servicioComentario;

    @GetMapping("/misRecetas")
    public String listarRecetas(HttpServletRequest request,
                                @RequestParam(value = "filtro", required = false) String filtro,
                                Model model) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        List<Receta> recetas;

        if (usuarioActual == null) {
            return "redirect:/login";
        }

        if (filtro != null && !filtro.isEmpty()) {
            recetas = servicioReceta.buscarRecetasPorNombreRecetas(filtro);
        } else {
            recetas = servicioReceta.listarRecetasPorUsuario(usuarioActual.getId());
        }

        model.addAttribute("recetas", recetas);
        return "misRecetas";
    }

    @PostMapping("/mis-recetas/comentario")
    public String agregarComentario(@RequestParam Long recetaId, @RequestParam String contenido, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        servicioComentario.agregarComentario(recetaId, usuario.getId(), contenido);
        return "redirect:/misRecetas";
    }
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