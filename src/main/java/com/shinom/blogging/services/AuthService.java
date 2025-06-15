package com.shinom.blogging.services;

import com.shinom.blogging.dto.ForgotPassword;
import com.shinom.blogging.dto.LoginDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.dto.UserRegisterDto;

public interface AuthService {

	String login(LoginDto dto) throws Exception;
	
	String sendOtp(UserDto dto);
	
	String sendOtp(ForgotPassword dto);
	
}
