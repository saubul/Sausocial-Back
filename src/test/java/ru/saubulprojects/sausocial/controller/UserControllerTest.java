package ru.saubulprojects.sausocial.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	//@Autowired
	//private UserController userController;

	@Test
	void testGetUserByUsername() throws Exception {
		mockMvc.perform(get("/api/user").param("username", "username"))
			   .andDo(print())
			   .andExpect(status().isOk());
		
		verify(userService).findUserModelByUsername("username");
	}

	@Test
	void testSaveUser() throws Exception {
		User user = new User();
		this.mockMvc.perform(post("/api/user/save")
								 .contentType(MediaType.APPLICATION_JSON)
								 .content(new ObjectMapper().writeValueAsString(user)))
					.andDo(print())
					.andExpect(status().isCreated());
		
		verify(userService).saveUserDTO(user);
	}

}
