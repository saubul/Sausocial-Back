package ru.saubulprojects.sausocial.service;

import java.util.Optional;

import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.entity.VerificationToken;

public interface VerificationTokenService {

	String generateVerificationToken(User user);
	
	Optional<VerificationToken> findByToken(String token);
	
}
