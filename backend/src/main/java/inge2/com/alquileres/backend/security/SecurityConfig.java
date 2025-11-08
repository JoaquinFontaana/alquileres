package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.configuration.CorsConfig;
import inge2.com.alquileres.backend.service.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity @EnableMethodSecurity
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
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/alquileres/extras").permitAll()
                        .requestMatchers(HttpMethod.GET,"/autos").permitAll()
                        .requestMatchers("/autos/categorias").permitAll()
                        .requestMatchers("/autos/estados").permitAll()
                        .requestMatchers("/autos/rembolsos").permitAll()
                        .requestMatchers(HttpMethod.GET,"/sucursales").permitAll()
                        .requestMatchers("/checkOut/notificacion/**").permitAll()
                        .requestMatchers("/estadisticas/**").hasAnyAuthority("ADMIN", "EMPLEADO")
                        .requestMatchers(HttpMethod.POST,"/clientes").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
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
