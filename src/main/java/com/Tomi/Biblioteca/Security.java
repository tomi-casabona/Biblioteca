

package com.Tomi.Biblioteca;

import com.Tomi.Biblioteca.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.Tomi.Biblioteca.repositories.UsuarioRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Tomi
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{

    
    //USerDetailServices -> loadByUserName -> UsuarioServicio
    @Autowired
    private UsuarioServices usuarioServices;   
    
    //Un metodo que va a configurar la autenticacion configureGlobal
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServices).passwordEncoder(new BCryptPasswordEncoder());
        };
    //La configuracion de las peticiones Http
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().antMatchers("/*").permitAll(); // para que nos de libre acceso
//    }
//    
        
            @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/css/*","/img/*","/js/*").permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/?success")
                    .loginProcessingUrl("/logincheck")
                    .failureUrl("/login?error=error")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
                
                
    }
   
}
