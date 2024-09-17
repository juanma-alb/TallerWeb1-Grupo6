package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ControladorDescubreRecetasTest {

    @Test
    public void testIrADescubrirRecetas() {
    
        ControladorDescubreRecetas controlador = new ControladorDescubreRecetas();

        
        ModelAndView modelAndView = controlador.irADescubreRecetas();

        
        assertEquals("descubre-recetas", modelAndView.getViewName(), "El nombre de la vista debería ser 'descubre-recetas'");
}
}
    

