package com.shinom.blogging.servicesImplements;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.ForgotPassword;
import com.shinom.blogging.dto.LoginDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.AuthService;
import com.shinom.blogging.services.UserServices;
import com.shinom.blogging.utils.JwtUtils;

import jakarta.mail.internet.MimeMessage;


@Service
public class AuthServiceImplements implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServices userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public String login(LoginDto dto)  throws Exception{
		
		
			UserDto userDto = userService.getUserByEmail(dto.username());
			if(userDto==null) throw new Exception("User not found with this email : "+dto.username());
			else {
				if(!passwordEncoder.matches(dto.password(), userDto.getPassword())) {
					throw new BadCredentialsException("not found");
				}
			}
		
		
		var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
		
		var authenticate = authenticationManager.authenticate(authToken);
		
		return JwtUtils.generateToken(((UserDetails)(authenticate.getPrincipal())).getUsername());
	}

	@Override
	public String sendOtp(UserDto user) {
		
		User userDto = this.userRepo.findByEmail(user.getEmail()).orElse(null);
		if(userDto!=null) {
			return "user exist";
		}
		// TODO Auto-generated method stub
		String from = "singhnomit2002@gmail.com";
		String to = user.getEmail();

		String subject = "Email Verification : OTP";
		String content = "Dear [[name]],<br>" + "OTP to verify your email:<br>"
				+ "<h3>[[otp]]</h3>" + "Thank you,<br>" + "Regards <br> Shinom Blogging";

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(from, "Shinom");
			helper.setTo(to);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getName());
			String otpString = getRandomNumberString();
			content = content.replace("[[otp]]", otpString);

			helper.setText(content, true);

			mailSender.send(message);
			return otpString;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRandomNumberString() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}

	public String sendOtp(ForgotPassword data) {
		UserDto userDto = this.userService.getUserByEmail(data.getEmail());
		if(userDto==null) {
			return "user not exist";
		}
		// TODO Auto-generated method stub
		String from = "singhnomit2002@gmail.com";
		String to = data.getEmail();

		String subject = "Forgot Password : OTP";
		String content = "Dear [[name]],<br>" + "OTP to forgot your password:<br>"
				+ "<h3>[[otp]]</h3>" + "Thank you,<br>" + "Regards <br> Shinom Blogging";

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(from, "Shinom");
			helper.setTo(to);
			helper.setSubject(subject);

			content = content.replace("[[name]]", userDto.getName());
			String otpString = getRandomNumberString();
			content = content.replace("[[otp]]", otpString);

			helper.setText(content, true);

			mailSender.send(message);
			return otpString;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
