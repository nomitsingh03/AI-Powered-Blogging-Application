package com.shinom.blogging.dto;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shinom.blogging.entities.Role;
import com.shinom.blogging.helper.MustBeVerified;

import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class UserDto {
	
	private int userId;
	@NotBlank(message = "Name is required")
	@Size(min = 3, max=20 , message = "Name must be between 5-20 characters")
	private String name;
	
	@NotBlank(message = "Email is required!")
	@Email(message="Enter a valid email (example@gmail.com)" , regexp = "^[a-zA-Z0-9]+[\\w-.]*[a-zA-Z0-9]+[@][a-z]+[.][a-z]{3,5}$")
//	@Pattern(regexp = "^[a-zA-Z0-9]+[\\w-.]*[a-zA-Z0-9]+[@][a-z]+[.][a-z]{3,5}$")
	private String email;
	
	private String password;
	
	private String imageName;
	
	private String provider; 
	
	private boolean deactivate;
	
	private boolean archive;
	
	@Pattern(regexp = "^[1-9][0-9]{9}$", message = "Mobile number must be 10 digits long and cannot start with zero")
	@Size(min = 10, max = 10, message = "Mobile number must be exactly 10 digits long")
	private String mobileNumber;
	
	@NotEmpty(message = "At least one interest must be selected")
	private Set<String> interests = new HashSet<>();
	
	@MustBeVerified(message = "Email Verification is required to register")
	private boolean verified =false;
	
	private String otp;
//	@NotBlank
//	@Size(min=100, max=500)
	private String about;
	private Set<RoleDto> roles;
	
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
	
//	@NotBlank(message = "Pick your role")
//	Role role;
	
}
