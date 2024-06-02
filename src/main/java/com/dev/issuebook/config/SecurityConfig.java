package com.dev.issuebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dev.issuebook.jwt.JWTAccessDeniedHandler;
import com.dev.issuebook.jwt.JwtAuthenticationEntryPoint;
import com.dev.issuebook.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
    private JWTAccessDeniedHandler accessDeniedHandler;
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JWTAccessDeniedHandler accessDeniedHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/dib/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/dib/**").hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web)
                -> web.ignoring().antMatchers("/authenticate/signup", "/authenticate/login", "/authenticate/refreshtoken");
    }

}
