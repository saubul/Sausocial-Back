package ru.saubulprojects.sausocial.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.RegisterRequest;
import ru.saubulprojects.sausocial.entity.NotificationEmail;
import ru.saubulprojects.sausocial.entity.Role;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.entity.VerificationToken;
import ru.saubulprojects.sausocial.exception.SausocialException;
import ru.saubulprojects.sausocial.service.AuthenticationService;
import ru.saubulprojects.sausocial.service.MailService;
import ru.saubulprojects.sausocial.service.UserService;
import ru.saubulprojects.sausocial.service.VerificationTokenService;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserService userService;
	private final VerificationTokenService verificationTokenService;
	private final MailService mailService;
	private final long jwtExpirationTime = 6000000;
	
	@Override
	public void signUp(RegisterRequest registerRequest) {
		User user = User.builder()
							.username(registerRequest.getUsername())
							.password(registerRequest.getPassword())
							.email(registerRequest.getEmail())
							.enabled(false)
							.roles(Arrays.asList(new Role("ROLE_USER")))
						.build();
		userService.saveUser(user);
		
		String verificationToken = verificationTokenService.generateVerificationToken(user);
		mailService.sendMail(NotificationEmail.builder()
											  	  .recipient(user.getEmail())
											  	  .subject("SAUSOCIAL ACCOUNT ACTIVATION")
											  	  .body("Thank you for signing up to Sausocial, " +
											            "please click on the link below to activate your account : " +
											            "http://localhost:8080/api/auth/accountVerification/" + verificationToken)
											  .build());
		
	}

	@Override
	@Transactional
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationTokenOptional = verificationTokenService.findByToken(token);
		VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() -> new SausocialException("Invalid verification token."));
		
		User user = userService.findUserById(verificationToken.getUser().getId());
		
		user.setEnabled(true);
		userService.saveUserDefault(user);
	}

	@Override
	public User getCurrentUser() {
		
		return userService.findUserByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	@Override
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
											.withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
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
				new ObjectMapper().writeValue(response.getOutputStream(), "Error message" + e.getMessage());
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
		
	}
	
}
