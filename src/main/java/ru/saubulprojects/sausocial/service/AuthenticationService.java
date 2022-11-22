package ru.saubulprojects.sausocial.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.User;

public interface AuthenticationService {
	
	void signUp(UserDTO userDTO);

	void verifyAccount(String token);
	
	User getCurrentUser();
	
	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
