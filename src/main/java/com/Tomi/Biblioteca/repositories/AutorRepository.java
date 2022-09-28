

package com.Tomi.Biblioteca.repositories;

import com.Tomi.Biblioteca.entities.Autor;
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
public interface AutorRepository extends JpaRepository<Autor, String>{
    
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Autor findByNombre(@Param("nombre")String nombre);   
    
    
    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre")
    public List<Autor> findByQ(@Param("nombre")String nombre);   
    
}

