package com.teamteach.files.services;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.util.IOUtils;

@Component
public class S3Service {

	private AmazonS3 s3Client;

	@Value("${bucketName}")
	private String bucketName;

	@Value("${awsAccessKey}")
	private String accessKey;

	@Value("${awsSecretKey}")
	private String secretKey;

	@Value("${region}")
	private String region;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(this.region.equals("ap-south-1") ? Regions.AP_SOUTH_1 : Regions.EU_WEST_2).build();
	}

	public String uploadPhoto(String key, File file) {
		PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, file);
		putRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		this.s3Client.putObject(putRequest);
		return this.s3Client.getUrl(bucketName, key).toExternalForm();
	}

	public byte[] downloadPhoto(String key) {

		S3Object s3object = this.s3Client.getObject(bucketName, key);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		byte[] byteArray = null;
		try {
			byteArray = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArray;
	}

	public void deleteFile(String key) {
		this.s3Client.deleteObject(bucketName, key);
		
	}
}
