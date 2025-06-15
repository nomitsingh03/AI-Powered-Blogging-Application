package com.shinom.blogging.helper;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginDetailsResponse {
	int id;
	String name;
	String email;
	String registrationStamp;
	String loginStamp;

}
