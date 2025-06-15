package com.shinom.blogging.services;

import com.shinom.blogging.dto.PostDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.Post;

public interface EmailServices {
	
	void userRegistrationEmail(UserDto email);
	void userForgotPasswordEmail(String email);
	void userPostCreationEmail(Integer userId, PostDto postDto);
	void userPostUpdateEmail(Post post);
	void userPostDeleteEmail(String email);
	void postCreationFailedEmail(Integer userID);
	void commonEmail(String email, String subject, String body);

}
