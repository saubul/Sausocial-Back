package ru.saubulprojects.sausocial.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.saubulprojects.sausocial.entity.Role;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.repository.RoleRepository;
import ru.saubulprojects.sausocial.repository.UserRepository;

@SpringBootTest
class UserServiceImplTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Test
	void testSaveUser() {
		
		User user = new User();
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		
		userService.saveUser(user);
		
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userArgumentCaptor.capture());
		verify(passwordEncoder).encode(user.getPassword());
		
	}

	@Test
	void testFindUserByUsername() {
		String username = "username";
		
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new User()));
		
		userService.findUserByUsername(username);
		
		ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
		verify(userRepository).findByUsername(usernameArgumentCaptor.capture());
		assertEquals(username, usernameArgumentCaptor.getValue());;
	}
	
	@Test
	void testDontFindUserByUsername() {
		String username = "username";
		
		assertThrows(UsernameNotFoundException.class, () -> userService.findUserByUsername(username));
	}


}
