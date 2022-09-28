package com.Tomi.Biblioteca.services;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Autor;
import com.Tomi.Biblioteca.repositories.AutorRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomi
 */
@Service
public class AutorServices {

    // @Autowired
    //private Autor autor;
    @Autowired
    private AutorRepository autorRepository;

    // Instancia un nuevo objeto Autor y lo guarda en la base de datos
    @Transactional
    public Autor crearAutor(String autor) throws ErrorWeb {

        try {

            if (autorRepository.findByNombre(autor) != null) {
                throw new ErrorWeb("El nombre de autor ya existe!");
            }
            if (autor.isEmpty()) {
                throw new ErrorWeb("El nombre del autor es obligatorio!");
            }
            if (autor.startsWith(" ")) {
                throw new ErrorWeb("El nombre del autor no puede comenzar con espacio!");
            }

        } catch (ErrorWeb e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error del sistema");
        }
        Autor a = new Autor();
        a.setNombre(autor);
        return autorRepository.save(a);
    }

    //Busca un Autor por id y lo elimina
    @Transactional
    public void borrarAutor(String id) throws ErrorWeb {
        try {
            if (id == null) {
                throw new ErrorWeb("El id es obligatorio");
            }
             if (autorRepository.findById(id).get() == null) {
                throw new ErrorWeb("El id no corresponde a un autor");
            }
          
        } catch (ErrorWeb e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();        
            throw new ErrorWeb("Error del sistema");
        }
      autorRepository.delete(autorRepository.findById(id).get());
    }
    
        
        // Busca un autor por id en la database y lo devuelve
    public Autor mostrarAutor(String id) throws ErrorWeb {
        try {
            if (id==null) {
                throw new ErrorWeb("El id no puede ser nulo");
                 }
            if (autorRepository.findById(id).get() == null) {
                throw new ErrorWeb("No se encontro autor con ese id");
            }
            
            
        } catch (ErrorWeb e) {
            throw e;        
         } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error de Sistema");
        }
        return autorRepository.findById(id).get();
    }

    // Busca todos los autores y los devuelve
    public List<Autor> mostrarAutores() {
       
        return autorRepository.findAll();
    }
    // Busca todos los autores  que coincidan con la q y los devuelve
    public List<Autor> findByQ(String q) throws ErrorWeb {
             try {
           if(autorRepository.findByQ("%"+q+"%").isEmpty()){
               throw new ErrorWeb("No hay coincidencias");
           }
        } catch (ErrorWeb e) {
            throw e;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error de sistema");
        }
   return autorRepository.findByQ("%"+q+"%");
    }

    //Busca un Autor por id y le cambia el nombre, por el que llega en el objeto de parametro
    public Autor modificarAutor(Autor autor) throws ErrorWeb {
          try {
            if (autor == null) {
                throw new ErrorWeb("Autor es obligatorio");
                 }            
            if(autor.getNombre().isEmpty() || autor.getNombre()== null || autor.getNombre()== "" || autor.getNombre()== " "){
            throw new ErrorWeb("El nombre nuevo del autor es obligatorio");
            }
            
         if (autorRepository.findByNombre(autor.getNombre()) != null) {
                throw new ErrorWeb("El nombre de autor ya existe!");
            }
         if (autor.getNombre().startsWith(" ")) {
                throw new ErrorWeb("El nombre del autor no puede comenzar con espacio!");
            }
            
            
        } catch (ErrorWeb e) {
            throw e;        
         } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error de Sistema");
        }
          
        Autor a = autorRepository.findById(autor.getId()).get();
        a.setNombre(autor.getNombre());
        return autorRepository.save(a);
    }

}
