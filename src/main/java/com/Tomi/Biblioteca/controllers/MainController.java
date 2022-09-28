

package com.Tomi.Biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Tomi
 */
@Controller
@RequestMapping()
public class MainController {
    
    @GetMapping("/")
    public String index( ){        
    return "index";
    }

}
