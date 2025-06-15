package com.shinom.blogging.servicesImplements;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinom.blogging.entities.LoginDetails;
import com.shinom.blogging.repositories.LoginDetailsRepo;
import com.shinom.blogging.services.LoginDetailsService;

@Service
public class LoginRegistrationDetailsImpl implements LoginDetailsService {

	@Autowired
	private LoginDetailsRepo repo;
	
	private static ZoneId zone = ZoneId.of("Asia/Kolkata");

	@Override
	public void saveRegistrationDetails(int userId) {
		// TODO Auto-generated method stub
		
	   LoginDetails details=new LoginDetails();
	   details.setUserId(userId);
	   details.setLoginTime(null);
	   details.setAccountCreationTime(LocalDateTime.now(zone));
	   this.repo.save(details);
//	    System.out.println(this.repo.save(details));
		
	}

	@Override
	public List<LoginDetails> getAllDetails() {
		// TODO Auto-generated method stub
//		System.out.println(this.repo.findAll());
		return this.repo.findAll();
	}

	@Override
	public List<LoginDetails> getAllByUserId(int id) {
		// TODO Auto-generated method stub
		return this.repo.findByUserId(id);
	}

	@Override
	public void saveLoginDetails(int userId) {
		// TODO Auto-generated method stub
		LoginDetails details=new LoginDetails();
		   details.setUserId(userId);
		   details.setAccountCreationTime(null);
		   details.setLoginTime(LocalDateTime.now(zone));
		   this.repo.save(details);
//		   System.out.println(this.repo.save(details));
	}

}
