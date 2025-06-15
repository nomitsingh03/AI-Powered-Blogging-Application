package com.shinom.blogging.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.helper.CustomResponse;
import com.shinom.blogging.servicesImplements.ImageUploadException;

@RestControllerAdvice
public class ExceptionHandlerHelper {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundEsceptionHandler(ResourceNotFoundException exc){
		String message = exc.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException exc){
		String message = exc.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNameNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundEsceptionHandler(UserNameNotFoundException exc){
		String message = exc.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> badCredentialException(BadCredentialsException exc){
//		String message = exc.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse("The username or password is incorrect", false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handelMethodArgumentNotValidException(MethodArgumentNotValidException exc){
		Map<String, String> map = new HashMap<>();
		BindingResult bindingResult = exc.getBindingResult();
		bindingResult.getAllErrors().forEach((error)->{
			String field= ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			map.put(field, message);
		});
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ImageUploadException.class)
	public ResponseEntity<CustomResponse> handleImageUploadException(ImageUploadException exception){
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				CustomResponse.builder().message(exception.getMessage())
				.success(false).build());
	}
	

}
