package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioHome;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorHome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorHomeTest {

    private ControladorHome controlador;
    private ServicioHome servicioHome;
    private ServicioDescubreRecetas servicioDescubreRecetas;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        servicioHome = mock(ServicioHome.class);
        servicioDescubreRecetas = mock(ServicioDescubreRecetas.class);

        controlador = new ControladorHome(servicioHome, servicioDescubreRecetas);

        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testIrAHomeSinUsuarioAutenticado() {
        when(session.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controlador.irAHome(request);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }

}
