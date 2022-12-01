package ru.saubulprojects.sausocial.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.saubulprojects.sausocial.dto.UserDTO;
import ru.saubulprojects.sausocial.entity.Role;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.exception.SausocialException;
import ru.saubulprojects.sausocial.repository.RoleRepository;
import ru.saubulprojects.sausocial.repository.UserRepository;
import ru.saubulprojects.sausocial.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passEncoder;
	
	@Override
	public User saveUser(User user) {
		user.setPassword(passEncoder.encode(user.getPassword()));
		Collection<Role> roles = user.getRoles().stream().map(role -> roleRepo.findByName(role.getName())).collect(Collectors.toList());
		user.setRoles(roles);
		return userRepo.save(user);
	}
	
	@Override
	public User saveUserDefault(User user) {
		return userRepo.save(user);
	}
	
	@Override
	public User findUserByUsername(String username) {
		Optional<User> userOptional = userRepo.findByUsername(username);
		
		return userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User with '%s' username not found", username)));
	}

	@Override
	public Page<UserDTO> findUsers(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return new PageImpl<>(userRepo.findAll(pageable).stream().map(user -> UserDTO.buildUserDTO(user)).collect(Collectors.toList()));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.findUserByUsername(username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
																	  user.getPassword(), 
																	  mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public User findUserById(Long id) {
		Optional<User> userOptional = userRepo.findById(id);
		User user = userOptional.orElseThrow(() -> new SausocialException(String.format("User with ID: %s not found", id)));
		return user;
	}

	@Override
	public UserDTO saveUserDTO(User user) {
		log.info("Saving new user to the database. User ID: {}", user.getId());
		user.setPassword(passEncoder.encode(user.getPassword()));
		Collection<Role> roles = user.getRoles().stream().map(role -> roleRepo.findByName(role.getName())).collect(Collectors.toList());
		user.setRoles(roles);
		userRepo.save(user);
		return UserDTO.buildUserDTO(user);
	}

	@Override
	public UserDTO findUserModelByUsername(String username) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s username not found", username)));
		UserDTO userDTO = UserDTO.buildUserDTO(user);
		return userDTO;
	}

}
