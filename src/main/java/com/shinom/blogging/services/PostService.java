package com.shinom.blogging.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shinom.blogging.dto.PostDto;
import com.shinom.blogging.entities.Category;
import com.shinom.blogging.helper.PostResponse;

public interface PostService {

	public PostDto create(PostDto dto, Integer user_id, Integer category_id);
	
	public PostDto update(PostDto dto, Integer post_id);
	
	public PostDto update(Integer post_id);
	public PostDto updateLikes(Integer post_id, Integer userId);

	public PostDto create(String dto, Integer user_id, Integer category_id, MultipartFile[] files) throws IOException;
	
	public PostDto update(String dto, Integer post_id, MultipartFile file);
	
	public void deletePost(Integer id);
	
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize, String sortBy, String orderBy);
	
	public PostResponse getAllPosts2(Integer pageNumber,Integer pageSize, String sortBy, String orderBy, Integer categoryId);
	
	public PostDto getPostById(Integer id);
	
	public List<PostDto> getPostsByCategory(Integer id);
	
	public List<PostDto> getPostsByUser(Integer id);
	
	public List<PostDto> searchPosts(String keyword);
	
	public boolean archivePost(Integer id);
	List<PostDto> archivedPosts(Integer id);
	List<PostDto> getArchivedPostsByUser(Integer id);

	public List<PostDto> getAllPosts3(String sortBy, String orderBy, Integer categoryId, String archived);

	List<PostDto> getAllPosts4(String sortBy, String orderBy, Integer categoryId, String archived);
	
}
