package com.shinom.blogging.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDto(
		@NotBlank(message = "Email is required!")
		@Email(message="Enter a valid email (like example123@gmail.com)" , regexp = "^[a-zA-Z0-9]+[\\w-.]*[a-zA-Z0-9]+[@][a-z]+[.][a-z]{3,5}$")
		String username, 
		
		String password) {

}
