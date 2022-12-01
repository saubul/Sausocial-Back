package ru.saubulprojects.sausocial.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import ru.saubulprojects.sausocial.entity.User;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(value = {"/create-user-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void testFindUserByUsername() {
		String username = "Saubul";
		Optional<User> user = userRepository.findByUsername(username);
		
		assertEquals(user.get().getUsername(), "Saubul");
	}
	
	@Test
	void testFindUserByUsernameException() {
		String username = "saubul";
		Optional<User> user = userRepository.findByUsername(username);
		
		assertThat(user.isEmpty()).isTrue();
	}

}
