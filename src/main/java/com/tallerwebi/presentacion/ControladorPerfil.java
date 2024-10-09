package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Comentario;
import com.tallerwebi.dominio.ServicioComentario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ControladorPerfil {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioComentario servicioComentario;

    @Autowired
    public ControladorPerfil(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }


    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView verPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Comentario> comentarios = servicioComentario.listarComentariosPorUsuario(usuario.getId());
        usuario.setComentarios(comentarios);

        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(path = "/editar-perfil", method = RequestMethod.GET)
    public ModelAndView editarPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("editarPerfil", model);
    }

    @PostMapping("/guardar-cambios")
    public ModelAndView editarPerfil(@ModelAttribute Usuario usuario, HttpServletRequest request) {

        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        try {
            servicioUsuario.modificarDatosPerfil(usuarioActual, usuario);
            request.getSession().setAttribute("usuario", usuarioActual);
            return new ModelAndView("redirect:/perfil");
        } catch (Exception e) {
            model.put("usuario", usuario);
            model.put("error", e.getMessage());
            return new ModelAndView("editarPerfil", model);
        }
    }



    @PostMapping("/cambiar-contrasenia")
    public ModelAndView cambiarContrasenia(@ModelAttribute Usuario usuario, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        try {
            servicioUsuario.modificarContraseniaUsuario(usuarioActual, usuario.getCurrentPassword(), usuario.getNewPassword(), usuario.getConfirmPassword());
            return new ModelAndView("redirect:/perfil");
        } catch (Exception e) {
            model.put("usuario", usuario);
            model.put("error", e.getMessage());
            return new ModelAndView("editarPerfil", model);
        }

    }






}
        /*
        if (!foto.isEmpty()) {
            if (foto.getContentType().startsWith("image/") && foto.getSize() <= 5 * 1024 * 1024) {
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
            } else {
                ModelMap model = new ModelMap();
                model.put("usuario", usuario);
                model.put("error", "El archivo no es válido. Debe ser una imagen y no exceder los 5 MB.");
                return new ModelAndView("editarPerfil", model);
            }
        }
        */