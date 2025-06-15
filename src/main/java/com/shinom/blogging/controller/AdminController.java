package com.shinom.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.helper.LoginDetailsResponse;
import com.shinom.blogging.services.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/blogging/admin")
@RequiredArgsConstructor
@Tag(name = "Admin ", description = "API for ADMIN")
public class AdminController {
	
	@Autowired
	private final AdminService adminService;
	
	
	@GetMapping("/all-users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = this.adminService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	
	@GetMapping("/loginDetails")
	public ResponseEntity<List<LoginDetailsResponse>> getAllLoginDetails(){
		List<LoginDetailsResponse> list = this.adminService.getAllLoginDetails();
		System.out.println("hello");
		return ResponseEntity.ok(list);
	}

}
