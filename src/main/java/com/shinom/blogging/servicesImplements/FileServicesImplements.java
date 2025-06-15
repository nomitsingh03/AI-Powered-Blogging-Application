package com.shinom.blogging.servicesImplements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shinom.blogging.newImageUploader.*;
import com.shinom.blogging.services.FileServices;


@Service
public class FileServicesImplements implements FileServices {
	
	@Autowired
	private ImageUploader imageUploader;

	@Override
	public List<String> uploadImage(String path, MultipartFile[] files) throws IOException {
		
		List<String> fileNames = new ArrayList<>();
		
		/*
		 * String name = file.getOriginalFilename();
		 * 
		 * String randomId = UUID.randomUUID().toString();
		 * 
		 * String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		 * 
		 * String filePath = path +fileName;
		 * 
		 * File newFile = new File(path);
		 * 
		 * if(!newFile.exists()) { System.out.println(newFile.mkdirs()); }
		 * 
		 * Files.copy(file.getInputStream(), Paths.get(filePath));
		 */
		
		for(MultipartFile file: files) {
			String fileName= imageUploader.uploadImage(file);
			fileNames.add(fileName);
		}
		
		
		return fileNames;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path+ File.separator+fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
