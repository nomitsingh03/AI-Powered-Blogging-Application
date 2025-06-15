package com.shinom.blogging.controller;


import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.Stoppable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.stringtemplate.v4.compiler.STParser.ifstat_return;
import org.stringtemplate.v4.compiler.STParser.notConditional_return;

import com.shinom.blogging.dto.ChangePasswordDto;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.newImageUploader.*;
import com.shinom.blogging.services.UserServices;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.val;

@RestController
@RequestMapping("/blogging/user")
@Tag(name = "user")
public class UserController {
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ImageUploader imageUploader;
	
	@Hidden
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto){
		
		UserDto userDto = this.userServices.createUser(dto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "update user by id")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto dto,@PathVariable("id") Integer id){
		
		UserDto userDto = this.userServices.updateUser(dto, id);
		return ResponseEntity.ok(userDto);
	}
	
	@Hidden
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int userId){
		this.userServices.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(this.userServices.getAllUsers());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "get user by id")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id){
		
		return ResponseEntity.ok(this.userServices.getUserById(id));
	}
	
	@PostMapping("/{id}/change-password")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto dto, @PathVariable("id") Integer userId){
		String message = "failed";
		if(this.userServices.changePassword(userId, dto)) message="success";
		return ResponseEntity.ok(message);
		
	}
	
	@PutMapping("/changeName")
	public ResponseEntity<ApiResponse> updateName(@RequestBody UserDto dto){
		System.out.println(dto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(this.userServices.updateName(dto.getUserId(), dto),true),HttpStatus.ACCEPTED);
	}
	@PutMapping("/changeEmail")
	public ResponseEntity<ApiResponse> updateEmail(@RequestBody UserDto dto){
		System.out.println(dto.getUserId());
		return new ResponseEntity<ApiResponse>(this.userServices.updateEmail(dto.getUserId(), dto),HttpStatus.OK);
	}
	@PutMapping("/changeAbout")
	public ResponseEntity<ApiResponse> updateAbout(@RequestBody UserDto dto){
		System.out.println(dto.getUserId()+" "+dto.getAbout());
		return new ResponseEntity<ApiResponse>(new ApiResponse(this.userServices.updateAbout(dto.getUserId(), dto),true),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{id}/changeProfileImage")
	public ResponseEntity<UserDto> updateProfileImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer userId) {
		UserDto dto = this.userServices.updateImage(file,userId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/image")
	public ResponseEntity<String> getImageByName(@RequestParam("imageName") String imageName){
		if(imageName==null) return ResponseEntity.ok(null);
		String urlString = this.imageUploader.getImageUrlByName(imageName);
		return ResponseEntity.ok(urlString);
	}
	
	@PutMapping("/changeActiveStatus/{userId}")
	public ResponseEntity<Boolean> changeActiveStatus(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userServices.changeActiveStatus(userId));
	}
}
