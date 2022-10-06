package ru.saubulprojects.sausocial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.VoteType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDTO {
	
	private VoteType voteType;
	private Long postId;
	
}
