package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ControladorMercadoPago {


    @GetMapping("/comprar")
    public ResponseEntity<String> comprar(@RequestParam String suscripcion, @RequestParam String suscripcionValor) {
        // Establecer el Access Token para la API de Mercado Pago
        MercadoPagoConfig.setAccessToken("APP_USR-7544106560468957-060313-b384a8aa74089431a6bfe64a7417d696-1811981688");

        // Convertgit ir el valor de la suscripción a BigDecimal
        BigDecimal precioReal = new BigDecimal(suscripcionValor);

        // Crear el ítem del producto (en este caso, el Plan Mensual)
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id(suscripcion)
                .title("Plan Mensual")
                .quantity(1)
                .currencyId("USD")
                .unitPrice(precioReal)
                .build();

        // Agregar los ítems al listado de items
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        // Definir las URLs de retorno después del pago (success, failure, pending)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/spring/planes") // URL de éxito
                .failure("http://localhost:8080/spring/planes") // URL de fallo
                .pending("http://localhost:8080/spring/planes") // URL de pendiente
                .build();

        // Crear la preferencia de pago
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved") // Redirección automática si el pago es aprobado
                .externalReference(suscripcionValor) // Guardar el valor de la suscripción como referencia
                .build();

        // Crear el cliente para la preferencia
        PreferenceClient client = new PreferenceClient();

        try {
            // Crear la preferencia de pago
            Preference preference = client.create(preferenceRequest);

            // Redirigir al usuario a la URL de pago (init point)
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(preference.getSandboxInitPoint()));  // Usar InitPoint del Sandbox
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } catch (MPException | MPApiException e) {
            // Manejar errores de Mercado Pago
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/ingresarSaldo")
    public ResponseEntity<String> ingresarSaldo(HttpServletRequest request, @RequestParam String ingresarSaldo, @RequestParam String valorSaldoIngresado) {
        // Configura el token de acceso de Mercado Pago
        MercadoPagoConfig.setAccessToken("TEST-7023725150442190-111916-fe43502e77285e8ff48c7ed002614b00-670209933");

        BigDecimal precioReal;
        try {
            precioReal = new BigDecimal(valorSaldoIngresado);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Formato de saldo ingresado inválido");
        }

        // Guarda el valor del saldo en la sesión
        request.getSession().setAttribute("valorSaldoIngresado", precioReal);

        // Crea el producto
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id(ingresarSaldo)
                .title("Cargar saldo")
                .quantity(1)
                .currencyId("USD")
                .unitPrice(precioReal)
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        // Configura las URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/spring/procesarRespuestaDeIngresarSaldo")
                .failure("http://localhost:8080/spring/procesarRespuestaDeIngresarSaldo")
                .pending("http://localhost:8080/spring/procesarRespuestaDeIngresarSaldo")
                .build();

        // Crea la preferencia de pago
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(valorSaldoIngresado) // Guarda el saldo como referencia externa
                .build();


        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            return ResponseEntity.ok(preference.getInitPoint());
        } catch (MPException | MPApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("/error");
        }
    }
}
