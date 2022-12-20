package ru.saubulprojects.sausocial.service;

import ru.saubulprojects.sausocial.dto.SubscriptionDTO;

public interface SubscriptionService {
	
	SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO);
	
	void deleteSubscription(SubscriptionDTO subscriptionDTO);
	
	boolean isSubscribed(SubscriptionDTO subscriptionDTO);
	
}
