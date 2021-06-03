package com.teamteach.files.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.teamteach.files.services.S3Service;
import com.teamteach.files.services.ScormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.teamteach.files.models.ObjectResponse;
import com.teamteach.files.models.ScormObject;

@RestController
@RequestMapping("files")
public class S3Controller {

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ScormService scormService;
	
	@PostMapping("upload/{folder}")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String folder) {
		System.out.println("Upload-" + file);
		try {
			File fileObj = convertMultiPartToFile(file);
			String key = folder+'/'+file.getOriginalFilename();
			System.out.println(key);
			String s3Url = s3Service.uploadPhoto(key, fileObj);
			fileObj.delete();
			return ResponseEntity.ok(s3Url);
		} catch (IOException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PostMapping("scorm/{folder}")
	public ResponseEntity<ObjectResponse> uploadScorm(@RequestParam("file") MultipartFile file, @PathVariable String folder) {
		System.out.println("Upload-" + file);
		try {
			File fileObj = convertMultiPartToFile(file);
			ScormObject scormObject = scormService.analyseContent(folder, fileObj);
			fileObj.delete();
			return ResponseEntity.ok(new ObjectResponse(true, "File uploaded successfully", scormObject));
		} catch (IOException e) {
			return ResponseEntity.ok(new ObjectResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping("download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
		System.out.println("Download-" + fileName);
		byte[] content = null;
		try {
			String key = fileName;
			content= s3Service.downloadPhoto(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" +fileName + "\"").body(content);
		
	}
	
	@PostMapping("delete")
	public void deleteFile(@RequestParam("fileName") String fileName) {
		System.out.println("Delete-" + fileName);
		try {
			String key = fileName;
			s3Service.deleteFile(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
}
