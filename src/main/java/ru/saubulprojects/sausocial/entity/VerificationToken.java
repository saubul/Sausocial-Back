package ru.saubulprojects.sausocial.entity;


import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationToken {

	@Id
	@SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
	private Long id;
	
	private String token;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
	@CreationTimestamp
	@Column(name = "date_created")
	private LocalDate dateCreated;
	
}
