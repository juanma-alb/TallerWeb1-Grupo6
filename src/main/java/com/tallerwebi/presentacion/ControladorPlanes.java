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
        // Lógica para obtener la URL de Mercado Pago según el plan seleccionado
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
                    return "planes"; // Redirige a la misma página de planes si el plan es inválido
            }

            // Redirige al botón de pago de Mercado Pago
            return "redirect:" + initPoint;

        } catch (Exception e) {
            // En caso de error, agrega un mensaje al modelo y vuelve a la página de planes
            model.addAttribute("error", "Ocurrió un problema al procesar tu compra. Inténtalo nuevamente.");
            return "planes";
        }
    }

    // Método para generar la URL de Mercado Pago
    private String generarUrlMercadoPago(String titulo, double precio, String descripcion) {
        // Aquí se implementa la lógica de integración con Mercado Pago
        // Puedes usar el SDK de Mercado Pago para generar la URL de pago
        // Esto es un ejemplo simplificado
        String urlPrueba = "https://www.mercadopago.com.ar/init-point-mock";
        return urlPrueba + "?title=" + titulo + "&price=" + precio + "&description=" + descripcion;
    }

     @GetMapping
    public String mostrarPlanes(Model model) {
        // Muestra la página de planes
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

