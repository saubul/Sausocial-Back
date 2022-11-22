package ru.saubulprojects.sausocial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.VoteType;
import ru.saubulprojects.sausocial.entity.Vote;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDTO {
	
	private VoteType voteType;
	private Long postId;
	
	public static VoteDTO buildVoteDTO(Vote vote) {
		VoteDTO voteDTO = VoteDTO.builder()
									 .postId(vote.getPost().getId())
									 .voteType(vote.getVoteType())
								 .build();
		return voteDTO;
	}
}
