package com.example.emailHandl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.request.UserEmailDetails;

import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(UserEmailDetails emailDetails)
	{
		boolean status=false;
		try {
			
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message, true);//I think no need messageHelper
			
			String to=emailDetails.getEmail();
			String firstName=emailDetails.getFirstName();
			String lastName=emailDetails.getLastNama();
			String password=emailDetails.getPassword();
			String url="http://localhost:8080/loginForm/"+to+"";
			String link ="<a href="+url+">click on login</a>";
			
			helper.setTo(to);
			helper.setSubject("Unlock IES Account");
			String content = "<b>Hi "+firstName+" "+lastName+",</b><br>"
					+ "<i>Welcome to IES famaly , your registration is almost.<br>"
					+ "complete.Please unlock your account using below details</i><br>"
					+ "Temporary Password: "+password+"<br><br>"+link+"<br>"
							+ "Thanks<br>"
							+ "IES Team";
			//helper.setText(content, true);
			//helper.setText("<h2>Please Cleck the link</h2>", true);
			//helper.addAttachment(file.getName(), f);
			
			Multipart multipart = new MimeMultipart();
			//MimeBodyPart textPart = new MimeBodyPart();
			//textPart.setText(content, "utf-8" );
			
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent( content, "text/html; charset=utf-8" );
			
			//multipart.addBodyPart( textPart );
			multipart.addBodyPart( htmlPart );
			
			message.setContent( multipart );
			
			mailSender.send(message);
			
			status=true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return status;
	}

}
