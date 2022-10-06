package ru.saubulprojects.sausocial.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.RegisterRequest;
import ru.saubulprojects.sausocial.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signUp")
	public HttpEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
			
		authenticationService.signUp(registerRequest);
		return new ResponseEntity<>("User registration successful.", HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/accountVerification/{token}")
	public HttpEntity<String> verifyAccount(@PathVariable("token") String token) {
		
		authenticationService.verifyAccount(token);
		return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
	}
	
	@GetMapping("/refreshToken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		authenticationService.refreshToken(request, response);
		
	}
}
