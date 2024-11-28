package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.ValidacionesIncorrectas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;
    private ServicioUsuario servicioUsuario;
    
    @Autowired
    private ServicioPlan servicioPlan;


    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin, ServicioUsuario servicioUsuario) {
        this.servicioLogin = servicioLogin;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {

        ModelMap model = new ModelMap();



        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("usuario", usuarioBuscado); //se guarda al usuario
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("DatosRegistro") DatosRegistro datosRegistro) {

        ModelMap model = new ModelMap();

        Usuario usuario = new Usuario();
        usuario.setEmail(datosRegistro.getEmail());
        usuario.setPassword(datosRegistro.getPassword());
        usuario.setNombre(datosRegistro.getNombre());

        usuario.setRol("USER");

        Plan planBasico = servicioPlan.obtenerPlanPorId(1L); // Método del servicio para obtener el plan con id 1
         usuario.setPlan(planBasico);

        try {
            servicioLogin.registrar(usuario,datosRegistro);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (ValidacionesIncorrectas e) {
            model.put("error", e.getMessage());
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("DatosRegistro", new DatosRegistro());
        return new ModelAndView("nuevo-usuario", model);
    }


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/activar", method = RequestMethod.GET)
    public ModelAndView activarCuenta(@RequestParam("codigo") String codigo) {
        Usuario usuario = servicioUsuario.buscarPorCodigoActivacion(codigo);
        if (usuario != null && !usuario.getActivo()) {
            usuario.setActivo(true);
            usuario.setActivationCode(null);
            servicioUsuario.guardar(usuario);
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("error", "mensaje", "Código de activación inválido o ya usado.");
        }
    }

}