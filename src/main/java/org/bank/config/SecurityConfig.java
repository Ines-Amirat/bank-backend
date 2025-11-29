package org.bank.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserDetailsService uds;

    public SecurityConfig(UserDetailsService uds) {
        this.uds = uds;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(PasswordEncoder pe) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(pe);
        provider.setUserDetailsService(uds);
        return new ProviderManager(provider);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // CORS pour accepter Angular + cookies de session
                .cors(cors -> cors.configurationSource(corsConfig()))

                .sessionManagement(sm -> sm.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/auth/me").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()  // <--- CHANGÉ ICI

                        // Les endpoints bancaires nécessitent un user connecté
                        .requestMatchers("/api/accounts/**").permitAll()
                        .requestMatchers("/api/transactions/**").permitAll()
                        .requestMatchers("/api/transfers/**").permitAll()

                        .anyRequest().permitAll()
                )

                // On désactive formLogin et BasicAuth
                .httpBasic(h -> h.disable())
                .formLogin(f -> f.disable())

                // 401 JSON clean
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"error\":\"unauthorized\"}");
                }));

        return http.build();
    }

    private CorsConfigurationSource corsConfig() {
        return request -> {
            CorsConfiguration c = new CorsConfiguration();
            c.setAllowedOrigins(List.of("http://localhost:4200"));
            c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
            c.setAllowedHeaders(List.of("*"));
            c.setAllowCredentials(true);  // IMPORTANT pour les cookies
            c.setMaxAge(3600L);
            return c;
        };
    }
}
