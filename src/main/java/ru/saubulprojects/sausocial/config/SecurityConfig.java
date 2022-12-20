package ru.saubulprojects.sausocial.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.filter.JwtAuthenticationFilter;
import ru.saubulprojects.sausocial.filter.UserPassAuthFilter;
import ru.saubulprojects.sausocial.service.UserService;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final PasswordEncoder passEncoder;
	private final UserService userService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public DaoAuthenticationProvider daoAuthProv() {
		DaoAuthenticationProvider daoAuthProv = new DaoAuthenticationProvider();
		daoAuthProv.setUserDetailsService(userService);
		daoAuthProv.setPasswordEncoder(passEncoder);
		return daoAuthProv;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests(auth -> auth
				.antMatchers("/api/auth/**", 
							"/api/post/**",
							"/api/user/**",
							"/api/subreddit/getAllSubreddits",
							"/api/comments/getComments/{postId}",
							"/v2/api-docs",
							"/configuration/ui",
							"/swagger-resources/**",
							"/configuration/security",
							"/swagger-ui.html",
                        	"/webjars/**")
				.permitAll()
				.anyRequest().authenticated())
			.addFilter(new UserPassAuthFilter(authenticationManager()))
			.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authenticationProvider(daoAuthProv())
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.formLogin(log -> log
				.loginProcessingUrl("/api/auth/login"))
			.cors().and()
			.formLogin().disable();
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token", "content-length"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
	
}
