package ru.saubulprojects.sausocial.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.Role;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.service.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserResource {
	
	private final UserService userService;
	
	@GetMapping("/user/getUsers") 
	public HttpEntity<Page<UserDTO>> getUsers(@RequestParam("pageNo") int pageNo) {
		log.info("getUsers() method of " + UserResource.class + " with params: pageNo = {}", pageNo);
		return new ResponseEntity<>(userService.findUsers(pageNo, 5), HttpStatus.OK);
	}
	
	@PostMapping("/user/save")
	public HttpEntity<User> saveUser(@RequestBody User user) {
		log.info("saveUser() method of " + UserResource.class + " with User ID: {}", user.getId());
		return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/api/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer ".length());
				
				Algorithm algo = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				
				String username = decodedJWT.getSubject();
				
				User user = userService.findUserByUsername(username);
				
				String accessToken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algo);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("accessToken", accessToken);
				tokens.put("refreshToken", refreshToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
			} catch (Exception e) {
				response.setHeader("Error", e.getMessage());
				Map<String, String> errors = new HashMap<>();
				errors.put("Error message", e.getMessage());
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
		
	}
	
}
