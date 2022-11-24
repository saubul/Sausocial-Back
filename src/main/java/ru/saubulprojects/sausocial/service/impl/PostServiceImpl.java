package ru.saubulprojects.sausocial.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.PostDTO;
import ru.saubulprojects.sausocial.dto.PostRequest;
import ru.saubulprojects.sausocial.entity.Comment;
import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.exception.SausocialException;
import ru.saubulprojects.sausocial.repository.CommentRepository;
import ru.saubulprojects.sausocial.repository.PostRepository;
import ru.saubulprojects.sausocial.service.PostService;
import ru.saubulprojects.sausocial.service.SubredditService;
import ru.saubulprojects.sausocial.service.UserService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	
	private final PostRepository postRepo;
	private final SubredditService subredditService;
	private final UserService userService;
	private final CommentRepository commentRepository;

	
	@Override
	public PostDTO findPostById(Long id) {
		Optional<Post> postOptional = postRepo.findById(id);
		Post post = postOptional.orElseThrow(() -> new SausocialException(String.format("Post with ID: %s not found", id)));
		return PostDTO.buildPostDTO(post);
	}

	@Override
	public PostDTO savePost(PostDTO postDTO) {
		Post post = Post.builder()
							.postName(postDTO.getPostName())
							.text(postDTO.getText())
							.user(userService.findUserByUsername(postDTO.getUsername()))
							.subreddit(subredditService.findSubredditByName(postDTO.getSubredditName()))
							.voteCount(0)
						.build();
		postRepo.save(post);
		return postDTO;
	}
	
	@Override
	public PostRequest savePost(PostRequest postRequest) {
		Post post = Post.builder()
							.postName(postRequest.getPostName())
							.url(postRequest.getUrl())
							.text(postRequest.getDescription())
							.user(userService.findUserByUsername(postRequest.getUsername()))
							.subreddit(subredditService.findSubredditByName(postRequest.getSubredditName()))
							.voteCount(0)
						.build();
		postRepo.save(post);
		return postRequest;
	}

	@Override
	public List<Post> findPostsBySubredditId(Long id) {
		
		return postRepo.findAllBySubreddit(subredditService.findSubredditById(id));
	}

	@Override
	public Post savePost(Post post) {
		return postRepo.save(post);
	}

	@Override
	public List<PostDTO> getPosts() {
		return postRepo.findAll().stream().map(post -> PostDTO.buildPostDTO(post)).collect(Collectors.toList());
	}

	@Override
	public Post findPostModelById(Long id) {
		Optional<Post> postOptional = postRepo.findById(id);
		Post post = postOptional.orElseThrow(() -> new SausocialException(String.format("Post with ID: %s not found", id)));
		return post;
	}

	@Override
	public List<PostDTO> findPostsDTOBySubredditId(Long id) {
		return postRepo.findAllBySubreddit(subredditService.findSubredditById(id)).stream().map(post -> PostDTO.buildPostDTO(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostsByUser(String username) {
		
		return postRepo.findAllByUser(userService.findUserByUsername(username)).stream().map(post -> PostDTO.buildPostDTO(post)).collect(Collectors.toList());
	}

	@Override
	public void deletePostById(Long postId) {
		
		postRepo.deleteById(postId);
	}

}
