package com.Tomi.Biblioteca.controllers;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Editorial;
import com.Tomi.Biblioteca.services.EditorialServices;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServices editorialServices;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String crearEditorial() {
        return "editorial-form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String guardarEditorial(RedirectAttributes redirectAttributes, ModelMap model, @RequestParam String nombre) {
        try {
            editorialServices.crearEditorial(nombre);
            redirectAttributes.addFlashAttribute("success", "Editorial cargada exitosamente!");

        } catch (ErrorWeb e) {
            model.put("error", e.getMessage());
            return "editorial-form";
        }
        return "redirect:/editorial/list";
    }

    @GetMapping("/list")
    public String listEditoriales(@RequestParam (required=false) String q, ModelMap m, Model model) {
        try {
            if (q!=null) {
                 model.addAttribute("editoriales", editorialServices.findByQ(q));
            }else{
            model.addAttribute("editoriales", editorialServices.mostrarEditoriales());
            }
        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "editorial-list";
        }
        return "editorial-list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarEditorial(ModelMap m, Model model, @PathVariable String id) {
        try {
            model.addAttribute("editorial", editorialServices.findById(id));

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());

        } finally {
            return "editar-editorial";

        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar")
    public String editarEditorial(RedirectAttributes redirectAttributes, ModelMap m, @ModelAttribute Editorial editorial) {
        try {
            editorialServices.modificarEditorial(editorial);
            redirectAttributes.addFlashAttribute("success", "Editorial modificada exitosamente!");

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "editar-editorial";
        }
        return "redirect:/editorial/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{nombre}")
    public String eliminarEditorial(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String nombre) {
        try {
            editorialServices.borrarEditorial(nombre);
            redirectAttributes.addFlashAttribute("success", "Editorial eliminada exitosamente!");

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
        } finally {
            return "redirect:/editorial/list";
        }
    }

}
