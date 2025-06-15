package com.shinom.blogging.servicesImplements;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.LoginDetails;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.helper.LoginDetailsResponse;
import com.shinom.blogging.repositories.LoginDetailsRepo;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.AdminService;
import com.shinom.blogging.services.LoginDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImplements implements AdminService {
	
	@Autowired
	private final UserRepo userRepo;
	
	@Autowired
	private final ModelMapper modelMapper;
	
	@Autowired
	private LoginDetailsService loginDetailsService;
	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		 var collect = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<LoginDetailsResponse> getAllLoginDetails() {
		// TODO Auto-generated method stub
		
		List<LoginDetails> list = this.loginDetailsService.getAllDetails();
		List<LoginDetailsResponse> result = new ArrayList<>();
		for(LoginDetails u: list){
			User dummy = new User();
			dummy.setUserId(1111111);
			dummy.setName("User Not Found");
			dummy.setEmail("User Not Found");
			
			User user = this.userRepo.findById(u.getUserId()).orElse(dummy);
			String formattedDate = null;
			String formattedDate2 = null;
			if(u.getAccountCreationTime()!=null)
				formattedDate = u.getAccountCreationTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
			if(u.getLoginTime()!=null)
				formattedDate2 = u.getLoginTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
			LoginDetailsResponse response = LoginDetailsResponse.builder().registrationStamp(formattedDate).id(u.getId())
				.loginStamp(formattedDate2).email(user.getEmail())
				.name(user.getName())
				.build();
			result.add(response);
			
			System.out.println(result.get(result.size()-1));
		}
		return result;
	}

}
