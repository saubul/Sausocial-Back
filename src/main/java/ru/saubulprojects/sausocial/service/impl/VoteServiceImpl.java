package ru.saubulprojects.sausocial.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.VoteDTO;
import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.entity.Vote;
import ru.saubulprojects.sausocial.entity.VoteType;
import ru.saubulprojects.sausocial.repository.VoteRepository;
import ru.saubulprojects.sausocial.service.AuthenticationService;
import ru.saubulprojects.sausocial.service.PostService;
import ru.saubulprojects.sausocial.service.UserService;
import ru.saubulprojects.sausocial.service.VoteService;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	private final VoteRepository voteRepo;
	private final PostService postService;
	private final AuthenticationService authenticationService;
	private final UserService userService;
	
	@Override
	public Boolean isLikedByUsername(Long postId, String username) {
		Post post = postService.findPostModelById(postId);
		User user = userService.findUserByUsername(username);
		Optional<Vote> vote = voteRepo.findByPostAndUser(post, user);
		if(vote.isEmpty()) {
			return false;
		}
		if(vote.get().getVoteType().equals(VoteType.NEUTRAL)) {
			return false;
		}
		return true;
	}
	
	@Override
	public Vote saveVote(VoteDTO voteDTO) {
		Post post = postService.findPostModelById(voteDTO.getPostId());
		User user = authenticationService.getCurrentUser();
		VoteType voteTypeDTO = voteDTO.getVoteType();
		Optional<Vote> voteOptional = voteRepo.findByPostAndUser(post, user);
		
		Vote vote = null;
		
		if(voteOptional.isPresent()) {
			vote = voteOptional.get();
			if(vote.getVoteType() == voteTypeDTO) {
				vote.setVoteType(VoteType.NEUTRAL);
				if(voteTypeDTO == VoteType.UPVOTE) {
					post.setVoteCount(post.getVoteCount() - 1);
				} else if (voteTypeDTO == VoteType.DOWNVOTE) {
					post.setVoteCount(post.getVoteCount() + 1);
				}
			} else {
				
				if(vote.getVoteType() == VoteType.NEUTRAL) {
					if(voteTypeDTO == VoteType.UPVOTE) {
						post.setVoteCount(post.getVoteCount() + 1);
					} else if (voteTypeDTO == VoteType.DOWNVOTE) {
						post.setVoteCount(post.getVoteCount() - 1);
					}
				} else {
					if(voteTypeDTO == VoteType.UPVOTE) {
						post.setVoteCount(post.getVoteCount() + 2);
					} else if (voteTypeDTO == VoteType.DOWNVOTE) {
						post.setVoteCount(post.getVoteCount() - 2);
					}
				}
				vote.setVoteType(voteTypeDTO);
			}
		} 
		else {
			vote = Vote.builder()
								.voteType(voteTypeDTO)
								.user(authenticationService.getCurrentUser())
								.post(post)
							.build();
			if(voteTypeDTO == VoteType.UPVOTE) {
				post.setVoteCount(post.getVoteCount() + 1);
			} else if (voteTypeDTO == VoteType.DOWNVOTE) {
				post.setVoteCount(post.getVoteCount() - 1);
			}
		}
		postService.savePost(post);
		return voteRepo.save(vote);
	}
	
	@Override
	public VoteDTO saveVoteDTO(VoteDTO voteDTO) {
		Post post = postService.findPostModelById(voteDTO.getPostId());
		User user = authenticationService.getCurrentUser();
		VoteType voteTypeDTO = voteDTO.getVoteType();
		Optional<Vote> voteOptional = voteRepo.findByPostAndUser(post, user);
		
		Vote vote = null;
		
		if(voteOptional.isPresent()) {
			vote = voteOptional.get();
			if(vote.getVoteType() == voteTypeDTO) {
				vote.setVoteType(VoteType.NEUTRAL);
				if(voteTypeDTO == VoteType.UPVOTE) {
					post.setVoteCount(post.getVoteCount() - 1);
				} else if (voteTypeDTO == VoteType.DOWNVOTE) {
					post.setVoteCount(post.getVoteCount() + 1);
				}
			} else {
				
				if(vote.getVoteType() == VoteType.NEUTRAL) {
					if(voteTypeDTO == VoteType.UPVOTE) {
						post.setVoteCount(post.getVoteCount() + 1);
					} else if (voteTypeDTO == VoteType.DOWNVOTE) {
						post.setVoteCount(post.getVoteCount() - 1);
					}
				} else {
					if(voteTypeDTO == VoteType.UPVOTE) {
						post.setVoteCount(post.getVoteCount() + 2);
					} else if (voteTypeDTO == VoteType.DOWNVOTE) {
						post.setVoteCount(post.getVoteCount() - 2);
					}
				}
				vote.setVoteType(voteTypeDTO);
			}
		} 
		else {
			vote = Vote.builder()
								.voteType(voteTypeDTO)
								.user(authenticationService.getCurrentUser())
								.post(post)
							.build();
			if(voteTypeDTO == VoteType.UPVOTE) {
				post.setVoteCount(post.getVoteCount() + 1);
			} else if (voteTypeDTO == VoteType.DOWNVOTE) {
				post.setVoteCount(post.getVoteCount() - 1);
			}
		}
		postService.savePost(post);
		voteRepo.save(vote);
		return buildVoteDTO(vote);
	}
	
	private VoteDTO buildVoteDTO(Vote vote) {
		return VoteDTO.builder()
						  .postId(vote.getPost().getId())
						  .voteType(vote.getVoteType())
					  .build();
	}

	
}
