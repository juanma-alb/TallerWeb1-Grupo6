package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.RepositorioComentario;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioUsuarioTest {

    private ServicioUsuarioImpl servicioUsuario;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioComentario repositorioComentarioMock;

    @BeforeEach
    public void setUp() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        repositorioComentarioMock = mock(RepositorioComentario.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock, repositorioComentarioMock);
    }

    @Test
    public void testObtenerUsuarioPorEmail() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuario);

        Usuario result = servicioUsuario.obtenerUsuarioPorEmail(email);

        assertNotNull(result, "usuario encontrado");
        assertEquals(email, result.getEmail(), "");
    }

    @Test
    public void testModificarContraseniaUsuarioExitoso() throws Exception {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword("oldPassword");
        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuario);

        servicioUsuario.modificarContraseniaUsuario(usuario, "oldPassword", "newPassword123", "newPassword123");

        verify(repositorioUsuarioMock).modificar(usuario);
        assertEquals("newPassword123", usuario.getPassword(), "contraseña actualizada");
    }

    @Test
    public void testModificarContraseniaUsuarioConErrorPasswordIncorrecta() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword("oldPassword");
        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuario);

        Exception exception = assertThrows(Exception.class, () ->
                servicioUsuario.modificarContraseniaUsuario(usuario, "wrongPassword", "newPassword123", "newPassword123")
        );
        assertEquals("La contraseña actual no es válida.", exception.getMessage());
    }

    @Test
    public void testModificarDatosPerfil() throws Exception {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre("Old Name");
        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuario);

        Usuario datosPerfil = new Usuario();
        datosPerfil.setNombre("New Name");
        datosPerfil.setDescripcion("New description");

        servicioUsuario.modificarDatosPerfil(usuario, datosPerfil);

        assertEquals("New Name", usuario.getNombre(), "nombre actualizado");
        assertEquals("New description", usuario.getDescripcion(), "descripción actualizada");
        verify(repositorioUsuarioMock).modificar(usuario);
    }


    /*
    @Test
    public void testObtenerPlanPorId() {
        Long planId = 1L;
        Plan plan = new Plan();
        plan.setId(planId);
        when(repositorioUsuarioMock.obtenerPlanPorId(planId)).thenReturn(plan);

        Plan result = servicioUsuario.obtenerPlanPorId(planId);

        assertNotNull(result, "plan encontrado");
        assertEquals(planId, result.getId(), "");
    }

    @Test
    public void testObtenerTodosLosPlanes() {
        List<Plan> planes = Arrays.asList(new Plan(), new Plan(), new Plan());
        when(repositorioUsuarioMock.obtenerTodosLosPlanes()).thenReturn(planes);

        List<Plan> result = servicioUsuario.obtenerTodosLosPlanes();

        assertNotNull(result, "lista de planes no nula");
        assertEquals(3, result.size(), " 3");
    }
    */
}
