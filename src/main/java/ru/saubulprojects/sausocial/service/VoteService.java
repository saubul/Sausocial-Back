package ru.saubulprojects.sausocial.service;

import ru.saubulprojects.sausocial.dto.VoteDTO;
import ru.saubulprojects.sausocial.entity.Vote;

public interface VoteService {

	Vote saveVote(VoteDTO voteDTO);

	VoteDTO saveVoteDTO(VoteDTO voteDTO);

}
