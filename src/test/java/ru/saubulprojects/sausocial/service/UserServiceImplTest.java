package ru.saubulprojects.sausocial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import ru.saubulprojects.sausocial.entity.Role;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.repository.RoleRepository;
import ru.saubulprojects.sausocial.repository.UserRepository;
import ru.saubulprojects.sausocial.service.impl.UserServiceImpl;

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
		
		User savedUser = userService.saveUser(user);
		
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userArgumentCaptor.capture());
		verify(passwordEncoder).encode(user.getPassword());
		
	}

	@Test
	void testFindUserByUsername() {
		String username = "username";
		
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new User()));
		
		User user = userService.findUserByUsername(username);
		
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
