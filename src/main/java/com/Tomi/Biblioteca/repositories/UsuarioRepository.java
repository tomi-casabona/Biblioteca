

package com.Tomi.Biblioteca.repositories;

import com.Tomi.Biblioteca.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tomi
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    
     @Query("SELECT u FROM Usuario u WHERE u.username like :un")
    public Usuario findByUserName(@Param("un")String un);  
    
}
