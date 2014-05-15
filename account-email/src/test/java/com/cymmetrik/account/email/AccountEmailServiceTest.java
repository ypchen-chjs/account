package com.cymmetrik.account.email;

import static org.junit.Assert.*;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {

	private GreenMail greenMail;
	@Before
	public void startMailServer() throws Exception {
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("test@juvenxu.com", "12345678");
		greenMail.start();
	}

	@Test
	public void testSendMail() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");
		String subject = "Test Subject";
		String htmlText = "<h3>Test</h3>";
		
		accountEmailService.sendMail("yiping.chen@cymmetrik.com", subject, htmlText);
		
//		SimpleMailMessage msg = new SimpleMailMessage();
//		msg.setFrom("yiping.chen@cymmetrik.com");
//		msg.setTo("yiping.chen@cymmetrik.com");
//		msg.setSubject("測試");
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("messages1", "ABC");
//		model.put("messages2", "XYZ");
//		model.put("note1", " 您好 您有一筆 ");
//		model.put("note2", "項目，需要審核。 ");
//		
//		accountEmailService.sendMailWithVelocity(msg, "mail.vm", model);
		greenMail.waitForIncomingEmail(2000, 1);
		
		Message[] msgs = greenMail.getReceivedMessages();
		assertEquals(1,msgs.length);
		assertEquals(subject,msgs[0].getSubject());
		assertEquals(htmlText,GreenMailUtil.getBody(msgs[0]).trim());
//	
	}
	
	@After
	public void stopMailServer() throws Exception{
		greenMail.stop();
	}

}
