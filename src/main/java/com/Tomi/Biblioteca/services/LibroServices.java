package com.Tomi.Biblioteca.services;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Autor;
import com.Tomi.Biblioteca.entities.Editorial;
import com.Tomi.Biblioteca.entities.Libro;
import com.Tomi.Biblioteca.repositories.AutorRepository;
import com.Tomi.Biblioteca.repositories.EditorialRepository;
import com.Tomi.Biblioteca.repositories.LibroRepository;
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
public class LibroServices {
//  (consulta, creación, modificación y dar de baja)

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Autowired
    private EditorialServices editorialServices;

    @Autowired
    private AutorServices autorServices;

  
    // Crea un nuevo objeto Libro en la base de Datos
    @Transactional
    public Libro cargarLibro(Libro libro) throws ErrorWeb {
        
        try {
            if (libro.getTitulo()==null || libro.getTitulo().startsWith(" ") || libro.getTitulo().isEmpty()) {
                throw new ErrorWeb("El titulo no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getEditorial()==null || libro.getEditorial().getNombre().startsWith(" ") || libro.getEditorial().getNombre().isEmpty()) {
                throw new ErrorWeb("El campo editorial no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getAnio()==null || libro.getAnio().toString().startsWith(" ") ) {
                throw new ErrorWeb("El campo año no pude estar vacio, ni comenzar con espacio");                
            }
              if (libro.getAnio().toString().length() == 3 ) {
                throw new ErrorWeb("El campo año debe tener 4 digitos");                
            }  
           
            if (libro.getAutor()==null || libro.getAutor().getNombre().startsWith(" ") || libro.getAutor().getNombre().isEmpty()) {
                throw new ErrorWeb("El campo autor no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getUnidadesTotales()==null || libro.getUnidadesTotales().toString().startsWith(" ")) {
                throw new ErrorWeb("El campo unidades Totales no pude estar vacio, ni comenzar con espacio");                
            }
                             
            if (libro.getUnidadesTotales()<1) {
                throw new ErrorWeb("El campo unidades Totales debe ser mayo que 0");                
            }    
           
            
            
        } catch (ErrorWeb ew) {
            throw ew;        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorWeb("Error de sistema");
        }
        
        libro.setUnidadesPrestadas(0);
        libro.setUnidadesDisponibles(libro.getUnidadesTotales());

        Editorial e = editorialRepository.findByNombre(libro.getEditorial().getNombre());

        if (e == null) {
            Editorial ed = new Editorial();
            ed.setNombre(libro.getEditorial().getNombre());
            libro.setEditorial(ed);
            editorialRepository.save(ed);
        } else {
            libro.setEditorial(e);
        }

        Autor a = autorRepository.findByNombre(libro.getAutor().getNombre());

        if (a == null) {
            Autor au = new Autor();
            au.setNombre(libro.getAutor().getNombre());
            libro.setAutor(au);
            autorRepository.save(au);
        } else {
            libro.setAutor(a);
        }

        return libroRepository.save(libro);
    }    
  
    
    
    // Busca por titulo un Libro en la base de datos y lo modifica con todos los atributos pasados por parametro
   @Transactional
    public Libro modificarLibro(Libro libro) throws ErrorWeb {
       
           try {
                       if (libro.getTitulo()==null || libro.getTitulo().startsWith(" ") || libro.getTitulo().isEmpty()) {
                throw new ErrorWeb("El titulo no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getEditorial()==null || libro.getEditorial().getNombre().startsWith(" ") || libro.getEditorial().getNombre().isEmpty()) {
                throw new ErrorWeb("El campo editorial no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getAnio()==null || libro.getAnio().toString().startsWith(" ") ) {
                throw new ErrorWeb("El campo año no pude estar vacio, ni comenzar con espacio");                
            }
              if (libro.getAnio().toString().length()==3 ) {
                throw new ErrorWeb("El campo año debe tener 4 digitos");                
            }  
           
            if (libro.getAutor()==null || libro.getAutor().getNombre().startsWith(" ") || libro.getAutor().getNombre().isEmpty()) {
                throw new ErrorWeb("El campo autor no pude estar vacio, ni comenzar con espacio");                
            }
            if (libro.getUnidadesTotales()==null || libro.getUnidadesTotales().toString().startsWith(" ")) {
                throw new ErrorWeb("El campo unidades Totales no pude estar vacio, ni comenzar con espacio");                
            }
                             
            if (libro.getUnidadesTotales()<1) {
                throw new ErrorWeb("El campo unidades Totales debe ser mayo que 0");                
            }          
            
            
       } catch (ErrorWeb e) {
           throw e;
       } catch (Exception ec) {
           ec.printStackTrace();
           throw new ErrorWeb("error de sistema");
       }
            
        libro.setUnidadesDisponibles(libro.getUnidadesTotales()-libro.getUnidadesPrestadas());
      
        
        Editorial e = editorialRepository.findByNombre(libro.getEditorial().getNombre());

        if (e == null) {
            Editorial ed = new Editorial();
            ed.setNombre(libro.getEditorial().getNombre());
            libro.setEditorial(ed);
            editorialRepository.save(ed);
        } else {
            libro.setEditorial(e);
        }

        Autor a = autorRepository.findByNombre(libro.getAutor().getNombre());

        if (a == null) {
            Autor au = new Autor();
            au.setNombre(libro.getAutor().getNombre());
            libro.setAutor(au);
            autorRepository.save(au);
        } else {
            libro.setAutor(a);
        }

        return libroRepository.save(libro);
    }

    ;

    // busca un Libro por titulo y pone su atributo alta en FALSE
   @Transactional
    public Libro bajaLibro(String titulo) throws ErrorWeb {
        try {
            if (titulo==null || titulo.startsWith(" ")) {
                throw new ErrorWeb("El titulo no puede ser nulo o comenzar con espacio");
                
            }
       } catch (ErrorWeb e) {
       throw e;
       } catch (Exception ex) {
           ex.printStackTrace();
           throw new ErrorWeb("Error del sistema");
       }
        
        Libro l = libroRepository.findByTitulo(titulo);
        l.setAlta(Boolean.FALSE);
        
        return libroRepository.save(l);
    }
    
   @Transactional
    public Libro prestarLibro(String id) throws ErrorWeb {
       
            if (id==null) {
                throw new ErrorWeb("El id no es correcto");                
            }
        
        Libro l = libroRepository.findById(id).get();
        
        l.setUnidadesPrestadas(l.getUnidadesPrestadas()+1);
        l.setUnidadesDisponibles(l.getUnidadesDisponibles()-1);
        
        return libroRepository.save(l);
    }
    
   @Transactional
    public Libro devolverLibro(String id) throws ErrorWeb {
       
            if (id==null) {
                throw new ErrorWeb("El id no es correcto");                
            }
        
        Libro l = libroRepository.findById(id).get();
        
        l.setUnidadesPrestadas(l.getUnidadesPrestadas()-1);
        l.setUnidadesDisponibles(l.getUnidadesDisponibles()+1);
        
        return libroRepository.save(l);
    }

    ;
    
     // busca un Libro por titulo y pone su atributo alta en TRUE
    @Transactional
    public Libro altaLibro(String titulo) throws ErrorWeb {
          try {
            if (titulo==null || titulo.startsWith(" ")) {
                throw new ErrorWeb("El titulo no puede ser nulo o comenzar con espacio");
                
            }
       } catch (ErrorWeb e) {
       throw e;
       } catch (Exception ex) {
           ex.printStackTrace();
           throw new ErrorWeb("Error del sistema");
       }
        Libro l = libroRepository.findByTitulo(titulo);
        l.setAlta(Boolean.TRUE);
        return libroRepository.save(l);
    }

    ;
    
    
    public Libro mostrarLibro(String titulo) throws ErrorWeb {
          try {
            if (titulo==null || titulo.startsWith(" ")) {
                throw new ErrorWeb("El titulo no puede ser nulo o comenzar con espacio");
                
            }
       } catch (ErrorWeb e) {
       throw e;
       } catch (Exception ex) {
           ex.printStackTrace();
           throw new ErrorWeb("Error del sistema");
       }
        Libro l = libroRepository.findByTitulo(titulo);
        return l;
    }

    ;
    
    public Optional<Libro> findById(String id) throws ErrorWeb {
        try {
            if (!libroRepository.findById(id).isPresent()) {
                throw new ErrorWeb("No se puede encontrar el libro");
            }
        } catch (ErrorWeb e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error del sistema");
        }
        return libroRepository.findById(id);
    }

    ;
    
    public List<Libro> listarLibros() throws ErrorWeb {
        try {
            if (libroRepository.findAll().isEmpty()){
                throw new ErrorWeb("La lista de libros esta vacia");
            }
        } catch (ErrorWeb e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error del sistema");
        }
        return libroRepository.findAll();
    }
    public List<Libro> listbyQ(String q) throws ErrorWeb {
        try {
            if (libroRepository.findByQ("%"+q+"%").isEmpty()){
                throw new ErrorWeb("No hay coincidencias");
            }
        } catch (ErrorWeb e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error del sistema");
        }
        return libroRepository.findByQ("%"+q+"%");
    }

    @Transactional
    public void borrarLibro(String id) throws ErrorWeb {
        try {
            if (id==null) {
                throw new ErrorWeb("El id no puede estar vacio");                
            }
            if (libroRepository.findById(id).get()==null) {
                 throw new ErrorWeb("El no corresponde a un libro");
            }
        } catch (ErrorWeb e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorWeb("Error del sistema");
        }
        libroRepository.delete(libroRepository.findById(id).get());
    }

;
}
