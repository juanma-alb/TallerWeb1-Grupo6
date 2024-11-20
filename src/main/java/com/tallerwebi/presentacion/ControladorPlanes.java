package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/planes")
public class ControladorPlanes {



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
}

