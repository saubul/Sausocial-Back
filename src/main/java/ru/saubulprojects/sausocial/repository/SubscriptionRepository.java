package ru.saubulprojects.sausocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.saubulprojects.sausocial.entity.Subscription;
import ru.saubulprojects.sausocial.entity.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	Optional<Subscription> findByOwnerAndSubscriber(User owner, User subscriber); 
	
}
