package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.IUploadFilesService;
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
    public class ControladorCrearReceta {

        @Autowired
        private ServicioReceta servicioReceta;

        @Autowired
        private IUploadFilesService uploadFilesService;

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
    public ModelAndView crearReceta(@ModelAttribute Receta receta, 
                                    @RequestParam("file") MultipartFile file, 
                                    HttpServletRequest request) {
        Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioActual == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            // subida de archivo
            String fileUploadResult = uploadFilesService.handleFileUpload(file);
            if (fileUploadResult.startsWith("Error")) {
                ModelAndView model = new ModelAndView("crearReceta");
                model.addObject("receta", receta);
                model.addObject("error", fileUploadResult); 
                return model;
            }

            
            receta.setFoto(file.getOriginalFilename());

            
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
    }
