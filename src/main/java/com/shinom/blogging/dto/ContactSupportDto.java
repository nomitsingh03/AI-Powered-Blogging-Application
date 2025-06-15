package com.shinom.blogging.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactSupportDto {
	
	String name;
	String email;
	int mobile;
	String description;

}
