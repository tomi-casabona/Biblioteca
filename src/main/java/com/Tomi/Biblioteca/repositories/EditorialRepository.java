

package com.Tomi.Biblioteca.repositories;

import com.Tomi.Biblioteca.entities.Editorial;
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
public interface EditorialRepository extends JpaRepository<Editorial, String>{
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial findByNombre(@Param("nombre")String nombre);   
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
    public List<Editorial> findByQ(@Param("nombre")String nombre);   
    
}
