package ru.saubulprojects.sausocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.saubulprojects.sausocial.entity.Comment;
import ru.saubulprojects.sausocial.entity.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPost(Post post);

}
