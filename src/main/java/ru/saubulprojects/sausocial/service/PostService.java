package ru.saubulprojects.sausocial.service;

import java.util.List;

import ru.saubulprojects.sausocial.dto.PostDTO;
import ru.saubulprojects.sausocial.entity.Post;

public interface PostService {

	PostDTO findPostById(Long id);
	Post findPostModelById(Long id);

	PostDTO savePost(PostDTO postDTO);
	
	Post savePost(Post post);

	List<Post> findPostsBySubredditId(Long id);
	List<PostDTO> findPostsDTOBySubredditId(Long id);

	List<PostDTO> getPosts();
	
}
