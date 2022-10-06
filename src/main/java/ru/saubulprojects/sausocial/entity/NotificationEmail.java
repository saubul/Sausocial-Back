package ru.saubulprojects.sausocial.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEmail {
	
	private String recipient;
	private String subject;
	private String body;
	
}
