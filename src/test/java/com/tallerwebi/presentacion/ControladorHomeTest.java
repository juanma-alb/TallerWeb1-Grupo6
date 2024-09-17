package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Controller
public class ControladorHomeTest {

    @Test
    public void testIrAHome() {
        // Crear una instancia del controlador
        ControladorHome controlador = new ControladorHome();

        // Llamar al método irAHome
        ModelAndView modelAndView = controlador.irAHome();

        // Verificar que el nombre de la vista es "home"
        assertEquals("home", modelAndView.getViewName(), "El nombre de la vista debería ser 'home'");
}
}

