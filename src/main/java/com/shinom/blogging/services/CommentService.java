package com.shinom.blogging.services;

import java.util.List;

import com.shinom.blogging.dto.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(Integer post_id,Integer user_id, CommentDto dto);
	
	public void deleteComment(Integer id);
	
	public List<CommentDto> getAllComments();
	
	public List<CommentDto> getCommentsByPostAndUser(Integer user_id, Integer post_id);
	
	public List<CommentDto> getAllCommentsByPost(Integer post_id);
	
	public List<CommentDto> getAllCommentsByUser(Integer user_id);
	
	public CommentDto updateComment(CommentDto dto, Integer comment_id);

	public CommentDto updateCommentLike(Integer comment_id);
}
