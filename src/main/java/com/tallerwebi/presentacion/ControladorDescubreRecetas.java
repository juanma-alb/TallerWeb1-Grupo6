package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioDescubreRecetas;
import com.tallerwebi.dominio.ServicioFavorito;
import com.tallerwebi.dominio.ServicioUsuario;

@Controller
public class ControladorDescubreRecetas {

    @Autowired
    private ServicioDescubreRecetas servicioDescubreRecetas;

    @Autowired
    private ServicioUsuario servicioUsuario;

     @Autowired
    private ServicioFavorito servicioFavorito;

    @Autowired
    public ControladorDescubreRecetas(ServicioDescubreRecetas servicioDescubreRecetas, ServicioUsuario servicioUsuario) {
        this.servicioDescubreRecetas = servicioDescubreRecetas;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(path = "/descubre-recetas", method = RequestMethod.GET)
    public ModelAndView irADescubreRecetas(HttpSession session) {
        List<Receta> recetas = servicioDescubreRecetas.obtenerRecetasParaCarrusel();
        
        ModelAndView modelAndView = new ModelAndView("descubre-recetas");
        modelAndView.addObject("recetas", recetas);
        modelAndView.addObject("categoria", ""); 
        modelAndView.addObject("subcategoria", ""); 
        
        
        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

        System.out.println("Método irADescubreRecetas ejecutado.");
        if (usuarioActual != null) {
        System.out.println("Rol del usuario actual: " + usuarioActual.getRol());
        modelAndView.addObject("esAdmin", usuarioActual.getRol().equals("ADMIN"));
        } else {
        System.out.println("No hay usuario actual en la sesión.");
        modelAndView.addObject("esAdmin", false);
    }

        return modelAndView;
    }

    //filtrar recetas
    @RequestMapping(path = "/filtrar-recetas", method = RequestMethod.GET)
    public ModelAndView filtrarRecetas(@RequestParam(value = "categoria", required = false) String categoria, 
                                       @RequestParam(value = "subcategoria", required = false) String subcategoria) {
        List<Receta> recetasFiltradas = servicioDescubreRecetas.filtrarPorCategoria(categoria, subcategoria);
        
        ModelAndView modelAndView = new ModelAndView("filtrar-recetas"); 
        modelAndView.addObject("recetas", recetasFiltradas);
        modelAndView.addObject("categoria", categoria); 
        modelAndView.addObject("subcategoria", subcategoria); 
        return modelAndView;
    }

    // redirigir a la vista de edicion de receta
    @RequestMapping(path = "/editar-receta-admin/{id}", method = RequestMethod.GET)
    public ModelAndView editarRecetaAdmin(@PathVariable("id") Long id, HttpSession session) {
        Usuario usuarioActual = obtenerUsuarioActual(session);
        Receta receta = servicioDescubreRecetas.obtenerRecetaPorId(id);

        if (usuarioActual == null || (!usuarioActual.getRol().equals("ADMIN") && !receta.getUsuario().getId().equals(usuarioActual.getId()))) {
            
            return new ModelAndView("redirect:/acceso-denegado");
        }

        ModelAndView modelAndView = new ModelAndView("editar-receta-admin");
        modelAndView.addObject("receta", receta);
        return modelAndView;
    }

    //guardar los cambios de la receta editada
    @RequestMapping(value = "/guardar-receta-admin", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String actualizarReceta(@ModelAttribute Receta receta, HttpSession session) {
        Usuario usuarioActual = obtenerUsuarioActual(session);
        Receta recetaExistente = servicioDescubreRecetas.obtenerRecetaPorId(receta.getId());

        if (usuarioActual == null || (!usuarioActual.getRol().equals("ADMIN") && !recetaExistente.getUsuario().getId().equals(usuarioActual.getId()))) {
            return "redirect:/acceso-denegado";
        }

        servicioDescubreRecetas.actualizarReceta(receta);
        return "redirect:/descubre-recetas";
    }

    //eliminar una receta
    @RequestMapping(path = "/eliminarRecetaAdmin/{id}", method = RequestMethod.POST)
    public String eliminarReceta(@PathVariable("id") Long id, HttpSession session) {
        Usuario usuarioActual = obtenerUsuarioActual(session);
        Receta receta = servicioDescubreRecetas.obtenerRecetaPorId(id);

        if (usuarioActual != null && (usuarioActual.getRol().equals("ADMIN") || receta.getUsuario().getId().equals(usuarioActual.getId()))) {
            servicioDescubreRecetas.eliminarReceta(id);
        } else {
            return "redirect:/acceso-denegado";  
        }

        return "redirect:/descubre-recetas";
    }

    //obtener el usuario actual de la sesion (no esta listo todavia)
    private Usuario obtenerUsuarioActual(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }

    
    private boolean tienePermisoParaModificarOEliminar(Usuario usuario, Receta receta) {
        return usuario != null && (usuario.getRol().equals("ADMIN") || receta.getUsuario().getId().equals(usuario.getId()));
    }



    @RequestMapping(path = "/favorito/agregar/{recetaId}", method = RequestMethod.POST)
    public String agregarAFavoritos(@PathVariable Long recetaId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Receta receta = servicioDescubreRecetas.obtenerRecetaPorId(recetaId);

        if (usuario != null && receta != null) {
            servicioFavorito.agregarAFavoritos(usuario, receta);
        }

        return "redirect:/descubre-recetas";
    }

    @RequestMapping(path = "/favorito/eliminar/{recetaId}", method = RequestMethod.POST)
    public String eliminarDeFavoritos(@PathVariable Long recetaId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            servicioFavorito.eliminarDeFavoritos(usuario.getId(), recetaId);
        }

        return "redirect:/descubre-recetas";
    }
}