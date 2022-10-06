package ru.saubulprojects.sausocial.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	
	@Id
	@SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
	private Long id;
	
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
}
