package com.shinom.blogging.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.shinom.blogging.exceptions.OAuth2AuthenticationFailure;
import com.shinom.blogging.security.JwtAuthenticationEntryPoint;
import com.shinom.blogging.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private  JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;	
	
	@Autowired
	private OAuth2SuccessHandler successHandler;
	
	@Autowired
	private OAuth2AuthenticationFailure failureHandler;

	private  final String REQUEST_URLS[] = {"/blogging/auth/**","/v3/api-docs","/swagger-ui/**","/swagger-resources/**","/webjars/**", "/api-docs", "/logout", "/blogging", "/favicon.ico"};
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
	 http.csrf(csrf->csrf.disable());
	 http.cors(Customizer.withDefaults());
	 
	 
	 http.authorizeHttpRequests( request -> 
	 	request
	 	.requestMatchers(REQUEST_URLS).permitAll()
	 	.requestMatchers(HttpMethod.GET).permitAll()
	 	.anyRequest().authenticated()
	 	
	 ).oauth2Login(oauth2-> oauth2.loginPage("http://localhost:3000/login").successHandler(successHandler));	 
	 http.exceptionHandling(config -> config.authenticationEntryPoint(this.authenticationEntryPoint));
	 http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 
	 
	 
	 http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	 
	 
	 return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public CorsFilter corsFilter()
	{
	    // Create a CorsConfigurationSource bean
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	 
	    // Create a CorsConfiguration instance
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOriginPattern("*");
	    corsConfiguration.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
	    corsConfiguration.setAllowedHeaders(List.of("*"));;
	    corsConfiguration.setMaxAge(3600L);
	      source.registerCorsConfiguration("/**", corsConfiguration);
	 
	    // Create and return a new CorsFilter with the configuration source
	    return new CorsFilter(source);
	    
	}

}
