package ru.saubulprojects.sausocial.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.saubulprojects.sausocial.dto.RegisterRequest;
import ru.saubulprojects.sausocial.entity.User;

public interface AuthenticationService {
	
	void signUp(RegisterRequest registerRequest);

	void verifyAccount(String token);
	
	User getCurrentUser();
	
	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
