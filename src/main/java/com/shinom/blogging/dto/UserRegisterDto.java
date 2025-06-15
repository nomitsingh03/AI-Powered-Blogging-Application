package com.shinom.blogging.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto (
		
		@NotBlank(message = "Name must be between 3-20 characters !")
		@Size(min = 3, max=20)
		String name,
		@NotBlank(message = "Email is required!")
		@Email(message="Enter a valid email !" , regexp = "^[a-zA-Z0-9]+[\\w-.]*[a-zA-Z0-9]+[@][a-z]+[.][a-z]{3,5}$")
		String email,
		@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,14}$",  message = "Password must contains at least one from each category (uppercase, lowercase, number, and special character)")
		@Size(min = 8, max=14)
		String password){
	
}
