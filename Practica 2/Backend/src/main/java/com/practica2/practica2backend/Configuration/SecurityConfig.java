package com.practica2.practica2backend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    private final AuthTokenFilter authTokenFilter;

    public SecurityConfig(AuthTokenFilter authTokenFilter) {
        this.authTokenFilter = authTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Clase para encriptar contraseña
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        //
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/dbconsole/**", "/login").permitAll()
                .antMatchers("/").authenticated()

                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/**").permitAll()
                .and().addFilterBefore(new AuthTokenFilter(), BasicAuthenticationFilter.class);*/
        http.cors().and().csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and()
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/{resource}")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        List<String> allowed = new ArrayList<>();
        allowed.add("http://localhost:3000");
        allowed.add("https://mockys.dl-santos-company.games");
        allowed.add("https://54.156.150.48");
        config.setAllowedOrigins(allowed);
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept","Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}