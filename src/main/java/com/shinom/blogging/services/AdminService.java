package com.shinom.blogging.services;

import java.util.List;

import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.LoginDetails;
import com.shinom.blogging.helper.LoginDetailsResponse;

public interface AdminService {
	
	public List<UserDto> getAllUsers();
	
	List<LoginDetailsResponse> getAllLoginDetails();
}
