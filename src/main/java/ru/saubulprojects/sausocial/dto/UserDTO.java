package ru.saubulprojects.sausocial.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private LocalDate birthday;
	private String country;
	
	public static UserDTO buildUserDTO(User user) {
		UserDTO userDTO = UserDTO.builder()
									 .name(user.getName())
									 .surname(user.getSurname())
									 .email(user.getEmail())
									 .country(user.getCountry())
									 .birthday(user.getBirthday())
									 .username(user.getUsername())
									 .password(user.getPassword())
								 .build();
		return userDTO;
	}
	
}
