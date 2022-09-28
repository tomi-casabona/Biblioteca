

package com.Tomi.Biblioteca.services;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Editorial;
import com.Tomi.Biblioteca.repositories.AutorRepository;
import com.Tomi.Biblioteca.repositories.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomi
 */
@Service
public class EditorialServices {
    
   // @Autowired
    //private Editorial editorial;
    
    @Autowired
    private EditorialRepository editorialRepository;
           
    
    // Instancia un nuevo objeto Editorial y lo guarda en la base de datos
    @Transactional
    public Editorial crearEditorial(String editorial) throws ErrorWeb{
        try {
            if (editorial == null) {
                throw new ErrorWeb("El nombre no puede estar vacio !!");
            }
            if (editorial.startsWith(" ")) {
                throw new ErrorWeb("El nombre de la editorial no puede comenzar con espacio !!");
            }
            if (editorialRepository.findByNombre(editorial)!= null) {
                throw new ErrorWeb("El nombre ya existe!");
            }
            
        } catch (ErrorWeb ew) {
            throw ew;
        }
         catch (Exception e) {
    e.printStackTrace();
    throw new ErrorWeb("Error de sistema");
        }
        
        Editorial a = new Editorial();
        a.setNombre(editorial);
        return editorialRepository.save(a);
    }

    
    //Busca un Editorial por id y lo elimina
    @Transactional
    public void borrarEditorial (String id) throws ErrorWeb{   
        try {
            if (id == null) {
                throw new ErrorWeb("El id no puede estar vacio");
            }
            if (editorialRepository.findById(id).get() == null) {
                throw new ErrorWeb("El id no corresponde a una editorial");
            }
            
        } catch (ErrorWeb ew) {
            throw ew;
        }
         catch (Exception e) {
             e.printStackTrace();
             throw new ErrorWeb("Error de sistema");
        }
    editorialRepository.delete(editorialRepository.findById(id).get());}
    
    
    // Busca un editorial por id en la database y lo devuelve
    public Editorial mostrarEditorial (String id) throws ErrorWeb{
        try {
            if (id == null) {
                throw new ErrorWeb("El id no puede estar vacio");
            }
            
        } catch (ErrorWeb ew) {
            throw ew;
        }
         catch (Exception e) {
             e.printStackTrace();
             throw new ErrorWeb("Error de sistema");
        }
   return editorialRepository.findById(id).get();
    }
    
     public Optional<Editorial> findById(String id) throws ErrorWeb {
         try {
            if (id == null) {
                throw new ErrorWeb("El id no puede estar vacio");
            }
            
        } catch (ErrorWeb ew) {
            throw ew;
        }
         catch (Exception e) {
             e.printStackTrace();
             throw new ErrorWeb("Error de sistema");
        }
        return editorialRepository.findById(id);
    }
    
    // Busca un editorial por nombre en la database y lo devuelve
    public List<Editorial> mostrarEditoriales () throws ErrorWeb{
        try {
           if(editorialRepository.findAll().isEmpty()){
               throw new ErrorWeb("La lista de Editoriales esta vacia");
           }
        } catch (ErrorWeb e) {
            throw e;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error de sistema");
        }
   return editorialRepository.findAll();
    }
    // Busca un editorial por query en la database y lo devuelve
    public List<Editorial> findByQ(String q) throws ErrorWeb{
        try {
           if(editorialRepository.findByQ("%"+q+"%").isEmpty()){
               throw new ErrorWeb("No hay coincidencias");
           }
        } catch (ErrorWeb e) {
            throw e;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error de sistema");
        }
   return editorialRepository.findByQ("%"+q+"%");
    }
    
 
    public Editorial modificarEditorial(Editorial editorial) throws ErrorWeb{
        try {
            if (editorial == null) {
                throw new ErrorWeb("El campo editorial no puede estar vacio");
            }
             if (editorial.getNombre().isEmpty() || editorial.getNombre()== null || editorial.getNombre()== "" || editorial.getNombre()== " ") {
                throw new ErrorWeb("El campo nombre no puede estar vacio");
            }
             if (editorialRepository.findByNombre(editorial.getNombre()) != null) {
                throw new ErrorWeb("El nombre de la editorial ya existe");
            }
              if (editorial.getNombre().startsWith(" ")) {
                throw new ErrorWeb("El nombre de la editorial no puede comenzar con espacio !!");
            }
            
        } catch (ErrorWeb ew) {
            throw ew;
        }
         catch (Exception e) {
             e.printStackTrace();
             throw new ErrorWeb("Error de sistema");
        }
     
        Editorial e = editorialRepository.findById(editorial.getId()).get();
        e.setNombre(editorial.getNombre());
      
       return editorialRepository.save(e);
    }
    
}
