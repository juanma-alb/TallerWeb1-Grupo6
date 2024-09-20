package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Controller
public class ControladorHomeTest {

    @Test
    public void testIrAHome() {
        ControladorHome controlador = new ControladorHome();


        ModelAndView modelAndView = controlador.irAHome();


        assertEquals("home", modelAndView.getViewName(), "El nombre de la vista debería ser 'home'");
}
}

