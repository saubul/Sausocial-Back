package ru.saubulprojects.sausocial.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.VoteDTO;
import ru.saubulprojects.sausocial.entity.Vote;
import ru.saubulprojects.sausocial.service.VoteService;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping("/vote")
	public HttpEntity<VoteDTO> vote(@RequestBody VoteDTO voteDTO) {
		
		return new ResponseEntity<>(voteService.saveVoteDTO(voteDTO), HttpStatus.CREATED);
		
	}
	
}
