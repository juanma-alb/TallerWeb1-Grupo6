package com.tallerwebi.presentacion;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.dominio.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List; 

@Controller
public class PayPalController {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private ServicioPlan servicioPlan;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioCodigoDescuento servicioCodigoDescuento;

    @GetMapping("/paypal/checkout")
public String checkout(@RequestParam Long planId, 
                       @RequestParam Long usuarioId, 
                       HttpServletRequest request, 
                       HttpSession session, 
                       RedirectAttributes redirectAttributes) {
    Plan plan = servicioPlan.obtenerPlanPorId(planId);

    // Determinar el precio base según el plan
    BigDecimal precioBase = BigDecimal.valueOf(10.00); // Default: Premium
    if (planId == 2) {
        precioBase = BigDecimal.valueOf(5.00); // Plan Avanzado
    } else if (planId == 1) {
        redirectAttributes.addFlashAttribute("error", "El plan básico no se puede comprar.");
        return "redirect:/planes";
    }

    // Verificar si hay un descuento aplicado
    BigDecimal descuento = BigDecimal.ZERO;
    if (session.getAttribute("descuento") != null) {
        descuento = (BigDecimal) session.getAttribute("descuento");
    }

    // Calcular el precio con descuento
    BigDecimal precioConDescuento = precioBase.subtract(precioBase.multiply(descuento).divide(BigDecimal.valueOf(100)));

    // Asegurar que el precio no sea negativo
    if (precioConDescuento.compareTo(BigDecimal.ZERO) < 0) {
        precioConDescuento = BigDecimal.ZERO;
    }

    // Configurar el monto para la transacción de PayPal
    Amount amount = new Amount();
    amount.setCurrency("USD");
    amount.setTotal(precioConDescuento.setScale(2, RoundingMode.HALF_UP).toString()); // Convertir a string con 2 decimales

    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setDescription("Compra de Plan: " + plan.getTipoPlan().getNombre());

    Payment payment = new Payment();
    payment.setIntent("sale");
    payment.setPayer(new Payer().setPaymentMethod("paypal"));
    payment.setTransactions(Collections.singletonList(transaction));

    String baseUrl = request.getRequestURL().toString().replace("/paypal/checkout", "");
    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl(baseUrl + "/paypal/cancel");
    redirectUrls.setReturnUrl(baseUrl + "/paypal/success?planId=" + planId + "&usuarioId=" + usuarioId);
    payment.setRedirectUrls(redirectUrls);

    try {
        Payment createdPayment = payment.create(apiContext);
        for (Links link : createdPayment.getLinks()) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                return "redirect:" + link.getHref();
            }
        }
    } catch (PayPalRESTException e) {
        e.printStackTrace();
    }

    return "redirect:/planes";
}



@GetMapping("/paypal/success")
public String success(@RequestParam String paymentId,
                      @RequestParam String PayerID,
                      @RequestParam Long planId,
                      @RequestParam Long usuarioId,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) {
    try {
        Payment payment = new Payment().setId(paymentId);
        PaymentExecution execution = new PaymentExecution().setPayerId(PayerID);
        Payment executedPayment = payment.execute(apiContext, execution);

        if (!"approved".equals(executedPayment.getState())) {
            redirectAttributes.addFlashAttribute("error", "El pago no fue aprobado.");
            return "redirect:/planes";
        }

        // Actualizar el plan del usuario
        Usuario usuario = servicioUsuario.obtenerUsuarioPorId(usuarioId);
        Plan planComprado = servicioPlan.obtenerPlanPorId(planId);

        if (usuario == null || planComprado == null) {
            redirectAttributes.addFlashAttribute("error", "Datos no válidos para la compra.");
            return "redirect:/planes";
        }

        servicioPlan.actualizarPlan(usuario, planComprado);
        session.setAttribute("usuario", usuario);

        // Generar un código de descuento
        CodigoDescuento codigoDescuento = servicioCodigoDescuento.crearCodigoDescuento(usuario, BigDecimal.valueOf(25.0)); // 25% descuento

        // Notificar al usuario
        String mensajeExito = String.format("¡Felicidades! Has comprado el plan %s. Durante 30 días, ahora podés %s. "
                                            + "¡Gracias por tu compra! Tu código de descuento para tu próxima compra es: %s",
                                            planComprado.getTipoPlan().getNombre(),
                                            planComprado.getTipoPlan().getDescripcion(),
                                            codigoDescuento.getCodigo());
        redirectAttributes.addFlashAttribute("success", mensajeExito);

        return "redirect:/planes";
    } catch (PayPalRESTException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Hubo un problema al procesar el pago.");
        return "redirect:/planes";
    }
}

}
