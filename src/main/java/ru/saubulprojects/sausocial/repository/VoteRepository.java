package ru.saubulprojects.sausocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.saubulprojects.sausocial.entity.Post;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	Optional<Vote> findByPostAndUser(Post post, User user);
	
}
