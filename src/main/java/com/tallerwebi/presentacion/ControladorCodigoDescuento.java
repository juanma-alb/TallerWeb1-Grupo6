package com.tallerwebi.presentacion;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tallerwebi.dominio.CodigoDescuento;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.ServicioCodigoDescuento;

@Controller
public class ControladorCodigoDescuento {

    @Autowired
    private ServicioCodigoDescuento servicioCodigoDescuento;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/descuento/aplicar")
    public String aplicarCodigoDescuento(@RequestParam String codigo, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para usar un código de descuento.");
            return "redirect:/planes";
        }

        Optional<CodigoDescuento> codigoDescuento = servicioCodigoDescuento.validarCodigoDescuento(codigo);

        if (codigoDescuento.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El código de descuento no es válido o ya ha sido usado.");
            return "redirect:/planes";
        }

        // Aplicar descuento
        session.setAttribute("descuento", codigoDescuento.get().getDescuento());
        servicioCodigoDescuento.marcarCodigoComoUsado(codigoDescuento.get());

        redirectAttributes.addFlashAttribute("success", "El código de descuento se aplicó correctamente.");
        return "redirect:/planes";
    }
}
