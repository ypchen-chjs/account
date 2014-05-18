package com.cymmetrik.account.email;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface AccountEmailService {
	public void sendMail(String to,String subject,String htmlText) throws AccountEmailException;
	public void sendMailWithVelocity(SimpleMailMessage msg,String templateName, Map<String, Object> model);
}
