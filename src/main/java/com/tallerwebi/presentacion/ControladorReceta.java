package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView crearReceta(@ModelAttribute Receta receta,
                                    HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            receta.setUsuario(usuarioActual);

            // Llamamos al servicio para guardar la receta sin manejar fotos
            servicioReceta.crearReceta(receta);
            return new ModelAndView("redirect:/home");

        } catch (Exception e) {
            // Agregamos el objeto receta en caso de que se necesite para mantener los datos en el formulario
            ModelAndView model = new ModelAndView("crearReceta");
            model.addObject("receta", receta); // Se mantiene la receta en el formulario de creación
            model.addObject("error", "Error al crear la receta: " + e.getMessage()); // Se muestra un mensaje de error más claro
            e.printStackTrace(); // Esto ayudará a identificar la causa del error en la consola
            return model;
        }
    }


    @GetMapping("/{id}")
    public String verReceta(@PathVariable("id") Long id, Model model) {
        Receta receta = servicioReceta.buscarRecetaPorId(id);
        if (receta!=null) {
            model.addAttribute("receta", receta);
            return "ver-receta";
        } else{
            return "redirect:/login";
        }
    }

}
