package com.tallerwebi.presentacion;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.tallerwebi.infraestructura.ServicioPlan;
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

import java.util.Collections;
import java.util.List; // Importación necesaria para manejar listas

@Controller
public class PayPalController {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private ServicioPlan servicioPlan;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/paypal/checkout")
public String checkout(@RequestParam Long planId, @RequestParam Long usuarioId, HttpServletRequest request) {
    Plan plan = servicioPlan.obtenerPlanPorId(planId);

    // Determinar el precio según el plan
    String precio = "10.00"; // Default: Premium
    if (planId == 2) {
        precio = "5.00"; // Plan Avanzado
    } else if (planId == 1) {
        return "redirect:/planes?error=PlanBasicoNoSeCompra";
    }

    Amount amount = new Amount();
    amount.setCurrency("USD");
    amount.setTotal(precio);

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

        String mensajeExito = String.format("Felicidades, acabas de comprar el plan %s. Durante 30 días, ahora podés %s. ¡Gracias por tu compra!",
                                            planComprado.getTipoPlan().getNombre(),
                                            planComprado.getTipoPlan().getDescripcion());

        redirectAttributes.addFlashAttribute("success", mensajeExito);
        return "redirect:/planes";
    } catch (PayPalRESTException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Hubo un problema al procesar el pago.");
        return "redirect:/planes";
    }
}
}
