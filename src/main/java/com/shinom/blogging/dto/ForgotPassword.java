package com.shinom.blogging.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class ForgotPassword{
	
		@NotBlank(message = "Email required!")
		@Email(message="Enter a valid email (like example123@gmail.com)" , regexp = "^[a-zA-Z0-9]+[\\w-.]*[a-zA-Z0-9]+[@][a-z]+[.][a-z]{3,5}$")
		private String email;
		private String sendOtp;		

}
