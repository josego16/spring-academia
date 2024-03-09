package com.acceso.springacademia.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private DataSource dataSource;

    // Podemos crear nuestra propia clase para autenticar a los usuarios
    // o bien usar un AuthenticationManagerBuilder.
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled "
                        + "from usuario " // added space here
                        + "where username = ?")
                .authoritiesByUsernameQuery("select username, rol.nombre  "
                        + "from usuario_roles, usuario, rol "
                        + "where usuario.id=usuario_roles.usuario_id and "
                        + "usuario_roles.roles_id = rol.id and username = ?");
    }

    /*
     * @Bean UserDetailsService userDetailsService(){
     * return new MyCustomUserDetailsService();
     * }
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        // Con Spring Security 6.2 y 7: usando Lambda DSL
        return http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/webjars/**", "/img/**", "/js/**", "/register/**", "/ayuda/**", "/login", "/denegado")
                                .permitAll()
                                .requestMatchers("/usuarios/**", "/usuarios/*/**", "/usuarios/*/*/**", "alumnos/**", "profesores/**", "asignaturas/**", "/ayuda/**", "/acerca/**")
                                //.authenticated()
                                .hasAuthority("gestor")
                        /*.anyRequest().permitAll()
                ).headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin())
                ).sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/
                ).exceptionHandling((exception) -> exception.
                        accessDeniedPage("/denegado"))
                .formLogin((formLogin) -> formLogin
                        //.loginPage("/login")
                        .permitAll()
                ).rememberMe(
                        Customizer.withDefaults()
                ).logout((logout) -> logout
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")
                        //.deleteCookies("JSESSIONID")
                        .permitAll()
                ).csrf((protection) -> protection
                                .disable()
                /*).cors((protection) -> protection
                        .disable()*/
                ).build();
    }
}