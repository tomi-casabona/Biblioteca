package com.Tomi.Biblioteca.services;

import com.Tomi.Biblioteca.Errores.ErrorWeb;
import com.Tomi.Biblioteca.entities.Editorial;
import com.Tomi.Biblioteca.entities.Libro;
import com.Tomi.Biblioteca.entities.Usuario;
import com.Tomi.Biblioteca.enums.Role;
import com.Tomi.Biblioteca.repositories.AutorRepository;
import com.Tomi.Biblioteca.repositories.EditorialRepository;
import com.Tomi.Biblioteca.repositories.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Tomi.Biblioteca.repositories.UsuarioRepository;
import java.util.ArrayList;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Tomi
 */
@Service
public class UsuarioServices implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LibroServices libroServices;

    // Instancia un nuevo objeto Editorial y lo guarda en la base de datos
    @Transactional
    public Usuario crearUser(String userName, String password, String password2) throws ErrorWeb {

        if (userName.startsWith(" ")) {
            throw new ErrorWeb("El username no puede comenzar con espacio !!");
        }
        if (userName == null) {
            throw new ErrorWeb("El username puede estar vacio !!");
        }
        if (userName.length() < 3) {
            throw new ErrorWeb("El username debe ser de al menos 3 digitos !!");
        }
        if (usuarioRepository.findByUserName(userName) != null) {
            throw new ErrorWeb("El nombre de usuario ya existe!");
        }

        if (password == null || password.isEmpty()) {
            throw new ErrorWeb("El password no puede estar vacio !!");
        }
        if (password.startsWith(" ")) {
            throw new ErrorWeb("El password no puede comenzar con espacio !!");
        }
        if (password2.startsWith(" ")) {
            throw new ErrorWeb("El password 2 no puede comenzar con espacio !!");
        }
        if (password2 == null || password.isEmpty()) {
            throw new ErrorWeb("El password 2 no puede estar vacio !!");
        }

        if (!password.equals(password2)) {
            throw new ErrorWeb("Los password no coinciden");

        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario a = new Usuario();
        a.setUsername(userName);
        a.setPassword(encoder.encode(password));
        a.setRol(Role.USER);

        return usuarioRepository.save(a);
    }
    // modifica un ususario
    @Transactional
    public Usuario modificarUsuario (Usuario u) throws ErrorWeb {

        if (u.getUsername().startsWith(" ")) {
            throw new ErrorWeb("El username no puede comenzar con espacio !!");
        }
        if (u.getUsername() == null) {
            throw new ErrorWeb("El username puede estar vacio !!");
        }
        if (u.getUsername().length() < 3) {
            throw new ErrorWeb("El username debe ser de al menos 3 digitos !!");
        }
        if (usuarioRepository.findByUserName(u.getUsername()) != null && 
            !usuarioRepository.findByUserName(u.getUsername()).getId().equals(u.getId())) {
            throw new ErrorWeb("El nombre de usuario ya existe!");
        }


        return usuarioRepository.save(u);
    }
    @Transactional
    public Usuario modificarContraseña (Usuario u, String p1, String p2) throws ErrorWeb{
        if (!p1.equals(p2) ) {
            throw new ErrorWeb("Las contraseñas no coinciden");            
        }
        if (p1.startsWith(" ") ) {
            throw new ErrorWeb("La contraseña no puede comenzar con espacio");            
        }
        if (p1.length() <3 ) {
            throw new ErrorWeb("La contraseña no puede tener menos de 3 caracteres");            
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        u.setPassword(encoder.encode(p1));
        
    return u;}

    @Transactional
    public void guardarLibro(String id) throws ErrorWeb {
        Libro l = libroServices.findById(id).get();
       
        if (id == null) {
            throw new ErrorWeb("El id no llega al usuario");
        }
        if (l == null) {
            throw new ErrorWeb("El id no corresponde a un libro");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario u = usuarioRepository.findByUserName(auth.getName());
        
         if (u.getLibros().contains(libroServices.findById(id).get())) {
            throw new ErrorWeb("No se puede prestar dos libros iguales");
        }
        
        if (u.getLibros() == null) {
            List<Libro> listalibros = new ArrayList<Libro>();
            listalibros.add(l);
            u.setLibros(listalibros);
        }
        List<Libro> ll = u.getLibros();
        ll.add(l);
        u.setLibros(ll);

    }

    @Transactional
    public void devolverLibro(String id) throws ErrorWeb {

        if (id == null) {
            throw new ErrorWeb("El id no llega al usuario");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario u = usuarioRepository.findByUserName(auth.getName());

        if (u.getLibros() == null || u.getLibros().isEmpty()) {
            throw new ErrorWeb("El usuario no posee ningun Libro");
        }

        List<Libro> libros = u.getLibros();

        if (libros.contains(libroServices.findById(id).get())) {

            for (int i = 0; i < libros.size(); i++) {
                if (libros.get(i).equals(libroServices.findById(id).get())) {
                    libroServices.devolverLibro(id);
                    libros.remove(i);
                }
            }
        } else {
            throw new ErrorWeb("No posees el libro:"+" "+libroServices.findById(id).get().getTitulo());

        }

    }

    //Busca un Editorial por id y lo elimina
    @Transactional
    public void borrarUser(String id) throws ErrorWeb {

        if (id == null) {
            throw new ErrorWeb("El id no puede estar vacio");
        }
        usuarioRepository.delete(usuarioRepository.findById(id).get());
    }

    // Busca un usuario por id en la database y lo devuelve
    public Usuario findById(String id) throws ErrorWeb {

        if (id == null) {
            throw new ErrorWeb("El id no puede estar vacio");
        }

        return usuarioRepository.findById(id).get();
    }
    //devuelve el usuario activo
    public Usuario FindUser() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Usuario u = usuarioRepository.findByUserName(auth.getName());
        
        return u;
    }
    
    // Busca todos los usuarios de la database
   public List<Usuario> findAll() throws ErrorWeb{
       if (usuarioRepository.findAll().isEmpty()) {
           throw new ErrorWeb("La lista de usuarios esta vacia");
       }
   return usuarioRepository.findAll();
           }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.findByUserName(username);
            User user;

            List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
            return new User(username, usuario.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }

}
