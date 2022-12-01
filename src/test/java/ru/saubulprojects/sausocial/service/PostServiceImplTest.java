package ru.saubulprojects.sausocial.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ru.saubulprojects.sausocial.repository.CommentRepository;
import ru.saubulprojects.sausocial.repository.PostRepository;
import ru.saubulprojects.sausocial.service.impl.PostServiceImpl;

class PostServiceImplTest {

	@Mock
	private PostRepository postRepo;
	@Mock
	private SubredditService subredditService;
	@Mock
	private UserService userService;
	@Mock
	private CommentRepository commentRepository;
	
	@InjectMocks
	private PostServiceImpl postServiceImpl;
	
	@Test
	void testFindPostById() {
		fail("Not yet implemented");
	}

	@Test
	void testSavePostPostDTO() {
		fail("Not yet implemented");
	}

	@Test
	void testSavePostPostRequest() {
		fail("Not yet implemented");
	}

	@Test
	void testFindPostsBySubredditId() {
		fail("Not yet implemented");
	}

	@Test
	void testSavePostPost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPosts() {
		fail("Not yet implemented");
	}

	@Test
	void testFindPostModelById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPostsByUser() {
		fail("Not yet implemented");
	}

	@Test
	void testDeletePostById() {
		fail("Not yet implemented");
	}

}
