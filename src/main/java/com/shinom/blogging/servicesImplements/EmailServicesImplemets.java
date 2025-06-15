package com.shinom.blogging.servicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.PostDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.Post;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.EmailServices;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicesImplemets implements EmailServices {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserRepo userRepository;

	@Override
	public void userRegistrationEmail(UserDto dto) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(dto.getEmail());
			helper.setSubject("Welcome to Blogging Application!");
			String content = "<h1>Welcome to Blogging Application</h1>"
                    + "<p>Dear " + dto.getName() + ",</p><br>"
                    + "<p>Thank you for registering with us. We are excited to have you on board!</p><br>"
                    +"<p><strong>username: </strong> "+dto.getEmail()+" </p>"
                    + "<p><strong>Password: </strong> "+dto.getPassword()+" </p>"
                    + "<p>Best regards,</p>"
                    + "<p>The Blogging Application Team</p>";
			helper.setText(content, true); // Set the email content as HTML	
			mailSender.send(message); // Send the email
			} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void userForgotPasswordEmail(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userPostCreationEmail(Integer userId, PostDto postDto) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).get();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getEmail());
			helper.setSubject("Your Post Has Been Created!");

	        // HTML content for the email
	        String content = "<!DOCTYPE html>" +
	                "<html>" +
	                "<head>" +
	                "<style>" +
	                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
	                ".email-container { max-width: 600px; margin: 20px auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
	                ".header { background-color: #007bff; color: #ffffff; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }" +
	                ".content { padding: 20px; text-align: center; }" +
	                ".footer { margin-top: 20px; font-size: 12px; color: #888888; text-align: center; }" +
	                "</style>" +
	                "</head>" +
	                "<body>" +
	                "<div class='email-container'>" +
	                "<div class='header'>" +
	                "<h1>Post Created Successfully</h1>" +
	                "</div>" +
	                "<div class='content'>" +
	                "<p>Dear User,</p>" +
	                "<p>Your post titled <strong>" + postDto.getTitle() + "</strong> has been created successfully!</p>" +
	                "<p>Thank you for using our platform.</p>" +
	                "</div>" +
	                "<div class='footer'>" +
	                "<p>&copy; 2023 Blogging Application. All rights reserved.</p>" +
	                "</div>" +
	                "</div>" +
	                "</body>" +
	                "</html>";

	        helper.setText(content, true); // Set the email content as HTML
	        mailSender.send(message); // Send the email
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	@Override
	public void userPostUpdateEmail(Post post) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(post.getUser().getEmail());
			helper.setSubject("Your Post Has Been Updated!");

	        // HTML content for the email
	        String content = "<!DOCTYPE html>" +
	                "<html>" +
	                "<head>" +
	                "<style>" +
	                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
	                ".email-container { max-width: 600px; margin: 20px auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
	                ".header { background-color: #007bff; color: #ffffff; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }" +
	                ".content { padding: 20px; text-align: center; }" +
	                ".footer { margin-top: 20px; font-size: 12px; color: #888888; text-align: center; }" +
	                "</style>" +
	                "</head>" +
	                "<body>" +
	                "<div class='email-container'>" +
	                "<div class='header'>" +
	                "<h1>Post Updated Successfully</h1>" +
	                "</div>" +
	                "<div class='content'>" +
	                "<p>Dear User,</p>" +
	                "<p>Your post titled <strong>" + post.getTitle() + "</strong> has been updated successfully!</p>" +
	                "<p>Thank you for using our platform.</p>" +
	                "</div>" +
	                "<div class='footer'>" +
	                "<p>&copy; 2025 Blogging Application. All rights reserved.</p>" +
	                "</div>" +
	                "</div>" +
	                "</body>" +
	                "</html>";

	        helper.setText(content, true); // Set the email content as HTML
	        mailSender.send(message); // Send the email
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	@Override
	public void userPostDeleteEmail(String email) {
		

	}

	@Override
	public void postCreationFailedEmail(Integer userId) {
		User user = this.userRepository.findById(userId).get();
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getEmail());
			helper.setSubject("Your content cannot be posted!");

	        // HTML content for the email
	        String content = "<!DOCTYPE html>" +
	                "<html>" +
	                "<head>" +
	                "<style>" +
	                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
	                ".email-container { max-width: 600px; margin: 20px auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
	                ".header { background-color: #007bff; color: #ffffff; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }" +
	                ".content { padding: 20px; text-align: center; }" +
	                ".footer { margin-top: 20px; font-size: 12px; color: #888888; text-align: center; }" +
	                "</style>" +
	                "</head>" +
	                "<body>" +
	                "<div class='email-container'>" +
	                "<div class='header'>" +
	                "<h1>Post Failed to create.</h1>" +
	                "</div>" +
	                "<div class='content'>" +
	                "<p>Dear User,</p>" +
	                "<p>Your post content has more toxicity , which violates the platform rules and policies !</p>" +
	                "<p>Thank you for using our platform.</p>" +
	                "</div>" +
	                "<div class='footer'>" +
	                "<p>&copy; 2025 Blogging Application. All rights reserved.</p>" +
	                "</div>" +
	                "</div>" +
	                "</body>" +
	                "</html>";

	        helper.setText(content, true); // Set the email content as HTML
	        mailSender.send(message); // Send the email
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	@Override
	public void commonEmail(String email, String subject, String body) {
		// TODO Auto-generated method stub

	}

}
