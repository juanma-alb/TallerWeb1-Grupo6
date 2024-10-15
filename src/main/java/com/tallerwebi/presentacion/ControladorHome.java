package com.tallerwebi.presentacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import com.tallerwebi.dominio.Receta;

import com.tallerwebi.dominio.ServicioHome;
import com.tallerwebi.dominio.ServicioDescubreRecetas;

@Controller
public class ControladorHome {

    @Autowired
    private ServicioHome servicioHome;

    @Autowired
    private ServicioDescubreRecetas servicioDescubreRecetas;

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