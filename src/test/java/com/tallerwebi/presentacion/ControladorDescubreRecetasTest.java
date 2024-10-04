package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ControladorDescubreRecetasTest {

    private ControladorDescubreRecetas controlador;
    private ServicioDescubreRecetas servicioDescubreRecetas;

    @BeforeEach
    public void setUp() {
        servicioDescubreRecetas = Mockito.mock(ServicioDescubreRecetas.class);
        controlador = new ControladorDescubreRecetas(servicioDescubreRecetas);
    }
    

    @Test
    public void testIrADescubreRecetas() {
        List<Receta> recetasMock = Collections.emptyList();
        when(servicioDescubreRecetas.obtenerRecetasParaCarrusel()).thenReturn(recetasMock);

        ModelAndView modelAndView = controlador.irADescubreRecetas();

        assertEquals("descubre-recetas", modelAndView.getViewName(), "El nombre de la vista debería ser 'descubre-recetas'");
        assertEquals(recetasMock, modelAndView.getModel().get("recetas"), "La lista de recetas debería estar vacía");
        assertEquals("", modelAndView.getModel().get("categoria"), "La categoría debería ser un string vacío");
        assertEquals("", modelAndView.getModel().get("subcategoria"), "La subcategoría debería ser un string vacío");
    }

    @Test
    public void testFiltrarRecetas() {
        String categoria = "Postres";
        String subcategoria = "Tartas";
        List<Receta> recetasFiltradasMock = Collections.emptyList();
        when(servicioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria)).thenReturn(recetasFiltradasMock);

        ModelAndView modelAndView = controlador.filtrarRecetas(categoria, subcategoria);

        assertEquals("filtrar-recetas", modelAndView.getViewName(), "El nombre de la vista debería ser 'filtrar-recetas'");
        assertEquals(recetasFiltradasMock, modelAndView.getModel().get("recetas"), "La lista de recetas filtradas debería estar vacía");
        assertEquals(categoria, modelAndView.getModel().get("categoria"), "La categoría debería coincidir con el valor pasado");
        assertEquals(subcategoria, modelAndView.getModel().get("subcategoria"), "La subcategoría debería coincidir con el valor pasado");
    }
}

