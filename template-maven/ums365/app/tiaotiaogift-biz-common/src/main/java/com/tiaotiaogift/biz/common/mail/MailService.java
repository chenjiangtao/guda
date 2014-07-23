package com.tiaotiaogift.biz.common.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	public void sendMail(String subject,String content,String to) throws MessagingException{
		MimeMessage mailMessage = javaMailSender.createMimeMessage(); 
	    MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage); 
	    messageHelper.setTo(to); 
	    messageHelper.setFrom(javaMailSender.getUsername()); 
	    messageHelper.setSubject(subject); 
	    messageHelper.setText(content,true); 
	    javaMailSender.send(mailMessage); 

	}

}
