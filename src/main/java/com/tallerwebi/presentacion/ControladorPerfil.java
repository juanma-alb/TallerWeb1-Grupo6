package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener comentarios y recetas del usuario
        List<Comentario> comentarios = servicioComentario.listarComentariosPorUsuario(usuario.getId());
        usuario.setComentarios(new HashSet<>(comentarios));

        List<Receta> recetas = servicioReceta.listarRecetasPorUsuario(usuario.getId());
        usuario.setRecetas(new HashSet<>(recetas));


        if (usuario.getInteresComidas() == null) {
            usuario.setInteresComidas(new HashSet<>());
        }

        String[] tiposComida = {"Vegano", "Ovolactovegetariano", "Flexitariano", "Básica"};
        List<InteresComida> listaIntereses = new ArrayList<>();

        for (String tipo : tiposComida) {
            int cantidad = servicioReceta.contarRecetasGuardadasPorTipo(usuario.getId(), tipo);
            String nivel = servicioReceta.calcularNivelInteres(cantidad);
            int porcentaje = servicioReceta.calcularPorcentaje(cantidad);

            InteresComida interesComida = new InteresComida(tipo, nivel, porcentaje);
            listaIntereses.add(interesComida);
        }


        usuario.setInteresComidas(new HashSet<>(listaIntereses));


        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil", model);
    }


    @GetMapping("/perfil/{id}")
    public String verPerfilUsuario(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioActual == null) {
            return "redirect:/login";
        }

        Usuario usuario = servicioUsuario.obtenerUsuarioPorId(id);

        if (usuario == null) {
            return "redirect:/error";
        }

        if (usuarioActual.getId().equals(id)) {
            return "redirect:/perfil";
        }

        model.addAttribute("usuario", usuario);

        return "perfilUsuario";
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


    /* para mis recetas perfil
    @GetMapping("/receta/{id}")
    public String verReceta(@PathVariable Long id, Model model) {
    Receta receta = recetaService.findById(id);
    model.addAttribute("receta", receta);
    return "receta"; // nombre de la vista para mostrar la receta
}
     */

    @RequestMapping(path = "/plan-alimenticio", method = RequestMethod.GET)
    public ModelAndView mostrarPlanAlimenticio(HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }


        String tipoComida = obtenerTipoComidaMayor(usuario.getInteresComidas());


        ModelMap model = new ModelMap();
        model.put("tipoComida", tipoComida);
        return new ModelAndView("plan-alimenticio", model);
    }

    private String obtenerTipoComidaMayor(Set<InteresComida> interesComidas) {
        if (interesComidas == null || interesComidas.isEmpty()) {
            return null;
        }
        return interesComidas.stream()
                .max(Comparator.comparingInt(InteresComida::getPorcentaje))
                .map(InteresComida::getTipo)
                .orElse(null);
    }


    private Usuario obtenerUsuarioDeSesion(HttpServletRequest request) {
        return (Usuario) request.getSession().getAttribute("usuario");
    }

    @GetMapping("/recomendacion")
    public String recomendarRecetas(Model model, HttpServletRequest request) {
        Usuario usuario = obtenerUsuarioDeSesion(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        String tipoComidaFavorito = servicioReceta.obtenerTipoComidaFavorito(usuario.getId());
        List<Receta> recetasRecomendadas = servicioReceta.recomendarRecetasPorTipo(tipoComidaFavorito);

        model.addAttribute("recetasRecomendadas", recetasRecomendadas);
        model.addAttribute("tipoComida", tipoComidaFavorito);

        return "recomendacion";
    }


}
