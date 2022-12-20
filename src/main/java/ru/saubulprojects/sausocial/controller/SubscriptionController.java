package ru.saubulprojects.sausocial.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.SubscriptionDTO;
import ru.saubulprojects.sausocial.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
	
	private final SubscriptionService subscriptionService;
	
	@PostMapping
	public HttpEntity<SubscriptionDTO> saveSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
		
		return new ResponseEntity<SubscriptionDTO>(subscriptionService.saveSubscription(subscriptionDTO), HttpStatus.OK);
	}
	
	@PostMapping("/isSubscribed")
	public HttpEntity<Boolean> isSubcribed(@RequestBody SubscriptionDTO subscriptionDTO) {
		return new ResponseEntity<Boolean>(subscriptionService.isSubscribed(subscriptionDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public HttpEntity<String> deleteSubscription(@RequestParam("ownerUsername") String ownerUsername,
														  @RequestParam("subscriberUsername") String subscriberUsername) {
		SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder().ownerUsername(ownerUsername).subscriberUsername(subscriberUsername).build();
		subscriptionService.deleteSubscription(subscriptionDTO);
		return new ResponseEntity<String>("DELETED",HttpStatus.OK);
	}
	
}
