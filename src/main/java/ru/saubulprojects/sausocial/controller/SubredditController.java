package ru.saubulprojects.sausocial.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.SubredditDTO;
import ru.saubulprojects.sausocial.entity.Subreddit;
import ru.saubulprojects.sausocial.service.SubredditService;

@RestController
@RequestMapping("/api/subreddit")
@RequiredArgsConstructor
public class SubredditController {
	
	private final SubredditService subredditService;
	
	@PostMapping("/create")
	public HttpEntity<Subreddit> createSubreddit(@RequestBody SubredditDTO subredditDTO) {
			
		return new ResponseEntity<>(subredditService.saveSubreddit(subredditDTO), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getSubreddits")
	public HttpEntity<Page<SubredditDTO>> getSubredditPage(@RequestParam("pageNo") int pageNo) {
		
		return new ResponseEntity<>(subredditService.getSubredditPage(pageNo), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllSubreddits")
	public HttpEntity<List<SubredditDTO>> getAllSubreddits() {
		
		return new ResponseEntity<>(subredditService.getAllSubreddits(), HttpStatus.OK);
	}
	
}
