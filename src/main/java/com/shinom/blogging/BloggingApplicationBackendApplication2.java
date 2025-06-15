package com.shinom.blogging;

import java.util.List;
import java.util.Set;

import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shinom.blogging.dto.RoleDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.Role;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.helper.WebConstants;
import com.shinom.blogging.repositories.RoleRepo;
import com.shinom.blogging.services.UserServices;


@SpringBootApplication
public class BloggingApplicationBackendApplication2 implements CommandLineRunner{
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserServices userServices;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplicationBackendApplication2.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		Role role1 = new Role();
//		role1.setId(WebConstants.ADMIN);
//		role1.setRoleName("ROLE_ADMIN");
//		
//		Role role2 = new Role();
//		role2.setId(WebConstants.NORMAL_USER);
//		role2.setRoleName("ROLE_USER");		
//			
//		List<Role> list = List.of(role1, role2);
//		this.roleRepo.saveAll(list);
		
//		User dto = new User();
//		dto.setEmail("admin@gmail.com");
//		dto.setName("Admin");
//		dto.setPassword("Admin@1234");
//		this.userServices.createAdmin(dto);
		
	}

}
