package com.shinom.blogging.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileServices {

	List<String> uploadImage(String path, MultipartFile[] file) throws IOException;
	
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
