package ru.saubulprojects.sausocial.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(value = {"/create-user-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserController userController;
	
	@Test
	void testGetUsers() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserByUsername() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveUser() {
		fail("Not yet implemented");
	}

}
