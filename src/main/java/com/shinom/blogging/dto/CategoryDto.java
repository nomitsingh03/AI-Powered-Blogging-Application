package com.shinom.blogging.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.shinom.blogging.entities.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CategoryDto {

	int category_id;
	
	@NotBlank(message="Title must be required")
	@Size(max=100)
	String categoryTitle;
	
	String categoryDescription;
	
	public CategoryDto(String title){
		this.categoryTitle=title;
	}
}
