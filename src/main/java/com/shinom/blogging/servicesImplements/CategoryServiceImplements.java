package com.shinom.blogging.servicesImplements;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.CategoryDto;
import com.shinom.blogging.entities.Category;
import com.shinom.blogging.exceptions.ResourceNotFoundException;
import com.shinom.blogging.repositories.CategoryRepo;
import com.shinom.blogging.services.CategoryService;

@Service
public class CategoryServiceImplements implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public CategoryDto creatCategory(CategoryDto categoryDto) {
		Category category = this.dtoToCategory(categoryDto);
		Category saved = categoryRepo.save(category);
		return this.CategoryToDto(saved);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto dto, Integer id) {
		 Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category ", " category_id ", id));
		category.setCategoryTitle(dto.getCategoryTitle());
		category.setCategoryDescription(dto.getCategoryDescription());
		
		Category update= categoryRepo.save(category);
		 return this.CategoryToDto(update);
	}

	@Override
	public void deleteCategory(Integer id) {
		
		Category category = categoryRepo.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category ", " category_id ", id));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer id) {
		Category category = categoryRepo.findById(id).orElseThrow(()->
			new ResourceNotFoundException("Category ", " category_id ", id));
		return this.CategoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories =categoryRepo.findAll();
		List<CategoryDto> list = categories.stream().map((category)->this.CategoryToDto(category)).collect(Collectors.toList());
		return list;
	}
	
	public CategoryDto CategoryToDto(Category category) {
		CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
		return dto;
	}

	public Category dtoToCategory(CategoryDto dto) {
		Category category =this.modelMapper.map(dto, Category.class);
		return category;
	}

	@Override
	public Set<String> getAllCategoryName() {
		Set<String> allCategories = new HashSet<>();
		List<CategoryDto> dtos = getAllCategory();
		for(CategoryDto dto : dtos) {
			allCategories.add(dto.getCategoryTitle());
		}
		return allCategories;
	}

}
