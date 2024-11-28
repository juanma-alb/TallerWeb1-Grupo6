package com.tallerwebi.dominio;

import org.springframework.beans.MutablePropertyValues;

import javax.persistence.*;
import java.util.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private String nombre;
    private String descripcion;
    private String ciudad;
    private String foto;
    private String activationCode;

    // private boolean puedeEditarPerfil;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    // private boolean primerIngreso = true;

    /*
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Receta> recetas;
    */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Receta> recetas = new HashSet<>();


    @Transient
    private String confirmPassword;

    private String currentPassword;

    private String newPassword; // Nueva contraseña (para actualización)

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Receta> recetasFavoritas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "usuarios_recetas_guardadas",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "receta_id")
    )
    private List<Receta> recetasGuardadas = new ArrayList<>();

    @ElementCollection
    private Map<String, Integer> contadorPorTipoComida = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "usuarios_seguidos",
            joinColumns = @JoinColumn(name = "seguidor_id"),
            inverseJoinColumns = @JoinColumn(name = "seguido_id")
    )
    private List<Usuario> seguidos = new ArrayList<>();

    @ManyToMany(mappedBy = "seguidos")
    private List<Usuario> seguidores = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InteresComida> interesComidas = new HashSet<>();


    // public boolean isPrimerIngreso() {
    //    return primerIngreso;
    // }

    //public void setPrimerIngreso(boolean primerIngreso) {
    //   this.primerIngreso = primerIngreso;
    // }

    public Set<InteresComida> getInteresComidas() {
        return interesComidas;
    }

    public void setInteresComidas(Set<InteresComida> interesComidas) {
        this.interesComidas = interesComidas;
    }


    public Map<String, Integer> getContadorPorTipoComida() {
        return contadorPorTipoComida;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Set<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(Set<Receta> recetas) {
        this.recetas = recetas;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setComentarios(Set<Comentario>  comentarios) {
        this.comentarios = comentarios;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public List<Receta> getRecetasGuardadas() {
        return recetasGuardadas;
    }

    public void setRecetasGuardadas(List<Receta> recetasGuardadas) {
        this.recetasGuardadas = recetasGuardadas;
    }

    public List<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(List<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String obtenerTipoComidaFavorito() {
        return contadorPorTipoComida.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public void seguir(Usuario usuario) {
        seguidos.add(usuario);
        usuario.getSeguidores().add(this);
    }

    public void dejarDeSeguir(Usuario usuario) {
        seguidos.remove(usuario);
        usuario.getSeguidores().remove(this);
    }


    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
