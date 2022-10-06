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
import ru.saubulprojects.sausocial.dto.CommentDTO;
import ru.saubulprojects.sausocial.entity.Comment;
import ru.saubulprojects.sausocial.service.CommentService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;

	@PostMapping("/create")
	public HttpEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
		
		return new ResponseEntity<>(commentService.saveComment(commentDTO), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getComments/{postId}")
	public HttpEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
		
		return new ResponseEntity<>(commentService.findAllCommentsByPostId(postId), HttpStatus.OK);
		
	}
	
}