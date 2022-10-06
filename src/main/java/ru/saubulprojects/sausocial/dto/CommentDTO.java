package ru.saubulprojects.sausocial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

	private Long id;
	private String text;
	private Long userId;
	private Long postId;
	private String dateCreated;
	
}
