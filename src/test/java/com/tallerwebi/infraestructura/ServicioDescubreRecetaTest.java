package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioDescubreRecetas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioDescubreRecetaTest {

    private ServicioDescubreRecetasImpl servicioDescubreRecetas;
    private RepositorioDescubreRecetas repositorioDescubreRecetasMock;

    @BeforeEach
    public void setUp() {
        repositorioDescubreRecetasMock = mock(RepositorioDescubreRecetas.class);
        servicioDescubreRecetas = new ServicioDescubreRecetasImpl(repositorioDescubreRecetasMock);
    }

    @Test
    public void testCrearReceta() {
        Receta receta = new Receta();
        receta.setNombre("Receta de prueba");

        servicioDescubreRecetas.crearReceta2(receta);

        verify(repositorioDescubreRecetasMock).save2(receta);
    }

    @Test
    public void testListarRecetas() {
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioDescubreRecetasMock.listarRecetasPredefinidas()).thenReturn(recetas);

        List<Receta> result = servicioDescubreRecetas.listarRecetas();

        assertEquals(2, result.size(), " 2");
    }

    @Test
    public void testObtenerRecetasParaCarrusel() {
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioDescubreRecetasMock.listarRecetasPredefinidas()).thenReturn(recetas);

        List<Receta> result = servicioDescubreRecetas.obtenerRecetasParaCarrusel();

        assertEquals(2, result.size(), " 2");
    }

    @Test
    public void testFiltrarPorCategoria() {
        String categoria = "Postre";
        String subcategoria = "Chocolate";
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioDescubreRecetasMock.filtrarPorCategoria(categoria, subcategoria)).thenReturn(recetas);

        List<Receta> result = servicioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria);

        assertEquals(2, result.size(), "2");
    }

    @Test
    public void testObtenerRecetaPorId() {
        Long recetaId = 1L;
        Receta receta = new Receta();
        when(repositorioDescubreRecetasMock.obtenerRecetaPorId(recetaId)).thenReturn(receta);

        Receta result = servicioDescubreRecetas.obtenerRecetaPorId(recetaId);

        assertNotNull(result, "receta encontrada");
    }

    @Test
    public void testEliminarReceta() {
        Long recetaId = 1L;

        servicioDescubreRecetas.eliminarReceta(recetaId);

        verify(repositorioDescubreRecetasMock).eliminarReceta(recetaId);
    }

    @Test
    public void testActualizarReceta() {
        Receta receta = new Receta();
        receta.setNombre("Receta Actualizada");

        servicioDescubreRecetas.actualizarReceta(receta);

        verify(repositorioDescubreRecetasMock).actualizar(receta);
    }
}
