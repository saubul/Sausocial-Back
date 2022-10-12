package ru.saubulprojects.sausocial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.CommentDTO;
import ru.saubulprojects.sausocial.entity.Comment;
import ru.saubulprojects.sausocial.entity.NotificationEmail;
import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.repository.CommentRepository;
import ru.saubulprojects.sausocial.service.CommentService;
import ru.saubulprojects.sausocial.service.MailService;
import ru.saubulprojects.sausocial.service.PostService;
import ru.saubulprojects.sausocial.service.UserService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepo;
	private final PostService postService;
	private final MailService mailService;
	private final UserService userService;
	
	@Override
	public CommentDTO saveComment(CommentDTO commentDTO) {
		Comment comment = Comment.builder()
									 .text(commentDTO.getText())
									 .user(userService.findUserByUsername(commentDTO.getUsername()))
									 .post(postService.findPostModelById(commentDTO.getPostId()))
								 .build();
		
		Post post = postService.findPostModelById(comment.getPost().getId());
		sendCommentNotification(comment.getUser().getUsername() + " posted a comment to your post.", post.getUser());
		commentRepo.save(comment);
		return commentDTO;
	}

	@Override
	public List<CommentDTO> findAllCommentsByPostId(Long postId) {
		return commentRepo.findAllByPost(postService.findPostModelById(postId))
						  .stream().map(comment -> {return this.buildCommentDTO(comment);})
						  .collect(Collectors.toList());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(NotificationEmail.builder()
											  	  .recipient(user.getEmail())
											  	  .subject("Post commented")
											  	  .body(message)
											  .build());
	}
	
	
	private CommentDTO buildCommentDTO(Comment comment) {
		return CommentDTO.builder()
							 .id(comment.getId())
						 	 .text(comment.getText())
						 	 .username(comment.getUser().getUsername())
						 	 .postId(comment.getPost().getId())
						 	 .duration(comment.getDateCreated().toString())
						 .build();
	}
}
