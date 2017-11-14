package com.bridgeit.toDoNotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailerServiceImpl implements IMailerService {
	
	@Autowired
	private MailSender mailSender;
	
	public void sendMail(String mail, String myMessage) {
			
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();  
	        
			simpleMailMessage.setFrom("hemanthreddysunny@gmail");  
	        simpleMailMessage.setTo(mail);  
	        simpleMailMessage.setSubject("Validate Email");  
	        simpleMailMessage.setText(myMessage);  
	       
	        //sending message to user  
	       
	        mailSender.send(simpleMailMessage);     
		
	}

}
