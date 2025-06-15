package com.shinom.blogging.imageUpload.services.Impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.shinom.blogging.imageUpload.services.ImageUploader;
import com.shinom.blogging.servicesImplements.ImageUploadException;

@Service
public class S3ImageUploader implements ImageUploader {
	
	@Autowired
	private AmazonS3 client;
	
	@Value("${app.s3.bucket}")
	private String BucketName;

	@Override
	public String uploadImage(String folder, MultipartFile file) {
		if(file==null) {
			throw new ImageUploadException("Image is not empty");
		}
		String actualFileNameString = file.getOriginalFilename();
		System.out.println(actualFileNameString);
		String filename =folder+"/"+ UUID.randomUUID().toString()+actualFileNameString.substring(actualFileNameString.lastIndexOf(".")-1);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		try {
			PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(BucketName, filename, file.getInputStream(),metadata));
//			System.out.println(filename);
//			return this.preSignedUrl(filename);
			System.out.println(filename);
			return filename;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ImageUploadException("Error in uploading Image "+e.getMessage());
		}
	}

	@Override
	public List<String> allFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageUrlByName(String fileName) {
		
//		uncomment 3 for production
		
		if(fileName==null) return null;
		 S3Object object = client.getObject(BucketName, fileName);
		String key = object.getKey();
		String url = preSignedUrl(key);
		return url;
		
//		return null;
	}

	@Override
	public String preSignedUrl(String filename) {
		Date expiration = new Date();
		
		long time = expiration.getTime();
		int hour = 2;
		time = time+ hour*60*60*1000;
		expiration.setTime(time);
		
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BucketName, filename)
					.withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}

	@Override
	public List<String> getAllImagesByPost(List<String> images) {
		
//		uncomment 4 for production
		
		List<String> imagesUrl = new ArrayList<>();
		if(images==null || images.size()==0) return imagesUrl;
		for(String image: images) {
			imagesUrl.add(this.getImageUrlByName(image));
		}
		return imagesUrl;
	}
	
	public void deleteImageByName(String imageName) {
        try {
            // Build DeleteObjectRequest
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BucketName, imageName);

            // Delete the object from S3
            client.deleteObject(deleteObjectRequest);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
