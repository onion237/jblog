package com.douzone.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:jblog.properties")
public class FileUploadService {
	@Value("${fileupload.fileSaveBasePath}")
	private String SAVE_PATH;
	@Value("${fileupload.urlBasePath}")
	private String URL_BASE;
	
	public String restore(byte[] fileData, String originalFileName, String path) {
		String url = null;
		int extensionIdx = originalFileName.lastIndexOf('.');
		
		if(extensionIdx == -1) {
			return url;
		}
		
		String extension = originalFileName.substring(extensionIdx);
		String fileName = UUID.randomUUID() + extension;
		url = URL_BASE  + path + "/" + fileName;
		String filePath = SAVE_PATH + path + "/" + fileName; 
		
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		try(OutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(fileData);
		} catch (IOException e1) {
			throw new RuntimeException("error occurs in file upload");
		}
		
		return url;
	}

}
