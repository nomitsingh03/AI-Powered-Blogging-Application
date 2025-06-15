package com.shinom.blogging.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.shinom.blogging.dto.PostDto;
import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.helper.CustomResponse;
import com.shinom.blogging.helper.PostResponse;
import com.shinom.blogging.helper.WebConstants;

import com.shinom.blogging.services.FileServices;
import com.shinom.blogging.services.PostService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/blogging/")
@Tag(name = "Post ", description = "This is for Post Controller")
public class PostController {
	
	@Autowired
	public PostService postService;
	
	@Autowired
	public FileServices fileServices;

	@PostMapping("/user/{user_id}/category/{category_id}/post")
	@Operation(summary = "create post using userId and categoryId")
	public ResponseEntity<PostDto> createPost(@RequestParam("postData") String dto , @RequestParam(value = "file", required = false) MultipartFile[] files , @PathVariable Integer user_id,
			@PathVariable Integer category_id) throws IOException {
		System.out.println(dto);
//		String fileName = this.fileServices.uploadImage(path, file);
		PostDto savedPost = this.postService.create(dto, user_id, category_id, files);
	return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
	}
	
	@Hidden
	@PutMapping("/post/{post_id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto dto, @PathVariable Integer post_id) {
		PostDto savedPost = this.postService.update(dto, post_id);
	return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
	}
	
	@PutMapping("/post/{post_id}/like")
	public ResponseEntity<PostDto> updatePostLike(@RequestBody PostDto dto,@RequestParam(name = "userId") Integer userId, @PathVariable Integer post_id) {
		System.out.println("heeko");
		PostDto savedPost = this.postService.updateLikes(post_id, userId);
	return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
	}
	
	@Hidden
	@DeleteMapping("/post/{post_id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer post_id) {
		this.postService.deletePost( post_id);
	return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Succcesfully ", true), HttpStatus.OK);
	}
	
	@GetMapping("/user/{user_id}/post")
	@Operation(summary = "get post by user")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer user_id) {
		List<PostDto> posts = this.postService.getPostsByUser(user_id);
	return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/user/{user_id}/archivePost")
	@Operation(summary = "get post by user")
	public ResponseEntity<List<PostDto>> getArchivedPostsByUser(@PathVariable Integer user_id) {
		List<PostDto> posts = this.postService.getArchivedPostsByUser(user_id);
	return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{category_id}/posts")
	@Operation(summary = "get Post by category")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer category_id) {
		List<PostDto> posts = this.postService.getPostsByCategory(category_id);
	return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue = WebConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = WebConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = WebConstants.SORT_BY, required = false)String sortBy,
			@RequestParam(value = "orderBy", defaultValue = WebConstants.SORT_ORDER, required = false)String orderBy) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, orderBy);
	return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/v2")
	public ResponseEntity<PostResponse> getAllPosts2(
			@RequestParam(value="pageNumber", defaultValue = WebConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = WebConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = WebConstants.SORT_BY, required = false)String sortBy,
			@RequestParam(value = "orderBy", defaultValue = WebConstants.SORT_ORDER, required = false)String orderBy,
			@RequestParam(value = "categoryId", defaultValue = WebConstants.CATEGORY_ID, required = false)Integer categoryId) {
		PostResponse postResponse = this.postService.getAllPosts2(pageNumber, pageSize, sortBy, orderBy, categoryId);
	return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/v3")
	public ResponseEntity<List<PostDto>> getAllPosts3(
			@RequestParam(value = "sortBy", defaultValue = WebConstants.SORT_BY, required = false)String sortBy,
			@RequestParam(value = "orderBy", defaultValue = WebConstants.SORT_ORDER, required = false)String orderBy,
			@RequestParam(value = "archived", defaultValue = WebConstants.ARCHIVED, required = false)String archived,
			@RequestParam(value = "categoryId", defaultValue = WebConstants.CATEGORY_ID, required = false)Integer categoryId) {
		List<PostDto> postResponse = this.postService.getAllPosts3(sortBy, orderBy, categoryId, archived);
	return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/v4")
	public ResponseEntity<List<PostDto>> getAllPosts4(
			@RequestParam(value = "sortBy", defaultValue = WebConstants.SORT_BY, required = false)String sortBy,
			@RequestParam(value = "orderBy", defaultValue = WebConstants.SORT_ORDER, required = false)String orderBy,
			@RequestParam(value = "archived", defaultValue = WebConstants.ARCHIVED, required = false)String archived,
			@RequestParam(value = "categoryId", defaultValue = WebConstants.CATEGORY_ID, required = false)Integer categoryId) {
		List<PostDto> postResponse = this.postService.getAllPosts4(sortBy, orderBy, categoryId, archived);
	return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/post/{post_id}")
	@Operation(summary = "get Post by Id")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer post_id) {
		PostDto dto = this.postService.getPostById(post_id);
	return new ResponseEntity<PostDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/post/search/{keywords}")
	@Operation(summary = "get post by searches")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keywords") String keywords){
		List<PostDto> postDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
//	@PostMapping("/post/image/upload/{postId}")
//	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile[] file,
//			@PathVariable("postId") Integer id) throws IOException{
//		
//				PostDto postDto = this.postService.getPostById(id);
//				List<String> fileName = this.fileServices.uploadImage(path, file);
//				postDto.setImages(fileName);
//				PostDto updatePostDto =  this.postService.update(postDto, id);
//				return new ResponseEntity<PostDto>(updatePostDto, HttpStatus.OK);
//		
//	}
	
//	@GetMapping(value = "/post/image/{imageName}", produces =MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImage(@PathVariable("imageName") String imageName, 
//			HttpServletResponse response) throws IOException {
//		InputStream resourceInputStream = this.fileServices.getResource(path, imageName);
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(resourceInputStream, response.getOutputStream());
//		
//	}
	
	@PutMapping("/post/archive/{post_id}")
	@Operation(summary = "archive posts")
	public ResponseEntity<CustomResponse> archivePost(@PathVariable Integer post_id) {
		boolean update = this.postService.archivePost(post_id);
		String messageString = "Post Archived Successfully !";
		if(!update) messageString = "Post UnArchived Successfully !";
	return new ResponseEntity<CustomResponse>(new CustomResponse(messageString, update), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/archived/posts/{userId}")
	public ResponseEntity<List<PostDto>> archivedPosts(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.archivedPosts(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	
}
