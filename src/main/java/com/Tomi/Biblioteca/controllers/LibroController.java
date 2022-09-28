package com.Tomi.Biblioteca.controllers;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Autor;
import com.Tomi.Biblioteca.entities.Libro;
import com.Tomi.Biblioteca.services.AutorServices;
import com.Tomi.Biblioteca.services.EditorialServices;
import com.Tomi.Biblioteca.services.LibroServices;
import com.Tomi.Biblioteca.services.UsuarioServices;
import java.util.Optional;
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
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServices libroServices;
    @Autowired
    private AutorServices autorServices;
    @Autowired
    private EditorialServices editorialServices;
    @Autowired
    private UsuarioServices usuarioServices;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String crearLibro(Model model) throws ErrorWeb {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autorServices.mostrarAutores());
        model.addAttribute("editoriales", editorialServices.mostrarEditoriales());
        return "libro-form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String crearLibro(RedirectAttributes redirectAttributes, ModelMap m, Model model, @ModelAttribute Libro libro) throws ErrorWeb {
        try {
            libroServices.cargarLibro(libro);

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            model.addAttribute("autores", autorServices.mostrarAutores());
            model.addAttribute("editoriales", editorialServices.mostrarEditoriales());
            return "libro-form";
        }
        redirectAttributes.addFlashAttribute("success", "Libro cargado exitosamente!");

        return "redirect:/libro/list";
    }

    @GetMapping("/list")
    public String listarLibros(@RequestParam(required = false) String q, ModelMap m, Model model) {
        try {
            if (q != null) {
                model.addAttribute("libros", libroServices.listbyQ(q));
            } else {
                model.addAttribute("libros", libroServices.listarLibros());
            }
        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "libro-list";
        }
        return "libro-list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarLibro(ModelMap m, Model model, @PathVariable String id) {
        try {
            model.addAttribute("libro", libroServices.findById(id));
            model.addAttribute("autores", autorServices.mostrarAutores());
            model.addAttribute("editoriales", editorialServices.mostrarEditoriales());

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());

        } finally {
            return "editar-libro";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar")
    public String editar(RedirectAttributes redirectAttributes, ModelMap m, @ModelAttribute Libro libro) {
        try {
            libroServices.modificarLibro(libro);

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
            return "editar-libro";
        }
        redirectAttributes.addFlashAttribute("success", "Libro editado exitosamente!");
        return "redirect:/libro/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String id) {
        try {
            libroServices.borrarLibro(id);
            redirectAttributes.addFlashAttribute("success", "Libro eliminado exitosamente!");

        } catch (ErrorWeb e) {
            m.put("error", e.getMessage());
        } finally {
            return "redirect:/libro/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/prestar/{id}")
    public String prestarLibro(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String id) {
        try {
            usuarioServices.guardarLibro(id);
            libroServices.prestarLibro(id);
            redirectAttributes.addFlashAttribute("success", "Libro: " + libroServices.findById(id).get().getTitulo() + " prestado exitosamente!");

        } catch (ErrorWeb e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } finally {
            return "redirect:/libro/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/devolver/{id}")
    public String devolverLibro(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String id) {
        try {
            usuarioServices.devolverLibro(id);
            redirectAttributes.addFlashAttribute("success", "Libro: " + libroServices.findById(id).get().getTitulo() + " devuelto exitosamente!");

        } catch (ErrorWeb e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

        } finally {
            return "redirect:/libro/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/alta/{nombre}")
    public String altaLibro(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String nombre) {
        try {
            libroServices.altaLibro(nombre);
            redirectAttributes.addFlashAttribute("success", "Libro: " + libroServices.mostrarLibro(nombre).getTitulo() + "dado de alta exitosamente!");

        } catch (ErrorWeb e) {
           redirectAttributes.addFlashAttribute("error", e.getMessage());

        } finally {
            return "redirect:/libro/list";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/baja/{nombre}")
    public String bajaLibro(RedirectAttributes redirectAttributes, ModelMap m, @PathVariable String nombre) {
        try {
            libroServices.bajaLibro(nombre);
            redirectAttributes.addFlashAttribute("success", "Libro: " + libroServices.mostrarLibro(nombre).getTitulo() + "dado de baja exitosamente");
        } catch (Exception e) {
           redirectAttributes.addFlashAttribute("error", e.getMessage());
        } finally {
            return "redirect:/libro/list";
        }
    }

}
