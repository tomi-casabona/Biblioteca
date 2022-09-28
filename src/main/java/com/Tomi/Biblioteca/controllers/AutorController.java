

package com.Tomi.Biblioteca.controllers;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Autor;
import com.Tomi.Biblioteca.services.AutorServices;
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
@RequestMapping("/autor")
public class AutorController {
    
    @Autowired
    private AutorServices autorServices;
    
    @GetMapping("/list")
    public String listAutor(@RequestParam (required = false) String q, ModelMap m, Model model) {
        try {
            if (q!=null) {
                 model.addAttribute("autores", autorServices.findByQ(q));
            }else{
            model.addAttribute("autores", autorServices.mostrarAutores());
            }
        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "autor-list";
        }
        return "autor-list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String crearAutor(){      
    return "autor-form";        
    }
      
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String crearAutor(RedirectAttributes redirectAttributes,ModelMap model, @RequestParam String nombre)  {
        try {
             autorServices.crearAutor(nombre);
                         redirectAttributes.addFlashAttribute("success", "Autor cargada exitosamente!");

        } catch (ErrorWeb e) {
            model.put("error", e.getMessage());
            return "autor-form";
        }
       
    return "redirect:/autor/list";        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable String id, ModelMap model) throws ErrorWeb{
        model.addAttribute("autor", autorServices.mostrarAutor(id));
        return "editar-autor";    
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar")
    public String editarAutor(RedirectAttributes redirectAttributes,ModelMap model, @ModelAttribute Autor autor) throws ErrorWeb{
        try {
            autorServices.modificarAutor(autor);
                                     redirectAttributes.addFlashAttribute("success", "Autor editado exitosamente!");

        } catch (ErrorWeb e) {
            model.put("error", e.getMessage());
            return "editar-autor";
        }
        
    return "redirect:/autor/list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(RedirectAttributes redirectAttributes,@PathVariable String id) throws ErrorWeb{
        autorServices.borrarAutor(id);
                                             redirectAttributes.addFlashAttribute("success", "Autor eliminado exitosamente!");

        return "redirect:/autor/list";
    }

}
