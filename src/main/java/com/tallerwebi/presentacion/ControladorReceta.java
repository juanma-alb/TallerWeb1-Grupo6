package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorReceta {

    @Autowired
    private ServicioReceta servicioReceta;


    @GetMapping("/crear-receta")
    public ModelAndView mostrarFormularioCrearReceta(HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("crearReceta");
        modelAndView.addObject("receta", new Receta());
        return modelAndView;
    }

    @PostMapping("/crear-receta")
    public ModelAndView crearReceta(@ModelAttribute Receta receta /*,@RequestParam("foto") MultipartFile archivoFoto*/, HttpServletRequest request) {

        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            receta.setUsuario(usuarioActual);
            /*
            if (!archivoFoto.isEmpty()) {
                String nombreFoto = servicioReceta.guardarFoto(archivoFoto);
                receta.setFoto(nombreFoto);
            }
            */
            servicioReceta.crearReceta(receta);

            return new ModelAndView("redirect:/home");

        } catch (Exception e) {
            ModelAndView model = new ModelAndView("crearReceta");
            model.addObject("error", e.getMessage());
            return model;
        }
    }

    // Listar las recetas del usuario actual
    @GetMapping("/mis-recetas")
    public ModelAndView listarMisRecetas(HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Receta> recetas = servicioReceta.listarRecetasPorUsuario(usuarioActual.getId());
        ModelAndView modelAndView = new ModelAndView("misRecetas");
        modelAndView.addObject("recetas", recetas);
        return modelAndView;
    }



}
