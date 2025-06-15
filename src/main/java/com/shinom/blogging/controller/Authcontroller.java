package com.shinom.blogging.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinom.blogging.dto.ForgotPassword;
import com.shinom.blogging.dto.LoginDto;
import com.shinom.blogging.dto.PasswordConfirmation;
import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.helper.AuthResponse;
import com.shinom.blogging.helper.AuthStatus;
import com.shinom.blogging.helper.ForgotPasswordResponse;
import com.shinom.blogging.helper.RegisterResponse;
import com.shinom.blogging.newImageUploader.ImageUploader;
//import com.shinom.blogging.imageUpload.services.ImageUploader;
import com.shinom.blogging.services.AuthService;
import com.shinom.blogging.services.EmailServices;
import com.shinom.blogging.services.LoginDetailsService;
import com.shinom.blogging.services.UserServices;
import com.shinom.blogging.utils.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/blogging/auth")
@Tag(name = "Auth", description = "This is for Authentication")
public class Authcontroller {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private LoginDetailsService loginDetailsService;
	
	@Autowired
	private EmailServices emailServices;
	
//	@Autowired
//	private ImageUploader imageUploader;
	
	@Autowired
	private ImageUploader imageUploader;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDetails) throws Exception{
		
		String token = authService.login(loginDetails);
		UserDto dto=null;
		try {
			dto = userServices.getUserByEmail(loginDetails.username());
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<AuthResponse>(new AuthResponse(null, e.getMessage(), AuthStatus.USER_DEACTIVATED),HttpStatus.BAD_REQUEST);
		}
		AuthResponse authResponse = new AuthResponse(dto,token, AuthStatus.LOGIN_SUCCESS);
		this.loginDetailsService.saveLoginDetails(dto.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(authResponse);
	}


	@PostMapping("/sign-up")
	@Operation(summary ="Create new User !!" )
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "not authorized"),
			@ApiResponse(responseCode = "201", description = "new user created")
	}
			
	)
	public ResponseEntity<RegisterResponse> signUp(@Valid @RequestBody UserDto dto) {	
		System.out.println(dto);
		var registerDetails = this.userServices.createUser(dto);
		this.loginDetailsService.saveRegistrationDetails(registerDetails.getUserId());
		this.emailServices.userRegistrationEmail(dto);
		return new ResponseEntity<RegisterResponse>(new RegisterResponse(registerDetails, AuthStatus.USER_CREATED_SUCCESSFULLY),HttpStatus.CREATED);
	}
	
	@PostMapping("/v2/sign-up")
	@Operation(summary ="Create new User !!" )
	@ApiResponses( value = {
			@ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "not authorized"),
			@ApiResponse(responseCode = "201", description = "new user created")
	}
			
	)
	public ResponseEntity<RegisterResponse> registerWithImage(@RequestPart("user") @Valid String userDtoJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {	
		
		 try {
	            ObjectMapper mapper = new ObjectMapper();
	            UserDto dto = mapper.readValue(userDtoJson, UserDto.class);
	            
//	            // for production uncomment 1
//	            
//	            if (file != null && !file.isEmpty()) {
//	            	String image= this.imageUploader.uploadImage("profile-images",file);
//	            	System.out.println(image);
//	            	dto.setImageName(image);
//	            }
	            String imageName = this.imageUploader.uploadImage(file);
	            if(imageName!=null) dto.setImageName(imageName);
	           
	            
	            var registerDetails = this.userServices.createUser(dto);
	          
	            this.loginDetailsService.saveRegistrationDetails(registerDetails.getUserId());
	            this.emailServices.userRegistrationEmail(dto);
	            return new ResponseEntity<RegisterResponse>(new RegisterResponse(registerDetails, AuthStatus.USER_CREATED_SUCCESSFULLY),HttpStatus.CREATED);

	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(null);
	        }
		
	}

	@GetMapping("/login/{userId}")
	public ResponseEntity<AuthResponse> login2(@PathVariable Integer userId) throws Exception{
		System.out.println("hello");
		UserDto user = this.userServices.getUserById(userId);
//		System.out.println(email);
		String token = JwtUtils.generateToken(user.getEmail());
		AuthResponse authResponse = new AuthResponse(user,token, AuthStatus.LOGIN_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(authResponse);
	}
	
	@PostMapping("/send-otp")
	public ResponseEntity<RegisterResponse> sendOtp(@RequestBody UserDto dto){
		String otpString = this.authService.sendOtp(dto);
		if(otpString.equals("user exist")) return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponse(dto, AuthStatus.ACCOUNT_ALREADY_EXIST) );
		dto.setOtp(otpString);
		System.out.println(otpString);
		return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponse(dto, AuthStatus.OTP_SENT_SUCCESSFULLY));
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPassword data ){
		String otpString = authService.sendOtp(data);
		if(otpString==null) return ResponseEntity.ok(new ForgotPasswordResponse(data, AuthStatus.FAILED_TO_SEND_OTP));
		if(otpString.equals("user not exist")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ForgotPasswordResponse(data, AuthStatus.EMAIL_NOT_REGISTERED));
		data.setSendOtp(otpString);
		return new ResponseEntity<ForgotPasswordResponse>(new ForgotPasswordResponse(data, AuthStatus.OTP_SENT_SUCCESSFULLY), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<com.shinom.blogging.helper.ApiResponse> validatePassword(@Valid @RequestBody PasswordConfirmation data ){
		boolean update= this.userServices.changePassword(data);
		String successString= "Some internal failure occur, Try Again!";
		com.shinom.blogging.helper.ApiResponse response = new com.shinom.blogging.helper.ApiResponse(successString, false);
		if(update) {
			successString="Password Changed Successfully!";
			response.setMessage(successString);
			response.setSuccess(true);
		}
		return new ResponseEntity<com.shinom.blogging.helper.ApiResponse>(response, HttpStatus.ACCEPTED);
	}
	
}
