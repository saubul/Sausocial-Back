package ru.saubulprojects.sausocial.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.PostDTO;
import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.service.PostService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping("/create")
	public HttpEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
		 return new ResponseEntity<>(postService.savePost(postDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public HttpEntity<PostDTO> getPost(@PathVariable("id") Long id) {
			
		return new ResponseEntity<>(postService.findPostById(id), HttpStatus.OK);
		
	}
	
	@GetMapping("/bySubreddit/{id}")
	public HttpEntity<List<PostDTO>> getPostsBySubreddit(@PathVariable("id") Long id) {
			
		return new ResponseEntity<>(postService.findPostsDTOBySubredditId(id), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllPosts")
	public HttpEntity<List<PostDTO>>getAllPosts(){
		return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
	}
	
}
