package ru.saubulprojects.sausocial.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.SubscriptionDTO;
import ru.saubulprojects.sausocial.entity.Subscription;
import ru.saubulprojects.sausocial.entity.User;
import ru.saubulprojects.sausocial.repository.SubscriptionRepository;
import ru.saubulprojects.sausocial.service.SubscriptionService;
import ru.saubulprojects.sausocial.service.UserService;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{

	private final SubscriptionRepository subscriptionRepository;
	private final UserService userService;
	
	@Override
	public SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO) {
		Subscription subscription = this.buildSubscription(subscriptionDTO);
		subscriptionRepository.save(subscription);
		return subscriptionDTO;
	}
	
	private Subscription buildSubscription(SubscriptionDTO subscriptionDTO) {
		Subscription subscription = Subscription.builder()
													.owner(userService.findUserByUsername(subscriptionDTO.getOwnerUsername()))
													.subscriber(userService.findUserByUsername(subscriptionDTO.getSubscriberUsername()))
												.build();
		return subscription;
	}

	@Override
	public void deleteSubscription(SubscriptionDTO subscriptionDTO) {
		User owner = userService.findUserByUsername(subscriptionDTO.getOwnerUsername());
		User subscriber = userService.findUserByUsername(subscriptionDTO.getSubscriberUsername());
		Subscription subscription = subscriptionRepository.findByOwnerAndSubscriber(owner, subscriber).orElse(null);
		subscriptionRepository.delete(subscription);
	}

	@Override
	public boolean isSubscribed(SubscriptionDTO subscriptionDTO) {
		User owner = userService.findUserByUsername(subscriptionDTO.getOwnerUsername());
		User subscriber = userService.findUserByUsername(subscriptionDTO.getSubscriberUsername());
		Subscription subscription = subscriptionRepository.findByOwnerAndSubscriber(owner, subscriber).orElse(null);
		if(subscription == null) {
			return false;
		}
		return true;
	}

}
