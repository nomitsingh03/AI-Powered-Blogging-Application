package com.shinom.blogging.services;

import java.util.List;

import com.shinom.blogging.entities.LoginDetails;

public interface LoginDetailsService {
	
	void saveRegistrationDetails(int userId);
	void saveLoginDetails(int userId);
	
	List<LoginDetails> getAllDetails();
	
	List<LoginDetails> getAllByUserId(int id);

}
