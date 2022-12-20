package ru.saubulprojects.sausocial.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.PostDTO;
import ru.saubulprojects.sausocial.dto.PostRequest;
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
	
	@PostMapping("/create-post")
	public HttpEntity<PostRequest> createPost(@RequestBody PostRequest postRequest) {
		 return new ResponseEntity<>(postService.savePost(postRequest), HttpStatus.CREATED);
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
	public HttpEntity<List<PostDTO>> getAllPosts(){
		return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/getAllPostsByUser/{username}")
	public HttpEntity<List<PostDTO>> getAllPostsByUser(@PathVariable("username") String username){
		return new ResponseEntity<>(postService.getPostsByUser(username), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public HttpEntity<String> deletePostById(@RequestParam("postId") Long postId) {
		postService.deletePostById(postId);
		return new ResponseEntity<String>("DELETED", HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public HttpEntity<List<PostDTO>> getPostsByUserSubscribed(@RequestParam("username") String username,
															  @RequestParam("filterStatus") String filterStatus) {
		if(filterStatus.equals("subscribed")) {
			System.out.println("SUBSCRIBED--------------------------");
			return new ResponseEntity<List<PostDTO>>(postService.filterBySubscribed(username), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PostDTO>>(postService.filterBySubscribed(username), HttpStatus.OK);
		}
	}
	
	@GetMapping("/contains")
	public HttpEntity<List<PostDTO>> getPostsByStringContaining(@RequestParam("string") String string) {
		return new ResponseEntity<List<PostDTO>>(postService.findAllPostsByString(string), HttpStatus.OK);
	}
	
}
