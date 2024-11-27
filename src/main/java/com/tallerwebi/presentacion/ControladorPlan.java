package com.tallerwebi.presentacion;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.*;

@Controller
public class ControladorPlan {

    @Autowired
    private ServicioPlan servicioPlan;

    

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
public String iniciarCompra(@RequestParam Long planId, @RequestParam Long usuarioId) {
    // Depuración: Verificar los valores de planId y usuarioId
    System.out.println("Plan ID: " + planId);
    System.out.println("Usuario ID: " + usuarioId);

    if (servicioPlan.obtenerPlanPorId(planId) == null) {
        System.out.println("Plan no encontrado.");
        return "redirect:/planes?error=PlanNoEncontrado";
    }

    return "redirect:/paypal/checkout?planId=" + planId + "&usuarioId=" + usuarioId;
}

    

}


