

package com.Tomi.Biblioteca.repositories;

import com.Tomi.Biblioteca.entities.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tomi
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, String>{
    
     @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro findByTitulo(@Param("titulo")String titulo);   
    
     @Query("SELECT l FROM Libro l WHERE l.titulo LIKE :q or l.autor.nombre LIKE :q or l.editorial.nombre LIKE :q")
    public List<Libro> findByQ(@Param("q")String q);   
   
    
    
}

