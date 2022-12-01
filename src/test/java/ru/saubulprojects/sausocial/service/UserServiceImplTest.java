package ru.saubulprojects.sausocial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(value = {"/create-user-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	void testSaveUser() {
		Collection<Role> roleCollection = new ArrayList<Role>();
		roleCollection.add(new Role("ROLE_USER"));
		User user = new User((long) 10, 
							 "Name", 
							 "Surname", 
							 "email@email.ru", 
							 "username", 
							 "password", 
							 LocalDate.now(), 
							 LocalDate.now(), 
							 "Russia", 
							 roleCollection, 
							 null, 
							 true);
		
		User savedUser = userService.saveUser(user);
		
		assertEquals(user, savedUser);
	}

	@Test
	void testFindUserByUsername() {
		String username = "Saubul";
		User user = userService.findUserByUsername(username);
		
		assertThat(user).isNotNull();
		assertEquals(user.getUsername(), "Saubul");
	}
	
	@Test
	void testFindUserByUsernameException() {
		String username = "saubul";
		
		assertThatThrownBy(() -> userService.findUserByUsername(username)).isInstanceOf(UsernameNotFoundException.class);
	}

	@Test
	@Disabled
	void testFindUsers() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testLoadUserByUsername() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testFindUserById() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testSaveUserDTO() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testFindUserModelByUsername() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testUserServiceImpl() {
		fail("Not yet implemented");
	}

}
