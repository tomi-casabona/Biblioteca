package com.Tomi.Biblioteca.controllers;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Usuario;
import com.Tomi.Biblioteca.services.UsuarioServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Tomi
 */
@Controller
@RequestMapping("/registro")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping("")
    public String registro() {
        return "registro";
    }

    @PostMapping("")
    public String crearUser(RedirectAttributes redirectAttributes, Model model, @RequestParam String userName, @RequestParam String password, @RequestParam String password2) {
        try {
            usuarioServices.crearUser(userName, password, password2);
       redirectAttributes.addFlashAttribute("success", "Usuario cargado exitosamente!");
            return "redirect:/";
        } catch (ErrorWeb e) {
            model.addAttribute("error", e.getMessage());  
            model.addAttribute("userName", userName);
        return "registro";
    }
    }
     
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarUsuarios(Model model, ModelMap m){        
        try {
            model.addAttribute("usuarios", usuarioServices.findAll());
        } catch (ErrorWeb e) {
           m.put("error", e.getMessage());
                       return "usuario-list";

        }
            return "usuario-list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/vista")
    public String vistaUsuario(Model model, ModelMap m){        
       
            model.addAttribute("usuarios", usuarioServices.FindUser());            
        
        
            return "usuario-list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarUsuario(ModelMap m, Model model, @PathVariable String id) {
        
            model.addAttribute("usuario", usuarioServices.FindUser());            

            return "cambiar-contraseña";        }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/cambiarContraseña/{id}")
    public String cambiarContraseña(ModelMap m, Model model, @PathVariable String id) {
        try {
            model.addAttribute("usuario", usuarioServices.findById(id));            

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());

        } finally {
            return "cambiar-contraseña";
        }
    }
     @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/cambiarContraseña")
    public String cambiarContraseña(@RequestParam String p2, @RequestParam String p1, RedirectAttributes redirectAttributes, ModelMap m, @ModelAttribute Usuario usuario) {
        try {
            usuarioServices.modificarContraseña(usuario, p1, p2);

        } catch (ErrorWeb e) {
           redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro/vista";
        }
        redirectAttributes.addFlashAttribute("success", "Contraseña modificada exitosamente!");
        return "redirect:/registro/vista";
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar")
    public String editar(RedirectAttributes redirectAttributes, ModelMap m, @ModelAttribute Usuario usuario) {
        try {
            usuarioServices.modificarUsuario(usuario);

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "editar-usuario";
        }
        redirectAttributes.addFlashAttribute("success", "Usuario editado exitosamente!");
        return "redirect:/registro/list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(RedirectAttributes redirectAttributes, @PathVariable String id) {
        try {
           usuarioServices.borrarUser(id);            
redirectAttributes.addFlashAttribute("success", "usuario eliminado");
        } catch (ErrorWeb e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

        } finally {
            return "redirect:/registro/list";
        }
    }
}


    
