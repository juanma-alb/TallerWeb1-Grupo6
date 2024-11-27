package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.InteresComida;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

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
    public void verPerfilSinUsuarioEnSesionDeberiaRedirigirALogin() {
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorPerfil.verPerfil(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void eliminarCuentaConUsuarioDeberiaInvalidarSesionYRedirigirALogin() {
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(1L);

        ModelAndView modelAndView = controladorPerfil.eliminarCuenta(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioUsuarioMock, times(1)).eliminar(1L);
        verify(sessionMock, times(1)).invalidate();
    }

    @Test
    public void cambiarContraseniaConErrorDeberiaMostrarError() throws Exception {
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        doThrow(new RuntimeException("Error cambiando contraseña")).when(servicioUsuarioMock)
                .modificarContraseniaUsuario(eq(usuarioMock), anyString(), anyString(), anyString());

        Usuario usuario = new Usuario();
        usuario.setCurrentPassword("passVieja");
        usuario.setNewPassword("passNueva");
        usuario.setConfirmPassword("passNueva");

        ModelAndView modelAndView = controladorPerfil.cambiarContrasenia(usuario, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("editarPerfil"));
        assertThat(modelAndView.getModel().get("error").toString(), equalTo("Error cambiando contraseña"));
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
    public void cambiarContraseniaExitoso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCurrentPassword("PasswordVieja");
        usuario.setNewPassword("NuevaPassword");
        usuario.setConfirmPassword("NuevaPassword");

        Usuario usuarioActual = new Usuario();
        usuarioActual.setEmail("alguien@gmail.com");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioActual);

        doNothing().when(servicioUsuarioMock).modificarContraseniaUsuario(
                eq(usuarioActual),
                eq("PasswordVieja"),
                eq("NuevaPassword"),
                eq("NuevaPassword")
        );

        ModelAndView modelAndView = controladorPerfil.cambiarContrasenia(usuario, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/perfil"));

        verify(servicioUsuarioMock, times(1)).modificarContraseniaUsuario(
                eq(usuarioActual),
                eq("PasswordVieja"),
                eq("NuevaPassword"),
                eq("NuevaPassword")
        );
    }


    @Test
    public void editarPerfilSinUsuarioEnSesionDeberiaRedirigirALogin() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorPerfil.editarPerfil(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void mostrarPlanAlimenticioDeberiaSeleccionarTipoComidaCorrecto() {
        InteresComida interes1 = new InteresComida("Vegano", "Alto", 80);
        InteresComida interes2 = new InteresComida("Básica", "Medio", 50);
        Set<InteresComida> intereses = new HashSet<>();
        intereses.add(interes1);
        intereses.add(interes2);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getInteresComidas()).thenReturn(intereses);

        ModelAndView modelAndView = controladorPerfil.mostrarPlanAlimenticio(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("plan-alimenticio"));
        assertThat(modelAndView.getModel().get("tipoComida").toString(), equalTo("Vegano"));
    }

}