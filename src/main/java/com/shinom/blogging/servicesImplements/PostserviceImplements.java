package com.shinom.blogging.servicesImplements;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinom.blogging.ai.controller.ToxicityDetectionController;
import com.shinom.blogging.ai.service.PerspectiveApiService;
import com.shinom.blogging.dto.PostDto;
import com.shinom.blogging.entities.Category;
import com.shinom.blogging.entities.Post;
import com.shinom.blogging.entities.User;
import com.shinom.blogging.exceptions.ResourceNotFoundException;
import com.shinom.blogging.helper.PostResponse;
import com.shinom.blogging.newImageUploader.ImageUploader;
import com.shinom.blogging.repositories.CategoryRepo;
import com.shinom.blogging.repositories.PostRepo;
import com.shinom.blogging.repositories.UserRepo;
import com.shinom.blogging.services.EmailServices;
import com.shinom.blogging.services.FileServices;
import com.shinom.blogging.services.PostService;


@Service
public class PostserviceImplements implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ImageUploader imageUploader;
	
	@Autowired
	private PerspectiveApiService	perspectiveApiService;
	
	
	private static ZoneId zone = ZoneId.of("Asia/Kolkata");
	
	@Autowired
	private EmailServices emailService;

	@Override
	public PostDto create(PostDto dto, Integer user_id, Integer category_id) {
		Post post = this.dtoToPost(dto);
		User user = this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User", " Id", user_id));
		Category category = this.categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category ", "Id " ,category_id));
		post.setCategory(category);
		post.setUser(user);
		LocalDate date = LocalDate.now(zone);
		LocalTime time = LocalTime.now(zone);
		LocalDateTime localDateTime = LocalDateTime.now(zone);
	
		post.setCreationDate(date);
		post.setCreationTime(time);
		post.setLastUpdatedDate(localDateTime);
		
		post.setVisible(true);
		
		Post savedPost = postRepo.save(post);
		return this.postToDto(savedPost);
	}

	@Override
	public PostDto update(PostDto dto, Integer post_id) {
		Post post = postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post "," Id ", post_id));
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		LocalDateTime localDateTime = LocalDateTime.now(zone);
		post.setLastUpdatedDate(localDateTime);
		Post updatedPost = this.postRepo.save(post);
		this.emailService.userPostUpdateEmail(updatedPost);
		return this.postToDto(updatedPost);
	}
	
	@Override
	public PostDto update(Integer post_id) {
		Post post = postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post "," Id ", post_id));
		post.setLikes(post.getLikes()+1);
		Post updatedPost = this.postRepo.save(post);
		return this.postToDto(updatedPost);
	}
	
	@Override
	public PostDto updateLikes(Integer post_id, Integer userId) {
		Post post = postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post "," Id ", post_id));
		Set<Integer> reactedUsers = post.getReactUsers();
//		if(reactedUsers.contains(userId)) reactedUsers.remove(userId);
//		else 
			reactedUsers.add(userId);
		post.setLikes(reactedUsers.size());
		Post updatedPost = this.postRepo.save(post);
		return this.postToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post ", "Id ", id));
		post.setDeleted(true);
//		postRepo.delete(post);
		this.postRepo.save(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		
		Sort sort = (orderBy.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
				
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
//		Page<Post> pages = this.postRepo.findAll(pageable);
		
		Page<Post> pages = this.postRepo.findByDeletedFalseAndArchiveFalse(pageable);
		
		List<Post> posts= pages.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
// 		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pages.getNumber());
		postResponse.setPageSize(pages.getSize());
		postResponse.setTotalPosts(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		postResponse.setLastPage(pages.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostResponse getAllPosts2(Integer pageNumber, Integer pageSize, String sortBy, String orderBy, Integer categoryId) {
		
		Sort sort = (orderBy.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
				
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
//		Page<Post> pages = this.postRepo.findAll(pageable);
		
		Optional<Category> category = this.categoryRepo.findById(categoryId);
		Page<Post> pages = this.postRepo.findByDeletedFalseAndArchiveFalse(pageable);
		if(category.isPresent())
			pages = this.postRepo.findByCategoryAndDeletedAndArchive(category.get(), false, false, pageable);
		
		List<Post> posts= pages.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
// 		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pages.getNumber());
		postResponse.setPageSize(pages.getSize());
		postResponse.setTotalPosts(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		postResponse.setLastPage(pages.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post = this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","Id ", id));
		PostDto dto = this.postToDto(post);
		List<String> allImagesByPost = imageUploader.getAllImagesByPost(dto.getImages());
		dto.setImages(allImagesByPost);
		return dto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category ", "Id ", id));
//		List<Post> posts = this.postRepo.findByCategory(category);
		List<Post> posts = this.postRepo.findByCategoryAndDeletedAndArchive(category, false, false);
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(()-> new RuntimeException("Some error occur"));
		List<Post> posts = this.postRepo.findByUserAndDeletedFalse(user);
//		List<Post> posts = this.postRepo.findByUserAndDeletedFalseAndArchiveFalse(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		return postDtos;
	}

	
	public List<PostDto> getArchivedPostsByUser(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(()-> new RuntimeException("Some error occur"));
//		List<Post> posts = this.postRepo.findByUser(user);
		List<Post> posts = this.postRepo.findByUserAndDeletedFalseAndArchiveTrue(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts =  this.postRepo.findByTitleContainingAllIgnoreCase(keyword);
		List<PostDto> postDtos = posts.stream().map((p)-> postToDto(p)).collect(Collectors.toList());
		for(PostDto dto : postDtos) {	
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		
		
		return postDtos;
	}
	
	private Post dtoToPost(PostDto dto) {
		Post post = this.modelMapper.map(dto, Post.class);
		return post;
	}
	
	private PostDto postToDto(Post post) {
		PostDto dto = this.modelMapper.map(post, PostDto.class);
		
		String formattedDate ="";
		LocalDate date = post.getCreationDate();
		if(date!=null)
		formattedDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		dto.setCreationDate(formattedDate);
		
		LocalTime time = post.getCreationTime();
		if(time!=null)
		formattedDate = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
		dto.setCreatTime(formattedDate);
		
		LocalDateTime dateTime = post.getLastUpdatedDate();
		if(dateTime!=null)
		formattedDate = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		dto.setLastUpatedDateandTime(formattedDate);
		
		return dto;
	}

	@Override
	public PostDto create(String data, Integer user_id, Integer category_id, MultipartFile[] files) throws IOException {
		PostDto dto = this.objectMapper.readValue(data,PostDto.class);
		System.out.println("hhh");
		
		boolean toxicity = this.detectToxicity(dto);
		if(toxicity) {
			this.emailService.postCreationFailedEmail(user_id);
			throw new IllegalArgumentException("Your post is not created. System detects some toxicity in your post.");
		}
		
		System.out.println("ggg");
		if(files!=null){
			List<String> imageList = Arrays.stream(files).map(file -> this.imageUploader.uploadPostImage(file)).collect(Collectors.toList());
			dto.setImages(imageList);
	    } else dto.setImages(null);   
//		dto.setImages(null);
		PostDto saveDto = this.create(dto, user_id, category_id);
		this.emailService.userPostCreationEmail(user_id, saveDto);
		return saveDto;
	}

	@Override
	public PostDto update(String dto, Integer post_id, MultipartFile file) {
		
		return null;
	}
	
	@Override
	public boolean archivePost(Integer id) {
		Post post = this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","Id ", id));
		post.setArchive(!post.isArchive());
		this.postRepo.save(post);
		return post.isArchive();
	}
	
	public List<PostDto> archivedPosts(Integer id){
		User user = this.userRepo.findById(id).orElseThrow(()-> new RuntimeException("Some error occur"));
		List<Post> posts = this.postRepo.findByUserAndDeletedFalseAndArchiveTrue(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		return postDtos;
	}
	
	public boolean detectToxicity(PostDto dto)  {
		System.out.println("eee");
		Double toxicityScore1 = perspectiveApiService.getToxicityScore(dto.getTitle());
		Double toxicityScore2 = perspectiveApiService.getToxicityScore(dto.getContent());
		System.out.println(toxicityScore1+" "+toxicityScore2);
//		if(toxicityScore1 > 0.2 || toxicityScore2 > 0.2) {
//			this.emailService.postCreationFailedEmail();
//			throw new IllegalArgumentException("Your post is not created. System detects some toxicity in your post.");
//
//		}
		return (toxicityScore1 > 0.2) || (toxicityScore2 > 0.2);
	}

	@Override
	public List<PostDto> getAllPosts3(String sortBy, String orderBy, Integer categoryId, String archived) {
		// TODO Auto-generated method stub
		Sort sort = (orderBy.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Optional<Category> category = this.categoryRepo.findById(categoryId);
		List<Post> posts = new ArrayList<>();
			
		if(category.isPresent()) {
			if(archived.equalsIgnoreCase("true"))
                posts = this.postRepo.findByCategoryAndArchive(category.get(), true, sort);
            else if(archived.equalsIgnoreCase("false"))
                posts = this.postRepo.findByCategoryAndArchive(category.get(), false, sort);
            else posts = this.postRepo.findByCategory(category.get());
		} else {
			if (archived.equalsIgnoreCase("true"))
				posts = this.postRepo.findByArchive(true, sort);
			else if (archived.equalsIgnoreCase("false"))
				posts = this.postRepo.findByArchive(false, sort);
			else
				posts = this.postRepo.findAll(sort);
		}
		
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		
		return postDtos;
	}
	
	@Override
	public List<PostDto> getAllPosts4(String sortBy, String orderBy, Integer categoryId, String archived) {
		// TODO Auto-generated method stub
		Sort sort = (orderBy.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		List<Post> posts = this.postRepo.findAll(sort);
	
		List<PostDto> postDtos = posts.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		
		for(PostDto dto : postDtos) {
			List<String> urlsList = imageUploader.getAllImagesByPost(dto.getImages());
			dto.setImages(urlsList);
		}
		
		return postDtos;
	}

}
