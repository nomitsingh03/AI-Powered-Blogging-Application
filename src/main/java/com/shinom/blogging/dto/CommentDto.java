package com.shinom.blogging.dto;

import java.util.Date;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class CommentDto {

	private int id;
	@Size(max=500)
	private String comment;
	private Date date;
	private int likes;
	private UserDto user;
	private boolean deactivate;
}
