package com.shinom.blogging.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameNotFoundException extends RuntimeException {

	private String username;
	
	public UserNameNotFoundException(String username) {
		super(String.format("Email not registered : %s", username));
	}

	
}
