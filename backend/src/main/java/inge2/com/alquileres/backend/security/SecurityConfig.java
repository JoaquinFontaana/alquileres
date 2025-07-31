package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.configuration.CorsConfig;
import inge2.com.alquileres.backend.service.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTAuthEntryPoint authEntryPoint;
    private final CustomDetailsService userDetailsService;
    private final EncryptService encryptService;

    @Autowired
    public SecurityConfig(JWTAuthEntryPoint authEntryPoint, CustomDetailsService userDetailsService, EncryptService encryptService, CorsConfig corsConfig) {
        this.authEntryPoint = authEntryPoint;
        this.userDetailsService = userDetailsService;
        this.encryptService = encryptService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .exceptionHandling( e -> e.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sucursal/admin/**").hasRole("ADMIN")
                        .requestMatchers("/sucursal/listar").permitAll()
                        .requestMatchers("/alquiler/cancelarReserva").hasRole("CLIENT")
                        .requestMatchers("/alquiler/listar").hasAnyRole("ADMIN","EMPLEADO")
                        .requestMatchers("/alquiler/get/extras").permitAll()
                        .requestMatchers("/alquiler/empleado/*").hasRole("EMPLEADO")
                        .requestMatchers("/estadisticas/**").hasRole("ADMIN")
                        .requestMatchers("/cliente/registrar").permitAll()
                        .requestMatchers("/cliente/multa").hasRole("CLIENT")
                        .requestMatchers("/auto/listar").permitAll()
                        .requestMatchers("/auto/reparado").hasRole("EMPLEADO")
                        .requestMatchers("/auto/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auto/get/**").permitAll()
                        .requestMatchers("/alquileres/pendientes").hasRole("EMPLEADO")
                        .requestMatchers("/cliente/listar/**").hasRole("CLIENT")
                        .requestMatchers("/checkOut/cliente/**").hasRole("CLIENT")
                        .requestMatchers("/checkOut/notificacion/**").permitAll()
                        .requestMatchers("/checkOut/empleado/**").hasRole("EMPLEADO")
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/auth/dobleAutenticacion").hasRole("ADMIN")
                        .requestMatchers("/empleado/**").hasRole("EMPLEADO")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager (
            AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);       // tu CustomDetailsService
        provider.setPasswordEncoder(passwordEncoder());           // tu PasswordEncoder que delega en EncryptService
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                // delega al servicio que ya tienes
                return encryptService.encryptPassword(rawPassword.toString());
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // y aquí verificas
                return encryptService.verifyPassword(rawPassword.toString(), encodedPassword);
            }
        };
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }


}
