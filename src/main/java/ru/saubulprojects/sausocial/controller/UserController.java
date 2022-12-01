package ru.saubulprojects.sausocial.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/getUsers") 
	public HttpEntity<Page<UserDTO>> getUsers(@RequestParam("pageNo") int pageNo) {
		return new ResponseEntity<>(userService.findUsers(pageNo, 5), HttpStatus.OK);
	}
	
	@GetMapping
	public HttpEntity<UserDTO> getUserByUsername(@RequestParam("username") String username){
		return new ResponseEntity<UserDTO>(userService.findUserModelByUsername(username), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public HttpEntity<UserDTO> saveUser(@RequestBody User user) {
		return new ResponseEntity<UserDTO>(userService.saveUserDTO(user), HttpStatus.CREATED);
	}
	

	
}
