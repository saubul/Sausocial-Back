package ru.saubulprojects.sausocial.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserPassAuthFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	private long jwtExpirationTime = 6000000;
	
	public UserPassAuthFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.setFilterProcessesUrl("/api/auth/login");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException {
			
		if(!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		String username = this.obtainUsername(request);
		username = (username != null) ? username.trim() : "";
		
		String password = this.obtainPassword(request);
		password = (password != null) ? password.trim() : "";
		
		UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		return this.authenticationManager.authenticate(authRequest);
		
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filter, Authentication authentication) 
			throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		Date expiresAt = new Date(System.currentTimeMillis() + jwtExpirationTime);
		String accessToken = JWT.create()
									.withSubject(user.getUsername())
									.withExpiresAt(expiresAt)
									.withIssuer(request.getRequestURL().toString())
									.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
									.sign(algorithm);
		
		String refreshToken = JWT.create()
									.withSubject(user.getUsername())
									.withIssuer(request.getRequestURL().toString())
									.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
									.sign(algorithm);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		tokens.put("username", user.getUsername());
		tokens.put("expiresAt", expiresAt.toString());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}
}
