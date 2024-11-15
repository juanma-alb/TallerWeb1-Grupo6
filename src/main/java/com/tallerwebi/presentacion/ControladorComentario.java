package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorComentario {

    @Autowired
    private ServicioReceta servicioReceta;

    @Autowired
    private ServicioComentario servicioComentario;

    @GetMapping("/comentarios/{id}")
    public ModelAndView mostrarComentario(@PathVariable("id") Long recetaId, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        Receta receta = servicioReceta.buscarRecetaPorId(recetaId);

        
        if (receta.getUsuario() == null) {
            receta.setUsuario(new Usuario()); 
        }

        ModelAndView modelAndView = new ModelAndView("comentarios");
        modelAndView.addObject("receta", receta);

        return modelAndView;
    }

    @PostMapping("/mis-recetas/agregar-comentario")
    public String agregarComentario(@RequestParam("recetaId") Long recetaId,
                                    @RequestParam("contenido") String contenido,
                                    @RequestParam("calificacion") Integer calificacion,
                                    HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        servicioComentario.agregarComentario(recetaId, usuarioActual.getId(), contenido, calificacion);
        return "redirect:/misRecetas";
    }


}
