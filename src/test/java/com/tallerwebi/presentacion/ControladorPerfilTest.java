package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {
    private ControladorPerfil controladorPerfil;
    private ServicioUsuario servicioUsuarioMock;
    private Usuario usuarioMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorPerfil = new ControladorPerfil(servicioUsuarioMock);
        usuarioMock = mock(Usuario.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
    }

    @Test
    public void verPerfilDeberiaMostrarPerfilDelUsuario() {
        String email = "alguien@gmail.com";
        when(usuarioMock.getEmail()).thenReturn(email);
        when(servicioUsuarioMock.obtenerUsuarioPorEmail(email)).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorPerfil.verPerfil(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
        assertThat(modelAndView.getModel().get("usuario"), equalTo(usuarioMock));
    }

    @Test
    public void editarPerfilDeberiaMostrarFormularioDeEdicion() {
        String email = "alguien@gmail.com";
        when(usuarioMock.getEmail()).thenReturn(email);
        when(servicioUsuarioMock.obtenerUsuarioPorEmail(email)).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorPerfil.editarPerfil(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("editarPerfil"));
        assertThat(modelAndView.getModel().get("usuario"), equalTo(usuarioMock));
    }


    @Test
    public void editarPerfilSinUsuarioEnSesionDeberiaRedirigirALogin() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorPerfil.editarPerfil(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    /*
    @Test
    public void guardarCambiosDePerfilExitoso() throws Exception {
        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNombre("Nuevo Nombre");
        usuarioEditado.setDescripcion("Nueva Descripción");
        usuarioEditado.setCiudad("Nueva Ciudad");
        usuarioEditado.setPassword("NuevaPassword");

        Usuario usuarioActual = new Usuario();
        usuarioActual.setEmail("alguien@gmail.com");
        usuarioActual.setPassword("PasswordVieja");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioActual);

        doNothing().when(servicioUsuarioMock).cambiarContrasenia(
                anyString(),  // email
                anyString(),  // currentPassword
                anyString(),  // newPassword
                anyString()   // confirmPassword
        );

        doNothing().when(servicioUsuarioMock).modificarPerfil(any(Usuario.class));


        ModelAndView modelAndView = controladorPerfil.editarPerfil(usuarioEditado, "PasswordVieja", "NuevaPassword", "NuevaPassword", requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/perfil"));

        verify(servicioUsuarioMock, times(1)).cambiarContrasenia(
                eq(usuarioActual.getEmail()),  // email
                eq("PasswordVieja"),           // currentPassword
                eq("NuevaPassword"),           // newPassword
                eq("NuevaPassword")            // confirmPassword
        );

        verify(servicioUsuarioMock, times(1)).modificarPerfil(usuarioActual);
    }
     */
}