package com.shinom.blogging.servicesImplements;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.CommentDto;
import com.shinom.blogging.entities.Comment;
import com.shinom.blogging.entities.Post;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.exceptions.ResourceNotFoundException;
import com.shinom.blogging.repositories.CommentRepo;
import com.shinom.blogging.repositories.PostRepo;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.CommentService;

@Service
public class CommentServiceImplements implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComment(Integer post_id, Integer user_id, CommentDto dto) {
		Comment comment = this.DtoToComment(dto);
		User user = this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User ", "Id ", user_id));
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post ", "Id ", post_id));
		comment.setUser(user);
		comment.setPost(post);
		comment.setDate(new Date());
		Comment savedComment= this.commentRepo.save(comment);
//		System.out.println(savedComment);
		
		return this.CommentToDto(savedComment);
	}


	@Override
	public void deleteComment(Integer id) {
		Comment comment = commentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment ", "Id ", id));
		commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getAllComments() {
		List<Comment> commentList = commentRepo.findAll();
		List<CommentDto> dtos = commentList.stream().map((comment)->this.CommentToDto(comment)).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public List<CommentDto> getCommentsByPostAndUser(Integer post_id, Integer user_id) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User ", "Id ", user_id));
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post ", "Id ", post_id));
		List<Comment> comments = this.commentRepo.findByUserAndPost(user, post);
		List<CommentDto> dtos = comments.stream().map((comment)->this.CommentToDto(comment)).collect(Collectors.toList());
		return dtos;
	}


	@Override
	public CommentDto updateComment(CommentDto dto, Integer comment_id) {
		Comment comment = this.commentRepo.findById(comment_id).orElseThrow(()->new ResourceNotFoundException("Comment ", "Id ", comment_id));
		comment.setComment(dto.getComment());
		comment.setDate(new Date());
//		comment.setLikes(dto.getLikes());
		Comment savedCom = this.commentRepo.save(comment);
		
		return this.CommentToDto(comment);
	}
	
	public CommentDto updateCommentLike(Integer comment_id) {
		Comment comment = this.commentRepo.findById(comment_id).orElseThrow(()->new ResourceNotFoundException("Comment ", "Id ", comment_id));
//		comment.setComment(dto.getComment());
//		comment.setDate(dto.getDate());
		comment.setLikes(comment.getLikes()+1);
		Comment savedCom = this.commentRepo.save(comment);	
		return this.CommentToDto(comment);
	}

	@Override
	public List<CommentDto> getAllCommentsByPost(Integer post_id) {
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post ", "Id ", post_id));
		List<Comment> comments =  this.commentRepo.findByPost(post);
		List<CommentDto> commentDtos =  comments.stream().map((comment)-> this.CommentToDto(comment)).collect(Collectors.toList());
		Collections.sort(comments, (a,b)->b.getDate().compareTo(a.getDate()));
		
		return commentDtos;
	}
	
	@Override
	public List<CommentDto> getAllCommentsByUser(Integer user_id) {
		User user = this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User ", "Id ", user_id));
		List<Comment> comments =  this.commentRepo.findByUser(user);
		List<CommentDto> commentDtos =  comments.stream().map((comment)-> this.CommentToDto(comment)).collect(Collectors.toList());
		return commentDtos;
	}

	public CommentDto CommentToDto(Comment comment) {
		CommentDto dto = this.modelMapper.map(comment, CommentDto.class);
		return dto;
	}
	
	public Comment DtoToComment(CommentDto dto) {
		Comment comment = this.modelMapper.map(dto, Comment.class);
		return comment;
	}
}
