package com.shinom.blogging.controller;

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

import com.shinom.blogging.dto.CategoryDto;
import com.shinom.blogging.helper.ApiResponse;
import com.shinom.blogging.services.CategoryService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogging/category")
@Tag(name = "Category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto){
		CategoryDto savedCategory = this.categoryService.creatCategory(dto); 
		return new ResponseEntity<CategoryDto>(savedCategory, HttpStatus.CREATED);
	}
	
	@Hidden
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,@PathVariable("id") Integer id){
		CategoryDto updatedCategory = this.categoryService.updateCategory(dto, id); 
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Integer id){
		CategoryDto updatedCategory = this.categoryService.getCategory(id); 
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer id){
		this.categoryService.deleteCategory(id); 
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> allCategory = this.categoryService.getAllCategory();
		System.out.println(allCategory);
		return new ResponseEntity<List<CategoryDto>>(allCategory, HttpStatus.OK);
	}

}
