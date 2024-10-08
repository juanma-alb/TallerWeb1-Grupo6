package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioHome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ControladorHomeTest {

    private ControladorHome controlador;
    private ServicioHome servicioHome;

    @BeforeEach
public void setUp() {
    servicioHome = Mockito.mock(ServicioHome.class);
    controlador = new ControladorHome(servicioHome);
}
    @Test
    public void testIrAHome() {
        List<Receta> recetasMock = Collections.emptyList();
        when(servicioHome.obtenerRecetasParaCarrusel()).thenReturn(recetasMock);

        ModelAndView modelAndView = controlador.irAHome();

        assertEquals("home", modelAndView.getViewName(), "El nombre de la vista debería ser 'home'");
        assertEquals(recetasMock, modelAndView.getModel().get("recetas"), "La lista de recetas debería estar vacía");
    }
}
