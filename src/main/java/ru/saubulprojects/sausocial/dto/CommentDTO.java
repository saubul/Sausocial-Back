package ru.saubulprojects.sausocial.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

	private String text;
	private String username;
	private Long postId;
	private LocalDateTime dateCreated;
	
	public static CommentDTO buildCommentDTO(Comment comment) {
		CommentDTO commentDTO = CommentDTO.builder()
											  .text(comment.getText())
											  .username(comment.getUser().getUsername())
											  .postId(comment.getPost().getId())
											  .dateCreated(comment.getDateCreated())
										  .build();
		return commentDTO;
	}
	
}
