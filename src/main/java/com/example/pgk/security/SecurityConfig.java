package com.example.pgk.security;

import com.example.pgk.config.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /*@Bean
    SecurityFilterChain springWebFilterChain(HttpSecurity http,
                                             JwtTokenProvider tokenProvider) throws Exception {
        return http
                *//*.httpBasic(AbstractHttpConfigurer::disable)*//*
                .httpBasic().disable()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                *//*.exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))*//*
                .authorizeRequests(c -> c
                        .antMatchers("/api/v1/login", "/api/v1/signup").permitAll()
                                .antMatchers("/api/auth/login","/api/auth/register",
                                        "/api/auth/gettokens").permitAll()
                                .antMatchers("/api/auth/test/admin").hasAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                        //  .antMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
                        // .antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
                        // .antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
                        *//*.anyRequest().authenticated()*//*

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        r -> r
                                .antMatchers("/api/v1/test/admin").hasAuthority("ROLE_ADMIN")
                                .antMatchers("/api/v1/test").authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();
    }

    /*@Bean
    UserDetailsService customUserDetailsService(UserRepository users) {
        logger.info("customUserDetailsService");
        return (username) -> users.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }*/

    /*@Bean
    AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        logger.info("customAuthenticationManager");
        return authentication -> {
            String username = authentication.getPrincipal() + "";
            String password = authentication.getCredentials() + "";
            logger.info("encoder: {}", encoder);
            logger.info("customAuthenticationManager: userDetailsService.loadUserByUsername(username)");
            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (!encoder.matches(password, user.getPassword())) {
                logger.info("customAuthenticationManager: !encoder.matches(password, user.getPassword()))");
                throw new BadCredentialsException("Bad credentials");
            }

            if (!user.isEnabled()) {
                logger.info("customAuthenticationManager: !user.isEnabled()");
                throw new DisabledException("User account is not active");
            }
            logger.info("new UsernamePasswordAuthenticationToken");
            return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
        };
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
