package ru.saubulprojects.sausocial.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/auth/login") || 
		   request.getServletPath().equals("/api/token/refresh/")) {
			filterChain.doFilter(request, response);
			return;
		} 
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String token = authorizationHeader.substring("Bearer ".length());
	
				JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret".getBytes())).build();
				
				DecodedJWT decodedJWT = verifier.verify(token);
				
				String username = decodedJWT.getSubject();
				
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

				
				UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(username, null, authorities);
				
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authenticationToken);
				SecurityContextHolder.setContext(securityContext);
				
				filterChain.doFilter(request, response);
				
			} catch (Exception e) {
				
				response.setHeader("Error", e.getMessage());
				new ObjectMapper().writeValue(response.getOutputStream(), "Error message" + e.getMessage());
				
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
