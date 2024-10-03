package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioDescubreRecetas;

@Controller
public class ControladorDescubreRecetas {

    @Autowired
    private ServicioDescubreRecetas servicioDescubreRecetas;

    @RequestMapping(path = "/descubre-recetas", method = RequestMethod.GET)
    public ModelAndView irADescubreRecetas() {
        List<Receta> recetas = servicioDescubreRecetas.obtenerRecetasParaCarrusel();
        
        ModelAndView modelAndView = new ModelAndView("descubre-recetas");
        modelAndView.addObject("recetas", recetas);
        modelAndView.addObject("categoria", ""); 
        modelAndView.addObject("subcategoria", ""); 
        return modelAndView;
    }

    
    @RequestMapping(path = "/filtrar-recetas", method = RequestMethod.GET)
    public ModelAndView filtrarRecetas(@RequestParam(value = "categoria", required = false) String categoria, 
                                       @RequestParam(value = "subcategoria", required = false) String subcategoria) {
        List<Receta> recetasFiltradas = servicioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria);
        
        ModelAndView modelAndView = new ModelAndView("filtrar-recetas"); 
        modelAndView.addObject("recetas", recetasFiltradas);
        modelAndView.addObject("categoria", categoria); 
        modelAndView.addObject("subcategoria", subcategoria); 
        return modelAndView;
    }

}
