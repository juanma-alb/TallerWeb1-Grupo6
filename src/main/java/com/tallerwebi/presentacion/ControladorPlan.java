package com.tallerwebi.presentacion;


import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.*;

@Controller
public class ControladorPlan {

    @Autowired
    private ServicioPlan servicioPlan;

    @Autowired
    private ServicioUsuario servicioUsuario;

    

    @GetMapping("/planes")
    public String mostrarPlanes(Model model, HttpSession session) {
        // Obtener los planes disponibles
        model.addAttribute("planes", servicioPlan.obtenerTodosLosPlanes());
    
        // Obtener el usuario actual desde la sesión
        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");
    
        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado en la sesión.");
            return "redirect:/login";
        }
    
        // Depuración: Verificar el usuarioId
        System.out.println("Usuario actual ID: " + usuarioActual.getId());
    
        // Pasar el usuarioId al modelo
        model.addAttribute("usuarioId", usuarioActual.getId());
        return "planes";
    }
    



   @PostMapping("/planes/compra")
public String iniciarCompra(@RequestParam Long planId, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
    Plan plan = servicioPlan.obtenerPlanPorId(planId);

    if (plan == null) {
        redirectAttributes.addFlashAttribute("error", "El plan seleccionado no existe.");
        return "redirect:/planes";
    }

    // Verificación adicional del usuario
    Usuario usuario = servicioUsuario.obtenerUsuarioPorId(usuarioId);
    if (usuario == null) {
        redirectAttributes.addFlashAttribute("error", "Usuario no válido.");
        return "redirect:/planes";
    }

    return "redirect:/paypal/checkout?planId=" + planId + "&usuarioId=" + usuarioId;
}



}


