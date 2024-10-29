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
    public ControladorHome(ServicioHome servicioHome) {
        this.servicioHome = servicioHome;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {

        List<Receta> recetas = servicioDescubreRecetas.obtenerRecetasParaCarrusel();

        List<Receta> recetasOrdenadas = servicioHome.obtenerRecetasOrdenadasPorCalificacion();


        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("recetas", recetas);
        modelAndView.addObject("recetasOrdenadas", recetasOrdenadas);


        return modelAndView;
    }




}