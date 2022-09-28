

package com.Tomi.Biblioteca.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ErrorsController implements ErrorController {
    
    @RequestMapping(value = "/errore", method = {RequestMethod.GET, RequestMethod.POST })
    public String mostrarError(Model model, HttpServletRequest httpServletRequest){
    String mensajeError = "";
    int codigoError = (int)httpServletRequest.getAttribute("javax.servlet.error.status_code");
    
    switch(codigoError){
        case 400:
            mensajeError="El recurso solicitado no existe";
            break;
        case 401:
            mensajeError="No se encuentra autorizado, debe registrarse";
            break;
        case 403:
            mensajeError="No tiene permisos suficientes para acceder al recurso";
            break;
        case 404:
            mensajeError="El recurso solicitado no se ha encontrado";
            break;
        case 500:
            mensajeError="El servidor no pudo procesar la solicitud con exito";
            break;
        default:
            mensajeError="";
            break;
    }
    model.addAttribute("mensaje", mensajeError);
    model.addAttribute("codigo",codigoError);
    
    return "error";
    }
    

}
