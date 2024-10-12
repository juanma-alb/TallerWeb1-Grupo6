package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
    private ServicioReceta servicioReceta;

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
        List<Receta> receta = servicioReceta.listarRecetasPorUsuario(usuario.getId());
        usuario.setRecetas(receta);

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


    @RequestMapping(path = "/eliminar-cuenta", method = RequestMethod.GET)
    public ModelAndView confirmarEliminacionCuenta(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("confirmarEliminacion");
    }

    @RequestMapping(path = "/eliminar-cuenta", method = RequestMethod.POST)
    public ModelAndView eliminarCuenta(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario != null) {
            Long usuarioId = usuario.getId();
            servicioUsuario.eliminar(usuarioId);
            request.getSession().invalidate();
        }
        return new ModelAndView("redirect:/login");
    }


}