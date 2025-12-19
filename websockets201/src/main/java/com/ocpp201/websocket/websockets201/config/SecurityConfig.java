//package com.ocpp201.websocket.websockets201.config;
//
//import com.ocpp201.websocket.websockets201.security.CustomUserDeatilsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final CustomUserDeatilsService userDetailsService;
//
//    public SecurityConfig(CustomUserDeatilsService uds) {
//        this.userDetailsService = uds;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/css/**").permitAll()
//
//                        // Swagger support
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//
//                        .requestMatchers("/dashboard/**", "/transactions/**", "/logs/**")
//                        .hasRole("ADMIN")
//
//                        .requestMatchers("/ocpp/**").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logout=true")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // change to BCrypt later
//    }
//
////    @Bean
////    public DaoAuthenticationProvider authenticationProvider() {
////
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();  // ← no constructor args
////        provider.setUserDetailsService(userDetailsService);
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }
//
//
////    @Bean
////    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
////        AuthenticationManagerBuilder builder =
////                http.getSharedObject(AuthenticationManagerBuilder.class);
////
////        builder.authenticationProvider(authenticationProvider());
////        return builder.build();
////    }
//
//}