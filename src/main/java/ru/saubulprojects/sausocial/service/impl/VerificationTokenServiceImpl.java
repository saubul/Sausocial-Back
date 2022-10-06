package ru.saubulprojects.sausocial.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.entity.VerificationToken;
import ru.saubulprojects.sausocial.repository.VerificationTokenRepository;
import ru.saubulprojects.sausocial.service.VerificationTokenService;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService{
	
	private final VerificationTokenRepository verificationTokenRepo;
	
	@Override
	public String generateVerificationToken(User user) {
		
		String verificationTokenString = UUID.randomUUID().toString();
		VerificationToken verificationToken = VerificationToken.builder()
																   .token(verificationTokenString)
																   .user(user)
															   .build();
		return verificationTokenRepo.save(verificationToken).getToken();
		
	}

	@Override
	public Optional<VerificationToken> findByToken(String token) {
		
		return verificationTokenRepo.findByToken(token);
	}
	
}
