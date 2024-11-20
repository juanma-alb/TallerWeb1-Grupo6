package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ControladorHome {

    @Autowired
    private ServicioHome servicioHome;

    @Autowired
    private ServicioDescubreRecetas servicioDescubreRecetas;

    @Autowired
    private ServicioReceta servicioReceta;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorHome(ServicioHome servicioHome) {
        this.servicioHome = servicioHome;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpServletRequest request) {

         Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
         if (usuario == null) {
            return new ModelAndView("redirect:/login"); // Redirigir si no está autenticado
        }
        List<Receta> recetas = servicioDescubreRecetas.obtenerRecetasParaCarrusel();

        List<Receta> recetasOrdenadas = servicioHome.obtenerRecetasOrdenadasPorCalificacion();


        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("recetas", recetas);
        modelAndView.addObject("recetasOrdenadas", recetasOrdenadas);
        modelAndView.addObject("usuario", usuario);

        return modelAndView;
    }


   /* @PostMapping("/guardar-intereses")

   public String guardarIntereses(@RequestParam List<String> tiposComida, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioReceta.actualizarInteresesUsuario(usuario.getId(), tiposComida);
        usuario.setPrimerIngreso(false);
        servicioUsuario.actualizarUsuario(usuario);
        request.getSession().setAttribute("usuario", usuario);

        System.out.println("Datos recibidos: " + tiposComida);

        return "redirect:/home";
    }*/

}