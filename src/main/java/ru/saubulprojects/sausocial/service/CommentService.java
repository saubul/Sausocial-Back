package ru.saubulprojects.sausocial.service;

import java.util.List;

import ru.saubulprojects.sausocial.dto.CommentDTO;
import ru.saubulprojects.sausocial.entity.Comment;

public interface CommentService {

	CommentDTO saveComment(CommentDTO commentDTO);

	List<CommentDTO> findAllCommentsByPostId(Long postId);

	
}
