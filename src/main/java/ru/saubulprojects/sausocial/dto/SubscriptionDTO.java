package ru.saubulprojects.sausocial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.Subscription;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {
	
	private String ownerUsername;
	private String subscriberUsername;
	
	public static SubscriptionDTO buildSubscriptionDTO(Subscription subscription) {
		SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
															 .ownerUsername(subscription.getOwner().getUsername())
															 .subscriberUsername(subscription.getSubscriber().getUsername())
														 .build();
		return subscriptionDTO;
	}
}
