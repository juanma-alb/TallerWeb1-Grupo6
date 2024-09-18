package com.tallerwebi.integracion;

/*
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class ControladorPerfilIntegracionTest {

    private MockMvc mockMvc;
    private RepositorioUsuario repositorioUsuario;
    private ServicioUsuario servicioUsuario;

    @BeforeEach
    public void setUp() {
        repositorioUsuario.borrarTodo();
    }

    @Test
    @Transactional
    public void guardarCambiosDePerfilDeberiaGuardarDatosCorrectamente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("alguien@gmail.com");
        usuario.setPassword("OldPassword");
        usuario.setNombre("Old Name");
        repositorioUsuario.guardar(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/guardar-cambios")
                        .param("currentPassword", "OldPassword")
                        .param("confirmPassword", "NewPassword")
                        .param("nombre", "New Name")
                        .param("descripcion", "New Description")
                        .param("ciudad", "New City")
                        .sessionAttr("usuario", usuario)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/perfil"));

        Usuario usuarioActualizado = repositorioUsuario.buscar("alguien@gmail.com");
        assertNotNull(usuarioActualizado);
        assertEquals("New Name", usuarioActualizado.getNombre());
        assertEquals("New Description", usuarioActualizado.getDescripcion());
        assertEquals("New City", usuarioActualizado.getCiudad());
        assertEquals("NewPassword", usuarioActualizado.getPassword());
    }
}
*/