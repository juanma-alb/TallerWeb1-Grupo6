package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service("servicioEmail")
public class ServicioEmailImpl implements ServicioEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoActivacion(Usuario usuario) {
        String subject = "Activa tu cuenta";
        String mensaje = "Hola " + usuario.getNombre() + ",\n\n" +
                "Gracias por registrarte. Por favor, haz clic en el siguiente enlace para activar tu cuenta:\n" +
                "http://localhost:8080/activar?codigo=" + usuario.getActivationCode() + "\n\n" +
                "Saludos, Equipo de Recetas.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usuario.getEmail());
        email.setSubject(subject);
        email.setText(mensaje);
        mailSender.send(email);
    }

}