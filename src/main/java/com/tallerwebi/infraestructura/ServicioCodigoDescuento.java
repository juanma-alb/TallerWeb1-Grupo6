package com.tallerwebi.infraestructura;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.CodigoDescuento;
import com.tallerwebi.dominio.RepositorioCodigoDescuento;
import com.tallerwebi.dominio.Usuario;

@Service
public class ServicioCodigoDescuento {

    @Autowired
    private RepositorioCodigoDescuento repositorioCodigoDescuento;

    @Transactional
    public CodigoDescuento crearCodigoDescuento(Usuario usuario, BigDecimal descuento) {
        CodigoDescuento codigo = new CodigoDescuento();
        codigo.setCodigo(UUID.randomUUID().toString().substring(0, 8).toUpperCase()); // Genera un código aleatorio
        codigo.setDescuento(descuento);
        codigo.setUsuario(usuario);
        return repositorioCodigoDescuento.guardar(codigo);
    }

    @Transactional(readOnly = true)
    public Optional<CodigoDescuento> validarCodigoDescuento(String codigo) {
        return repositorioCodigoDescuento.buscarPorCodigoYNoUsado(codigo);
    }

    @Transactional
    public void marcarCodigoComoUsado(CodigoDescuento codigoDescuento) {
        codigoDescuento.setUsado(true);
        repositorioCodigoDescuento.actualizar(codigoDescuento);
    }
}

