package com.shinom.blogging.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostDto {
	
	private int postId;
	private String title;
	private String content;
	private String creationDate;
	private String creatTime;
	private List<String> images;
	private String lastUpatedDateandTime;
	private int likes;
	private boolean visible;
    private boolean archive;
	private boolean deleted;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments = new HashSet<>();
	private Set<Integer> reactUsers=new HashSet<>(); 
	
}
