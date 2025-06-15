package com.shinom.blogging.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountDeactivate extends RuntimeException {
	
	public UserAccountDeactivate(String username) {
		super(String.format("Your account is deactivated : %s", username));
	}

}
