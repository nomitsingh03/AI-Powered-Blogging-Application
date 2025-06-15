package com.shinom.blogging.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasswordConfirmation{
	
	private String email;
	private String password;
	private String confirmPassword;
	

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	@JsonProperty
	public void setPassword(
			@NotBlank(message = "Enter password")
			@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,14}$", message = "Password must be contains (upperCase, Lowercase, Number, Special characters) !")
			@Size(min = 8, max=14, message = "Password must be between 8-14 and contains (upperCase, Lowercase, Number, Special characters) !.")
			String password) {
		this.password=password;
	}

}
