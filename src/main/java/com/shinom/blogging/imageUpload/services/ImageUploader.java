package com.shinom.blogging.imageUpload.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

	String uploadImage(String folder, MultipartFile file);
	
	List<String> allFiles();
	
	String getImageUrlByName(String fileName);

	String preSignedUrl(String filename);
	
	List<String> getAllImagesByPost(List<String> images);
	
	void deleteImageByName(String name);
	
}
