package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCrearReceta {


    @RequestMapping(path = "/crear-receta", method = RequestMethod.GET)
    public ModelAndView irACrearReceta() {
        return new ModelAndView("crear-receta");
    }
}
