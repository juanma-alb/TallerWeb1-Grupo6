package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.ValidacionesIncorrectas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;
	private ServicioUsuario servicioUsuarioMock;

	@BeforeEach
	public void init() {
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);

		servicioLoginMock = mock(ServicioLogin.class);
		servicioUsuarioMock = mock(ServicioUsuario.class);

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

	@Test
	public void registrarmeSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, ValidacionesIncorrectas {
		// Preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getEmail()).thenReturn("test@unlam.com");
		when(datosRegistroMock.getPassword()).thenReturn("P@ssw0rd");
		when(datosRegistroMock.getNombre()).thenReturn("Usuario Nuevo");

		Usuario usuarioMock = new Usuario();
		usuarioMock.setEmail(datosRegistroMock.getEmail());
		usuarioMock.setPassword(datosRegistroMock.getPassword());
		usuarioMock.setNombre(datosRegistroMock.getNombre());
		usuarioMock.setRol("USER");

		Plan planBasicoMock = mock(Plan.class);
		when(servicioUsuarioMock.obtenerPlanPorId(1L)).thenReturn(planBasicoMock);

		doNothing().when(servicioLoginMock).registrar(any(Usuario.class), eq(datosRegistroMock));

		// Ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioUsuarioMock, times(1)).obtenerPlanPorId(1L);
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistroMock));
	}


	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ValidacionesIncorrectas {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getEmail()).thenReturn("existente@unlam.com");
		when(datosRegistroMock.getPassword()).thenReturn("P@ssw0rd");
		when(datosRegistroMock.getNombre()).thenReturn("Usuario Existente");

		doThrow(new UsuarioExistente("El usuario ya existe"))
				.when(servicioLoginMock)
				.registrar(any(Usuario.class), eq(datosRegistroMock));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistroMock));
	}


	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ValidacionesIncorrectas {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getEmail()).thenReturn("test@unlam.com");
		when(datosRegistroMock.getPassword()).thenReturn("1234");
		when(datosRegistroMock.getNombre()).thenReturn("Nombre");

		doThrow(new ValidacionesIncorrectas("Error en validaciones"))
				.when(servicioLoginMock)
				.registrar(any(Usuario.class), eq(datosRegistroMock));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error en validaciones"));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistroMock));
	}


	@Test
	public void testRegistrarmeExitoso() throws ValidacionesIncorrectas, UsuarioExistente {
		DatosRegistro datosRegistro = new DatosRegistro();
		datosRegistro.setEmail("nuevo_usuario@example.com");
		datosRegistro.setPassword("P@ssw0rd");
		datosRegistro.setNombre("Nuevo Usuario");

		Usuario usuario = new Usuario();
		usuario.setEmail("nuevo_usuario@example.com");
		usuario.setPassword("P@ssw0rd");
		usuario.setNombre("Nuevo Usuario");

		doNothing().when(servicioLoginMock).registrar(any(Usuario.class), eq(datosRegistro));

		// Ejecución
		try {
			controladorLogin.registrarme(datosRegistro);
		} catch (Exception e) {
			fail("El registro del usuario debería ser exitoso");
		}
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistro));
	}

	@Test
	public void registrarmeConContrasenaInvalidaDeberiaMostrarError() throws ValidacionesIncorrectas, UsuarioExistente {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getPassword()).thenReturn("123");
		when(datosRegistroMock.getNombre()).thenReturn("Nombre Correcto");

		doThrow(new ValidacionesIncorrectas("La contraseña no cumple con los requisitos."))
				.when(servicioLoginMock).registrar(any(Usuario.class), eq(datosRegistroMock));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("La contraseña no cumple con los requisitos."));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistroMock));
	}


	@Test
	public void registrarmeConNombreInvalidoDeberiaMostrarError() throws ValidacionesIncorrectas, UsuarioExistente {
		// preparación
		DatosRegistro datosRegistroMock = mock(DatosRegistro.class);
		when(datosRegistroMock.getPassword()).thenReturn("P@ssw0rd");
		when(datosRegistroMock.getNombre()).thenReturn("Nombre123");

		doThrow(new ValidacionesIncorrectas("El nombre solo puede contener letras."))
				.when(servicioLoginMock).registrar(any(Usuario.class), eq(datosRegistroMock));

		// ejecución
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El nombre solo puede contener letras."));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class), eq(datosRegistroMock));
	}




}