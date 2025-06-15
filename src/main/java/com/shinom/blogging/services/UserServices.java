package com.shinom.blogging.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shinom.blogging.dto.ChangePasswordDto;
import com.shinom.blogging.dto.PasswordConfirmation;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.helper.ApiResponse;

public interface UserServices {

	public UserDto createUser(UserDto dto);
	public UserDto createNewUser(UserDto dto);
	public User dtoToUser(UserDto userdto);
	public UserDto userToDto(User user);
	public UserDto getUserById(int id);
	public UserDto getUserByEmail(String email);
	public UserDto updateUser(UserDto user, int id);
	public void deleteUser(int id);
	public List<UserDto> getAllUsers();
	public boolean changePassword(int id, ChangePasswordDto dto);
	public boolean changePassword(PasswordConfirmation data);
	public String updateName(int id, UserDto dto);
	public ApiResponse updateEmail(int id, UserDto dto);
	public String updateAbout(int id, UserDto dto);
	public UserDto updateImage(MultipartFile file, int userId);
	public UserDto createAdmin(User user); 
	public User getUser2ByEmail(String email);
	public Boolean changeActiveStatus(Integer userId);
}
