package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InteresComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo;
    private String nivel;
    private int porcentaje;

    public InteresComida(String tipo, String nivel, int porcentaje) {
        this.tipo = tipo;
        this.nivel = nivel;
        this.porcentaje = porcentaje;
    }


    // Getters
    public String getTipo() {
        return tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    // Setters
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    }
