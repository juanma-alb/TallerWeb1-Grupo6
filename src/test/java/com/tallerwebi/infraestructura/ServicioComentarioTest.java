package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.RepositorioComentario;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ServicioComentarioTest {

    @Mock
    private RepositorioComentario repositorioComentario;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RepositorioReceta repositorioReceta;

    @InjectMocks
    private ServicioComentarioImpl servicioComentario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregarComentario() {
        Long recetaId = 1L;
        Long usuarioId = 2L;
        String contenido = "buena receta";
        int calificacion = 5;

        Usuario usuarioMock = mock(Usuario.class);
        Receta recetaMock = mock(Receta.class);

        when(repositorioUsuario.buscarPorId(usuarioId)).thenReturn(usuarioMock);
        when(repositorioReceta.buscarRecetaPorId(recetaId)).thenReturn(recetaMock);

        servicioComentario.agregarComentario(recetaId, usuarioId, contenido, calificacion);

        verify(repositorioUsuario).buscarPorId(usuarioId);
        verify(repositorioReceta).buscarRecetaPorId(recetaId);
        verify(repositorioComentario).guardar(any(Comentario.class));
        verify(repositorioReceta).save(recetaMock);
    }
}
