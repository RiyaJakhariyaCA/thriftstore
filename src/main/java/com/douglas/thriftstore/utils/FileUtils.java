package com.douglas.thriftstore.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils
{
	 private static  String uploadDirectory = "F:\\Coding\\swe-project-fe\\public\\upload\\"; // /Users/macos/code/testupload for mac
	 
	public static void deleteExistingImages(List<String> filePaths) {
		try {
		for(String filepath:filePaths) {
			File deletefile = new File(filepath);
			deletefile.delete();
		}
		}catch(Exception e) {
			//log the error
		}
	}
	
	public static String saveFile(MultipartFile file) throws Exception {
		File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();  
        }
        
        Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

}
