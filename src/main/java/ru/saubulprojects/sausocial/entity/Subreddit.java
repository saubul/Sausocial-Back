package ru.saubulprojects.sausocial.entity;


import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subreddits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subreddit {
	
	@Id
	@SequenceGenerator(name = "subreddit_id_seq", sequenceName = "subreddit_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subreddit_id_seq")
	private Long id;

	@NotBlank(message = "Name is required.")
	private String name;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
	@JsonIgnore
	private Collection<Post> posts;
	
	@CreationTimestamp
	@Column(name = "date_created")
	private LocalDate dateCreated;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
}



