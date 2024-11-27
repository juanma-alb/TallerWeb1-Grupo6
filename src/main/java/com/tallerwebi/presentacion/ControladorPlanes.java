package com.tallerwebi.presentacion;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;


@Controller

@RequestMapping("/planes")
public class ControladorPlanes {

 @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping
    public String procesarCompra(@RequestParam("plan") String planSeleccionado, Model model) {
        String initPoint;
        try {
            switch (planSeleccionado) {
                case "mensual":
                    initPoint = generarUrlMercadoPago("Plan Mensual", 1255.00, "1 mes de servicio premium");
                    break;
                case "anual":
                    initPoint = generarUrlMercadoPago("Plan Anual", 13500.00, "1 año de servicio premium con 10% de descuento");
                    break;
                case "premium":
                    initPoint = generarUrlMercadoPago("Plan Premium", 1500.00, "Acceso sin límites a todas las funcionalidades");
                    break;
                default:
                    model.addAttribute("error", "Plan no válido. Intenta nuevamente.");
                    return "planes";
            }

            return "redirect:" + initPoint;

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un problema al procesar tu compra. Inténtalo nuevamente.");
            return "planes";
        }
    }

    private String generarUrlMercadoPago(String titulo, double precio, String descripcion) {
        String urlPrueba = "https://www.mercadopago.com.ar/init-point-mock";
        return urlPrueba + "?title=" + titulo + "&price=" + precio + "&description=" + descripcion;
    }

     @GetMapping
    public String mostrarPlanes(Model model) {
        return "planes";
    }

    @PostMapping("/adquirir-plan")
    public String adquirirPlan(@RequestParam Long idPlan, HttpServletRequest request) {
        System.out.println("Solicitud para adquirir plan con ID: " + idPlan);

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getEmail());

            Plan plan = servicioUsuario.obtenerPlanPorId(idPlan);
            System.out.println("Plan obtenido: " + plan.getNombrePlan());

            usuario.setPlan(plan);
            servicioUsuario.actualizarUsuario(usuario);
            request.getSession().setAttribute("usuario", usuario);

            System.out.println("Plan asignado exitosamente al usuario.");
        } else {
            System.out.println("No se encontró un usuario en la sesión.");
        }
        return "redirect:/planes";
    }
    

}

