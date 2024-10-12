package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption; */

import javax.servlet.http.HttpServletRequest;



@Controller
public class ControladorReceta {

    @Autowired
    private ServicioReceta servicioReceta;

    // Mostrar el formulario de crear receta
    @GetMapping("/crear-receta")
    public ModelAndView mostrarFormularioCrearReceta(HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("crearReceta");
        modelAndView.addObject("receta", new Receta());
        return modelAndView;
    }

    // Crear una receta
    @PostMapping("/crear-receta")
    public ModelAndView crearReceta(@ModelAttribute Receta receta, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            receta.setUsuario(usuarioActual);
            servicioReceta.crearReceta(receta);
            return new ModelAndView("redirect:/misRecetas");

        } catch (Exception e) {
            ModelAndView model = new ModelAndView("crearReceta");
            model.addObject("receta", receta);
            model.addObject("error", "Error al crear la receta: " + e.getMessage());
            e.printStackTrace();
            return model;
        }
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
    @GetMapping("/editarReceta/{id}")
    public String mostrarFormularioEditarReceta(@PathVariable Long id, Model model, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        Receta receta = servicioReceta.buscarRecetaPorId(id);

        if (usuarioActual == null || receta == null || !receta.getUsuario().getId().equals(usuarioActual.getId())) {
            return "redirect:/login"; 
        }

        model.addAttribute("receta", receta);
        return "editarReceta"; 
    }

    
    @PostMapping("/editarReceta/{id}")
public String editarReceta(@PathVariable Long id, 
                           @ModelAttribute Receta recetaActualizada, 
                           /*@RequestParam("foto") MultipartFile foto,*/ 
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

    /* 
    if (!foto.isEmpty()) {
        try {
            
            
            String rutaDirectorioImagenes = "/spring/imagenes/slides"; 
            String nombreArchivo = foto.getOriginalFilename();
            Path rutaCompleta = Paths.get(rutaDirectorioImagenes, nombreArchivo);
            
            
            Files.copy(foto.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
            
            
            receta.setFoto(nombreArchivo); 

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error"; 
        }
    } */

    servicioReceta.actualizarReceta(receta);

    return "redirect:/misRecetas"; 
}

    
    @PostMapping("/eliminarReceta/{id}")
    public String eliminarReceta(@PathVariable Long id, HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        Receta receta = servicioReceta.buscarRecetaPorId(id);

        if (usuarioActual == null || receta == null || !receta.getUsuario().getId().equals(usuarioActual.getId())) {
            return "redirect:/login";
        }

        servicioReceta.eliminarReceta(id);
        return "redirect:/misRecetas"; 
    }

}
