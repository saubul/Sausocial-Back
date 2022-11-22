package ru.saubulprojects.sausocial.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.saubulprojects.sausocial.entity.NotificationEmail;
import ru.saubulprojects.sausocial.exception.SausocialException;
import ru.saubulprojects.sausocial.service.MailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
	
	private final JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String from;
	
	@Override
	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		
		MimeMessagePreparator mimeMessagePreparator = (mimeMessage) -> {
				
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(notificationEmail.getRecipient());
			mimeMessageHelper.setSubject(notificationEmail.getSubject());
			mimeMessageHelper.setText(notificationEmail.getBody());
			
		};
		try {
			javaMailSender.send(mimeMessagePreparator);
			log.info("Activation email sent.");
		} catch (MailException e) {
			log.error("Exception occured while sending a mail to: " + notificationEmail.getRecipient());
			throw new SausocialException("Exception occured while sending a mail to: " + notificationEmail.getRecipient(), e);
		}
		
	}

}
