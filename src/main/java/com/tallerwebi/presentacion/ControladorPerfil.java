package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ControladorPerfil {


    private ServicioUsuario servicioUsuario;


    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/perfil")
    public ModelAndView verPerfil(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("USER_EMAIL");
        Usuario usuario = servicioUsuario.obtenerUsuarioPorEmail(email);
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil", model);
    }

    @PostMapping("/guardar-cambios")
    public ModelAndView editarPerfil(@ModelAttribute("usuario") Usuario usuario,
                                     @RequestParam("password") String password,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     @RequestParam("foto") MultipartFile foto,
                                     HttpServletRequest request) {

        if (!password.equals(confirmPassword)) {
            ModelMap model = new ModelMap();
            model.put("usuario", usuario);
            model.put("error", "Las contraseñas no coinciden.");
            return new ModelAndView("editarPerfil", model);
        }

        Usuario usuarioActual = servicioUsuario.obtenerUsuarioPorEmail(usuario.getEmail());
        if (usuarioActual != null) {
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setDescripcion(usuario.getDescripcion());
            usuarioActual.setCiudad(usuario.getCiudad());
            if (!password.isEmpty()) {
                usuarioActual.setPassword(password);
            }

            if (!foto.isEmpty()) {
                String nombreArchivo = foto.getOriginalFilename();
                String rutaRelativa = "core/imagenes/" + nombreArchivo;
                Path rutaAbsoluta = Paths.get("src/main/webapp/resources/" + rutaRelativa);

                try {
                    Files.createDirectories(rutaAbsoluta.getParent());
                    foto.transferTo(rutaAbsoluta.toFile());
                    usuarioActual.setFoto(rutaRelativa);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            servicioUsuario.modificarUsuario(usuarioActual);
        }

        return new ModelAndView("redirect:/perfil");
    }
}
