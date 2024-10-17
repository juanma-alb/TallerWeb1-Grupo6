package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/*
import com.tallerwebi.infraestructura.ServicioRecetaImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import java.util.stream.Collectors;
import java.security.Principal;
import java.time.LocalDateTime;
 */

@Controller
public class ControladorMisRecetas {

    @Autowired
    private ServicioReceta servicioReceta;

    @Autowired
    private ServicioComentario servicioComentario;

    @GetMapping("/misRecetas")
    public String listarRecetas(HttpServletRequest request,
                                @RequestParam(value = "filtro", required = false) String filtro,
                                Model model) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
        List<Receta> recetas;

        if (usuarioActual == null) {
            return "redirect:/login";
        }

        if (filtro != null && !filtro.isEmpty()) {
            recetas = servicioReceta.buscarRecetasPorNombreRecetas(filtro);
        } else {
            recetas = servicioReceta.listarRecetasPorUsuario(usuarioActual.getId());
        }

        model.addAttribute("recetas", recetas);
        return "misRecetas";
    }

    @PostMapping("/mis-recetas/comentario")
    public String agregarComentario(@RequestParam Long recetaId, @RequestParam String contenido, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        servicioComentario.agregarComentario(recetaId, usuario.getId(), contenido);
        return "redirect:/misRecetas";
    }



// ver una receta
@GetMapping("/{id}")
public String verReceta(@PathVariable("id") Long id, Model model) {
    Receta receta = servicioReceta.buscarRecetaPorId(id);
    if (receta != null) {
        model.addAttribute("receta", receta);
        return "ver-receta";
    } else {
        return "redirect:/login";
    }
}

// mostrar el form para editar una receta + verificacion para que el propio usuario pueda hacerlo
@GetMapping("/editar-receta-user/{id}")
public String mostrarFormularioEditarReceta(@PathVariable Long id, Model model, HttpServletRequest request) {
    Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

    Receta receta = servicioReceta.buscarRecetaPorId(id);

    if (usuarioActual == null || receta == null || !receta.getUsuario().getId().equals(usuarioActual.getId())) {
        return "redirect:/login"; 
    }

    model.addAttribute("receta", receta);
    return "editar-receta-user"; 
}


@PostMapping("/editar-receta-user/{id}")
public String editarRecetaUser(@PathVariable Long id, 
                       @ModelAttribute Receta recetaActualizada,  
                       HttpServletRequest request) {
Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

Receta receta = servicioReceta.buscarRecetaPorId(id);

if (usuarioActual == null || receta == null || !receta.getUsuario().getId().equals(usuarioActual.getId())) {
    return "redirect:/login";
}


receta.setNombre(recetaActualizada.getNombre());
receta.setCategoria(recetaActualizada.getCategoria());
receta.setSubcategoria(recetaActualizada.getSubcategoria());
receta.setCalorias(recetaActualizada.getCalorias());
receta.setTiempoPreparacion(recetaActualizada.getTiempoPreparacion());
receta.setDescripcion(recetaActualizada.getDescripcion());
receta.setComensales(recetaActualizada.getComensales());
receta.setContenido(recetaActualizada.getContenido());


servicioReceta.actualizarReceta(receta);

return "redirect:/misRecetas"; 
}


@PostMapping("/eliminarRecetaUser/{id}")
public String eliminarRecetaUser(@PathVariable Long id, HttpServletRequest request) {
    Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

    Receta receta = servicioReceta.buscarRecetaPorId(id);

    if (usuarioActual == null || receta == null || !receta.getUsuario().getId().equals(usuarioActual.getId())) {
        return "redirect:/login";
    }

    servicioReceta.eliminarReceta(id);
    return "redirect:/misRecetas"; 
}
}


