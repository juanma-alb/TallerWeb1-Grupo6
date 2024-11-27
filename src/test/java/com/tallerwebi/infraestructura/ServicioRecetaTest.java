package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioRecetaTest {

    private ServicioRecetaImpl servicioReceta;
    private RepositorioReceta repositorioRecetaMock;
    private RepositorioUsuario repositorioUsuarioMock;

    @BeforeEach
    public void setUp() {
        repositorioRecetaMock = mock(RepositorioReceta.class);
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioReceta = new ServicioRecetaImpl(repositorioRecetaMock, repositorioUsuarioMock);
    }

    @Test
    public void testCrearReceta() {
        Receta receta = new Receta();
        receta.setNombre("Receta de prueba");

        servicioReceta.crearReceta(receta);

        verify(repositorioRecetaMock).save(receta);
    }

    @Test
    public void testListarRecetasPorUsuario() {
        Long usuarioId = 1L;
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioRecetaMock.findByUsuarioIdAndNoPredefinidas(usuarioId)).thenReturn(recetas);

        List<Receta> result = servicioReceta.listarRecetasPorUsuario(usuarioId);

        assertEquals(2, result.size(), "2");
    }

    @Test
    public void testListarTodasLasRecetas() {
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioRecetaMock.listarTodasLasRecetas()).thenReturn(recetas);

        List<Receta> result = servicioReceta.listarTodasLasRecetas(0);

        assertEquals(2, result.size(), "2");
    }

    @Test
    public void testBuscarRecetasPorNombre() {
        String filtro = "Prueba";
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioRecetaMock.buscarRecetasPorNombreRecetas(filtro)).thenReturn(recetas);

        List<Receta> result = servicioReceta.buscarRecetasPorNombreRecetas(filtro);

        assertEquals(2, result.size(), " 2");
    }

    @Test
    public void testBuscarRecetaPorId() {
        Long recetaId = 1L;
        Receta receta = new Receta();
        when(repositorioRecetaMock.buscarRecetaPorId(recetaId)).thenReturn(receta);

        Receta result = servicioReceta.buscarRecetaPorId(recetaId);

        assertNotNull(result, "receta encontrada");
    }

    @Test
    public void testActualizarReceta() {
        Receta receta = new Receta();
        receta.setNombre("Receta Actualizada");

        servicioReceta.actualizarReceta(receta);

        verify(repositorioRecetaMock).actualizar(receta);
    }

    @Test
    public void testEliminarReceta() {
        Long recetaId = 1L;

        servicioReceta.eliminarReceta(recetaId);

        verify(repositorioRecetaMock).eliminar(recetaId);
    }

    @Test
    public void testGuardarReceta() {
        Long recetaId = 1L;
        Long usuarioId = 1L;
        Receta receta = new Receta();
        Usuario usuario = new Usuario();
        when(repositorioRecetaMock.buscarRecetaPorId(recetaId)).thenReturn(receta);
        when(repositorioUsuarioMock.buscarPorId(usuarioId)).thenReturn(usuario);

        servicioReceta.guardarReceta(recetaId, usuarioId);

        assertTrue(receta.isGuardada(), "receta guardada");
        verify(repositorioRecetaMock).save(receta);
    }

    @Test
    public void testContarRecetasGuardadasPorTipo() {
        Long usuarioId = 1L;
        String tipoComida = "Postre";
        when(repositorioRecetaMock.contarRecetasGuardadasPorUsuarioYTipo(usuarioId, tipoComida)).thenReturn(5);

        int result = servicioReceta.contarRecetasGuardadasPorTipo(usuarioId, tipoComida);

        assertEquals(5, result, "5");
    }

    @Test
    public void testCalcularNivelInteres() {
        assertEquals("poco", servicioReceta.calcularNivelInteres(2), "'poco'");
        assertEquals("mucho", servicioReceta.calcularNivelInteres(4), "'mucho'");
        assertEquals("en exceso", servicioReceta.calcularNivelInteres(6), "'en exceso'");
    }

    @Test
    public void testCalcularPorcentaje() {
        assertEquals(0, servicioReceta.calcularPorcentaje(0), " 0");
        assertEquals(50, servicioReceta.calcularPorcentaje(5), "50");
        assertEquals(100, servicioReceta.calcularPorcentaje(10), "100");
    }

    @Test
    public void testObtenerTipoComidaFavorito() {
        Long usuarioId = 1L;
        String tipoComida = "Postre";
        when(repositorioRecetaMock.encontrarTipoComidaFavorito(usuarioId)).thenReturn(tipoComida);

        String result = servicioReceta.obtenerTipoComidaFavorito(usuarioId);

        assertEquals(tipoComida, result, "'Postre'");
    }

    @Test
    public void testRecomendarRecetasPorTipo() {
        String tipoComida = "Postre";
        List<Receta> recetas = Arrays.asList(new Receta(), new Receta());
        when(repositorioRecetaMock.encontrarRecetasPorTipo(tipoComida)).thenReturn(recetas);

        List<Receta> result = servicioReceta.recomendarRecetasPorTipo(tipoComida);

        assertEquals(2, result.size(), " 2");
    }
}
