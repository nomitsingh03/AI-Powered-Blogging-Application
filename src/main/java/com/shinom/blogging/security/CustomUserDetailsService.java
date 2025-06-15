package com.shinom.blogging.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shinom.blogging.entities.User;
import com.shinom.blogging.exceptions.ResourceNotFoundException;
import com.shinom.blogging.repositories.UserRepo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user = userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User ", " Email "+username, 0));
		return user;
	}


	
}
