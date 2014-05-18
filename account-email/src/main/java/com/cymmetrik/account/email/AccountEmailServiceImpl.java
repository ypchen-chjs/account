package com.cymmetrik.account.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class AccountEmailServiceImpl implements AccountEmailService{
	private JavaMailSender javaMailSender;
	private VelocityEngine velocityEngine;
	private String systemEmail;
	
	@Override
	public void sendMail(String to, String subject, String htmlText)
			throws AccountEmailException {
		
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
			
			try {
				msgHelper.setFrom(systemEmail);
				msgHelper.setTo(to);
				msgHelper.setSubject(subject);
				msgHelper.setText(htmlText,true);
				
				javaMailSender.send(msg);
			} catch (MessagingException e) {
				throw new AccountEmailException("Faild to send mail",e);
			}
	}
	
	@Override
	public void sendMailWithVelocity(SimpleMailMessage msg,String templateName, Map<String, Object> model){

        String result = null;

        try {
            result =VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,templateName, model);
        } catch (VelocityException e) {
            e.printStackTrace();
        }

        msg.setText(result); 
        
        sendHTML(msg);
    
	}
	public void sendHTML(SimpleMailMessage msg)
    {
    	MimeMessage mimeMsg = null;
    	try{
    		mimeMsg = ((JavaMailSenderImpl) javaMailSender).createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
    		helper.setTo(msg.getTo());
    		
    		if(msg.getSubject()!=null)
    			helper.setSubject(msg.getSubject());
    		
    		if(msg.getFrom() != null)
    			helper.setFrom(msg.getFrom());
    		
    		if(msg.getBcc()!=null)
    			helper.setBcc(msg.getBcc());
    			
    		helper.setText(msg.getText(),true);
    			
    		((JavaMailSenderImpl) javaMailSender).send(mimeMsg);
    	}catch(MessagingException ex){
    	}
    }
	
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public String getSystemEmail() {
		return systemEmail;
	}
	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
