package com.shinom.blogging.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinom.blogging.dto.CommentDto;
import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.services.CommentService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogging")
@Tag(name = "Comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{user_id}/post/{post_id}/comment")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto dto,@PathVariable Integer user_id,@PathVariable Integer post_id) {
		CommentDto saved = this.commentService.createComment(post_id, user_id, dto);
//		System.out.println(saved);
		return new ResponseEntity<CommentDto>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping("/comment/{comment_id}")
	public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto dto, @PathVariable Integer comment_id) {
		CommentDto updatedCommentDto = this.commentService.updateComment(dto, comment_id);
		return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
	}
	
	@PutMapping("/comment/like/{comment_id}")
	public ResponseEntity<CommentDto> updateCommentLike(@PathVariable Integer comment_id) {
		CommentDto updatedCommentDto = this.commentService.updateCommentLike(comment_id);
		return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
	}
	
	@GetMapping("/user/{user_id}/post/{post_id}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPostAndUser(@PathVariable Integer user_id,@PathVariable Integer post_id) {
		List<CommentDto> list = this.commentService.getCommentsByPostAndUser(post_id, user_id);
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/post/{post_id}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Integer post_id) {
		List<CommentDto> list = this.commentService.getAllCommentsByPost(post_id);
		Collections.sort(list, (a,b)->b.getDate().compareTo(a.getDate()));
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
	}
	
	@Hidden
	@GetMapping("/user/{user_id}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Integer user_id) {
		List<CommentDto> list = this.commentService.getAllCommentsByUser(user_id);
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> getAllComments() {
		List<CommentDto> list = this.commentService.getAllComments();
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
	}
	
	@Hidden
	@DeleteMapping("/comment/{comment_id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer comment_id) {
		this.commentService.deleteComment(comment_id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted ",true), HttpStatus.OK);
	}
	
	
	
	

}
