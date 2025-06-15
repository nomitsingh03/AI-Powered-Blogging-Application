package com.shinom.blogging.servicesImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shinom.blogging.dto.CategoryDto;
import com.shinom.blogging.dto.ChangePasswordDto;
import com.shinom.blogging.dto.PasswordConfirmation;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.Role;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.exceptions.ResourceNotFoundException;
import com.shinom.blogging.exceptions.UserAccountDeactivate;
import com.shinom.blogging.exceptions.UserNameNotFoundException;
import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.helper.WebConstants;
import com.shinom.blogging.newImageUploader.*;
import com.shinom.blogging.repositories.RoleRepo;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.CategoryService;
import com.shinom.blogging.services.UserServices;


@Service
public class UserServiceImplements implements UserServices {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ImageUploader imageUploader;
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public UserDto createUser(UserDto dto) {
		Optional<User> existUser = this.userRepo.findByEmail(dto.getEmail());
		if(existUser.isPresent()) throw new RuntimeException("User already exists");
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		Set<String> categorySet =  this.categoryService.getAllCategoryName();
		for(String ints : dto.getInterests()) {
			if(!categorySet.contains(ints)) {
				this.categoryService.creatCategory(new CategoryDto(ints));
				categorySet.add(ints);
			}
		}
		if(dto.getImageName()!=null) user.setImageName(dto.getImageName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		Role role = this.roleRepo.findById(WebConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser); 
	}
	
	public UserDto createAdmin(User user) {
		Optional<User> existUser = this.userRepo.findByEmail(user.getEmail());
		if(existUser.isPresent()) throw new RuntimeException("User already exists");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(WebConstants.ADMIN).get();
		user.getRoles().add(role);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser); 
	}



	@Override
	public UserDto getUserById(int id) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		return this.userToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user= userRepo.findByEmail(email).orElseThrow(()-> new UserNameNotFoundException(email));
		user= userRepo.findByEmailAndDeactivateFalse(email).orElseThrow(()-> new UserAccountDeactivate(email));
		return this.userToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto dto, int id) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		user.setAbout(dto.getAbout());
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		List<Role> collect = new ArrayList<>(dto.getRoles()).stream().map(role -> this.modelMapper.map(role, Role.class)).collect(Collectors.toList());
		user.getRoles().addAll(collect);
		User saved = userRepo.save(user);
		return this.userToDto(saved);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		user.setDeactivate(true);
		this.userRepo.save(user);
//		userRepo.delete(user);
	}

	@Override
	public UserDto userToDto(User user) {
		// TODO Auto-generated method stub
		UserDto dto = this.modelMapper.map(user, UserDto.class);
		return dto;
	}


	@Override
	public User dtoToUser(UserDto userdto) {
		User user =this.modelMapper.map(userdto, User.class);
		return user;
	}


	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}


	@Override
	public boolean changePassword(int id, ChangePasswordDto dto) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		if(passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(dto.newPassword1()));
			userRepo.save(user);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean changePassword(PasswordConfirmation data) {
		System.out.println(data.getEmail());
		User user = userRepo.findByEmail(data.getEmail()).orElseThrow(()-> new UserNameNotFoundException(data.getEmail()));
			user.setPassword(passwordEncoder.encode(data.getPassword()));
			userRepo.save(user);
			return true;
	}


	@Override
	public String updateName(int id, UserDto dto) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		user.setName(dto.getName());
		userRepo.save(user);
		return "success";
	}


	@Override
	public ApiResponse updateEmail(int id, UserDto dto) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		if(passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			 user.setEmail(dto.getEmail());
			 userRepo.save(user);
			 return new ApiResponse("Email updated Successfully",true);
		}
		return new ApiResponse("Incorrect Password",false);
	}


	@Override
	public String updateAbout(int id, UserDto dto) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " Id", id));
		user.setAbout(dto.getAbout());
		userRepo.save(user);
		return "success";
	}


	@Override
	public UserDto updateImage(MultipartFile file, int userId) throws RuntimeException{
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", " Id", userId));
		String name = this.imageUploader.uploadImage(file);
		try {
			String prevImageName = user.getImageName();
			if(prevImageName!=null || prevImageName!="") this.imageUploader.deleteImageByName(prevImageName);
		} catch (RuntimeException e) {
			throw new RuntimeException("Image not found");
		}
		user.setImageName(name);
		User updateUser= this.userRepo.save(user);
		return userToDto(updateUser);
	}

	@Override
	public UserDto createNewUser(UserDto dto) {
//		Optional<User> existUser = this.userRepo.findByEmail(dto.getEmail());
//		if(existUser.isPresent()) throw new RuntimeException("User already exists");
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setProvider(dto.getProvider());
		Role role = this.roleRepo.findById(WebConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser); 
		
	}

	@Override
	public User getUser2ByEmail(String email) {
		User user = this.userRepo.findByEmail(email).orElse(null);
		return user;
	}

	@Override
	public Boolean changeActiveStatus(Integer userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", " Id", userId));
		user.setDeactivate(!user.isDeactivate());
		this.userRepo.save(user);
		return true;
	}

}
