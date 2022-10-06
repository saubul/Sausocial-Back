package ru.saubulprojects.sausocial.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
	
	@Id
	@SequenceGenerator(name = "vote_id_seq", sequenceName = "vote_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_seq")
	private Long id;
	
	@Enumerated(value = EnumType.STRING)
	private VoteType voteType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "post_id_fk"))
	private Post post;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
}
