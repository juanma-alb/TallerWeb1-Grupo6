package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.Usuario;
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

    @GetMapping("/comentarios/{id}")
    public ModelAndView mostrarComentario(@PathVariable("id") Long recetaId, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        Receta receta = servicioReceta.buscarRecetaPorId(recetaId);

        ModelAndView modelAndView = new ModelAndView("comentarios");
        modelAndView.addObject("receta", receta);

        return modelAndView;
    }


}
