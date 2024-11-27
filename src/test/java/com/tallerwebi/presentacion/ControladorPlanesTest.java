package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorPlanesTest {
/*
    private ControladorPlanes controladorPlanes;
    private ServicioUsuario servicioUsuario;
    private Model model;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        servicioUsuario = mock(ServicioUsuario.class);
        controladorPlanes = new ControladorPlanes();
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testMostrarPlanes() {
        String viewName = controladorPlanes.mostrarPlanes(model);

        assertEquals("planes", viewName, "vista planes");
    }


    @Test
    public void testProcesarCompraPlanInvalido() {
        String planSeleccionado = "invalido";
        when(model.addAttribute(anyString(), anyString())).thenReturn(model);

        String result = controladorPlanes.procesarCompra(planSeleccionado, model);

        assertEquals("planes", result, "vista planes");
        verify(model).addAttribute("error", "Plan no válido. Intenta nuevamente.");
    }



    @Test
    public void testAdquirirPlanSinUsuario() {
        when(request.getSession().getAttribute("usuario")).thenReturn(null);

        Long idPlan = 1L;

        String viewName = controladorPlanes.adquirirPlan(idPlan, request);

        assertEquals("redirect:/planes", viewName, " vista planes");
        verify(servicioUsuario, never()).actualizarUsuario(any(Usuario.class));
    }

 */
}
