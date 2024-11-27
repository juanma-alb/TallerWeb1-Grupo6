package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.ValidacionesIncorrectas;
import org.junit.jupiter.api.BeforeEach;


import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;
	private ServicioUsuario servicioUsuarioMock;


	@BeforeEach
	public void init(){
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock, servicioUsuarioMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	/*
	@Test
	public void registrarmeSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, ValidacionesIncorrectas {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), any(DatosRegistro.class));
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ValidacionesIncorrectas {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(any(Usuario.class), any(DatosRegistro.class));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

*/
	/*@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ValidacionesIncorrectas {

		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getEmail()).thenReturn("test@example.com");
		when(datosRegistroMock.getPassword()).thenReturn("password");
		when(datosRegistroMock.getNombre()).thenReturn("Nombre");

		// Simula un error de validación en el servicio de registro
		doThrow(new ValidacionesIncorrectas("Error en validaciones")).when(servicioLoginMock).registrar(any(Usuario.class), any(DatosRegistro.class));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}

	@Test
	public void testRegistrarmeExitoso() {
		DatosRegistro datosRegistro = new DatosRegistro();
		datosRegistro.setEmail("nuevo_usuario@example.com");
		datosRegistro.setPassword("P@ssw0rd");
		datosRegistro.setNombre("Nuevo Usuario");

		Usuario usuario = new Usuario();
		usuario.setEmail("nuevo_usuario@example.com");
		usuario.setPassword("P@ssw0rd");
		usuario.setNombre("Nuevo Usuario");

		try {
			controladorLogin.registrarme(datosRegistro);
		} catch (Exception e) {
			fail("El registro del usuario debería ser exitoso");
		}
	}
	@Test
	public void registrarmeConContrasenaInvalidaDeberiaMostrarError() throws ValidacionesIncorrectas, UsuarioExistente {
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getPassword()).thenReturn("123"); // Contraseña inválida
		when(datosRegistroMock.getNombre()).thenReturn("Nombre Correcto");

		doThrow(new ValidacionesIncorrectas("La contraseña no cumple con los requisitos."))
				.when(servicioLoginMock).registrar(any(Usuario.class), any(DatosRegistro.class));

		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("La contraseña no cumple con los requisitos."));
	}


	@Test
	public void registrarmeConNombreInvalidoDeberiaMostrarError() throws ValidacionesIncorrectas, UsuarioExistente {
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getPassword()).thenReturn("P@ssw0rd");
		when(datosRegistroMock.getNombre()).thenReturn("Nombre123"); // Nombre inválido

		doThrow(new ValidacionesIncorrectas("El nombre solo puede contener letras."))
				.when(servicioLoginMock).registrar(any(Usuario.class), any(DatosRegistro.class));

		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El nombre solo puede contener letras."));
	}
	*/


}