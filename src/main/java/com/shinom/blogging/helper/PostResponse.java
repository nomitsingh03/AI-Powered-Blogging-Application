package com.shinom.blogging.helper;

import java.util.List;

import com.shinom.blogging.dto.PostDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalPosts;
	private int totalPages;
	private boolean lastPage;
	

}
