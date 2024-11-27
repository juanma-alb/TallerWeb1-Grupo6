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

import javax.servlet.http.HttpServletRequest;

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
                      @RequestParam(required = false) Long planId, 
                      @RequestParam(required = false) Long usuarioId) {
    if (planId == null || usuarioId == null) {
        System.out.println("Error: planId o usuarioId no válidos.");
        return "redirect:/planes?error=ParametrosInvalidos";
    }

    try {
        // Ejecutar el pago en PayPal
        Payment payment = new Payment().setId(paymentId);
        PaymentExecution execution = new PaymentExecution().setPayerId(PayerID);
        payment.execute(apiContext, execution);

        // Obtener el usuario y el plan comprado
        Usuario usuario = servicioUsuario.obtenerUsuarioPorId(usuarioId);
        Plan planComprado = servicioPlan.obtenerPlanPorId(planId);

        // Actualizar el plan del usuario
        servicioPlan.actualizarPlan(usuario, planComprado);

        
        return "redirect:/planes";
    } catch (PayPalRESTException e) {
        e.printStackTrace();
        return "redirect:/planes?error=ErrorPayPal";
    }
}


}

