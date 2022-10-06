package ru.saubulprojects.sausocial.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.User;

public interface UserService extends UserDetailsService {
	
	User saveUserDefault(User user);
	
	User saveUser(User user);
	UserDTO saveUserDTO(User user);
	
	User findUserByUsername(String username);
	
	Page<UserDTO> findUsers(int pageNo, int pageSize);

	User findUserById(Long id);
}
