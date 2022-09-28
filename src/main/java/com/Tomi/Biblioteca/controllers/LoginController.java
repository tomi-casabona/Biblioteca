

package com.Tomi.Biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tomi
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping("")
    public String login(Model model, @RequestParam (required = false) String error,  @RequestParam (required = false) String username,
     @RequestParam (required = false) String logout,  @RequestParam (required = false) String success){
        if (error != null) {
            model.addAttribute("error", "el usuario o la contraseña son incorrectos");
                    }
        if (username!= null) {
                        model.addAttribute("username", username);

        }
        if (logout!= null) {
                        model.addAttribute("logout", "Logout exitoso!");

        }
        if (success!= null) {
                        model.addAttribute("success", "Login exitoso!");
return "/index";
        }
    return "login";
            }
    

}
