package ru.saubulprojects.sausocial.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_email_uq", columnNames = "email"),
											@UniqueConstraint(name = "user_username_uq", columnNames = "username")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long id;
	
	private String name;
	
	private String surname;
	
	@Email(message = "Wrong email.")
	private String email;
	
	@NotBlank(message = "Username can't be empty.")
	private String username;
	
	@NotBlank(message = "Password can't be empty.")
	private String password;
	
	@CreationTimestamp
	@Column(name = "date_created")
	private LocalDate dateCreated;
	
	private LocalDate birthday;
	
	private String country;
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles",
			   joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))},
			   inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_id_fk"))})
	private Collection<Role> roles;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comments;
	
	private boolean enabled;
}
