package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorDescubreRecetasTest {

    private ControladorDescubreRecetas controlador;
    private ServicioDescubreRecetas servicioDescubreRecetas;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        servicioDescubreRecetas = mock(ServicioDescubreRecetas.class);
        controlador = new ControladorDescubreRecetas(servicioDescubreRecetas, mock(ServicioUsuario.class));

        session = mock(HttpSession.class);
    }

    @Test
    public void testIrADescubreRecetas() {
        List<Receta> recetasMock = Collections.emptyList();
        when(servicioDescubreRecetas.obtenerRecetasParaCarrusel()).thenReturn(recetasMock);

        Usuario usuario = mock(Usuario.class);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(usuario.getRol()).thenReturn("USER");

        ModelAndView modelAndView = controlador.irADescubreRecetas(session);

        assertEquals("descubre-recetas", modelAndView.getViewName(), "El nombre de la vista debería ser 'descubre-recetas'");
        assertEquals(recetasMock, modelAndView.getModel().get("recetas"), "La lista de recetas debería estar vacía");
        assertEquals("", modelAndView.getModel().get("categoria"), "La categoría debería ser un string vacío");
        assertEquals("", modelAndView.getModel().get("subcategoria"), "La subcategoría debería ser un string vacío");

        assertFalse((Boolean) modelAndView.getModel().get("esAdmin"), "El usuario no es admin");
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


    @Test
    public void testEditarRecetaAdminConPermiso() {
        Long recetaId = 1L;
        Usuario usuario = mock(Usuario.class);
        Receta receta = mock(Receta.class);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(servicioDescubreRecetas.obtenerRecetaPorId(recetaId)).thenReturn(receta);
        when(receta.getUsuario()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRol()).thenReturn("ADMIN");

        ModelAndView modelAndView = controlador.editarRecetaAdmin(recetaId, session);

        assertEquals("editar-receta-admin", modelAndView.getViewName(), "La vista debería ser 'editar-receta-admin'");
        assertEquals(receta, modelAndView.getModel().get("receta"), "La receta debería ser la obtenida por ID");
    }

    @Test
    public void testEliminarRecetaAdminConPermiso() {
        Long recetaId = 1L;
        Usuario usuario = mock(Usuario.class);
        Receta receta = mock(Receta.class);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(servicioDescubreRecetas.obtenerRecetaPorId(recetaId)).thenReturn(receta);
        when(usuario.getRol()).thenReturn("ADMIN");
        when(receta.getUsuario()).thenReturn(usuario);
        when(receta.getUsuario().getId()).thenReturn(1L);

        String viewName = controlador.eliminarReceta(recetaId, session);

        assertEquals("redirect:/descubre-recetas", viewName, "Debería redirigir a la vista de descubre-recetas después de eliminar");
        verify(servicioDescubreRecetas, times(1)).eliminarReceta(recetaId);
    }


}
