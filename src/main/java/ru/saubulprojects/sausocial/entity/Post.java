package ru.saubulprojects.sausocial.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	
	@Id
	@SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
	private Long id;
	
	@Column(name = "post_name")
	@NotBlank(message = "Post name can't be empty.")
	private String postName;
	
	private String url;
	
	private String text;
	
	@Column(name = "vote_count")
	private Integer voteCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
	@CreationTimestamp
	@Column(name = "date_created")
	private LocalDate dateCreated;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subreddit_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "subreddit_id_fk"))
	private Subreddit subreddit;
		
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private List<Comment> comments;
}
