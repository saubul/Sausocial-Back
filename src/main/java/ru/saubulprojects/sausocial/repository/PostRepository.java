package ru.saubulprojects.sausocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.entity.Subreddit;
import ru.saubulprojects.sausocial.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findAllBySubreddit(Subreddit subreddit);
	
	List<Post> findAllByUser(User user); 
	
	@Query("select p from Post p join Subscription s on p.user = s.subscriber where s.owner = :user")
	List<Post> findAllByUserSubscribed(User user);

	//@Query("select * from Post p join Subscription s on p.user = s.subscriber where s.owner = :user")
	//List<Post> findAllByUserLiked(User user);
	
	List<Post> findAllByTextContainingIgnoreCase(String string);
}
