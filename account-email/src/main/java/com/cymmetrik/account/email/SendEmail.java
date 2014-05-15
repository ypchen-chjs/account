package com.cymmetrik.account.email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;

public class SendEmail {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
	static AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");

	public SendEmail() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void sendMail(SimpleMailMessage msg,String template,Map<String, Object> model) throws Exception {
		accountEmailService.sendMailWithVelocity(msg, template, model);
	}

}
