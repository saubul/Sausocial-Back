package ru.saubulprojects.sausocial.service;

import ru.saubulprojects.sausocial.entity.NotificationEmail;

public interface MailService {

	void sendMail(NotificationEmail notificationEmail);
	
}
