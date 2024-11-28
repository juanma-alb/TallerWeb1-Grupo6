package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

public interface RepositorioCodigoDescuento {
    CodigoDescuento guardar(CodigoDescuento codigoDescuento);
    Optional<CodigoDescuento> buscarPorCodigoYNoUsado(String codigo);
    List<CodigoDescuento> buscarPorUsuarioId(Long usuarioId);
    void actualizar(CodigoDescuento codigoDescuento);
}


