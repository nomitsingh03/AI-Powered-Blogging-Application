package com.shinom.blogging.newImageUploader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploader {
	
	@Value("${project.image}")
	private String path;
	
//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//	    try {
//	        File imageFile = new File(uploadDir + file.getOriginalFilename());
//	        if (imageFile.exists()) {
//	            imageFile.delete(); // Remove the old image
//	        }
//	        file.transferTo(imageFile); // Save the new image
//	        return ResponseEntity.ok("Image updated successfully!");
//	    } catch (IOException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image.");
//	    }
//	}
//	
//	
//	const fetchImage = async (id) => {
//	    const response = await fetch(`http://localhost:8080/images/${id}`);
//	    const blob = await response.blob();
//	    const imageUrl = URL.createObjectURL(blob);
//	    document.getElementById("image").src = imageUrl;
//	};

	public String uploadImage(MultipartFile file)  {
		
		 if (file != null && !file.isEmpty()) {
             // Define the directory where you want to save the image
             String directoryPath = path;
             File directory = new File(directoryPath);
             if (!directory.exists()) {
               directory.mkdirs();
             }

             // Generate a unique file name using UUID
             String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
             String filePath = directoryPath + File.separator + uniqueFileName;
             File localFile = new File(filePath);
             try {
				file.transferTo(localFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

             return uniqueFileName;
           }
		 return null;
	}
	
	public String uploadPostImage(MultipartFile file)  {
		
		 if (file != null && !file.isEmpty()) {
            // Define the directory where you want to save the image
            String directoryPath = path+ File.separator + "blog-images";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
              directory.mkdirs();
            }

            // Generate a unique file name using UUID
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = directoryPath + File.separator + uniqueFileName;
            File localFile = new File(filePath);
            try {
				file.transferTo(localFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            return uniqueFileName;
          }
		 return null;
	}
	
	public String getImageUrlByName(String imageName) {
//		try {
//            Path imagePath = Paths.get(path).resolve(imageName).normalize();
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists()) {
//                return resource.toString();
//            } 
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
		return imageName;
		
	}
	
	public List<String> getAllImagesByPost(List<String> images){
//		List<String> result = new ArrayList<>();
//		for (String image : images) {
//			String urlString = this.getImageUrlByName(image);
//			if (urlString != null)
//				result.add(urlString);
//		}
//		return result;
		return images;
	}
	
	public String deleteImageByName(String fileName) {
		File file = new File(path + fileName);
		if (file.exists()) {
			file.delete();
			return "File deleted successfully";
		} else {
			return "File not found";
		}
	}

}
