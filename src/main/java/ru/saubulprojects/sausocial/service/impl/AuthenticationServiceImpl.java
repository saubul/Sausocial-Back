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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.UserDTO;
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
	
	@Value("${mail_server}")
	private String mail_server;
	
	
	@Override
	public void signUp(UserDTO userDTO) {
		User user = User.builder()
							.username(userDTO.getUsername())
							.password(userDTO.getPassword())
							.email(userDTO.getEmail())
							.name(userDTO.getName())
							.surname(userDTO.getSurname())
							.birthday(userDTO.getBirthday())
							.country(userDTO.getCountry())
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
											            "http://" + mail_server + "/api/auth/accountVerification/" + verificationToken)
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
				
				DecodedJWT decodedJWT = JWT.decode(refreshToken);
				String username = decodedJWT.getSubject();
				
				User user = userService.findUserByUsername(username);
				Date expiresAt = new Date(System.currentTimeMillis() + jwtExpirationTime);
				String accessToken = JWT.create()
											.withSubject(user.getUsername())
											.withExpiresAt(expiresAt)
											.withIssuer(request.getRequestURL().toString())
											.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
										.sign(algo);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("accessToken", accessToken);
				tokens.put("refreshToken", refreshToken);
				tokens.put("username", user.getUsername());
				tokens.put("expiresAt", expiresAt.toString());
				
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
				
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
		
	}
	
}
