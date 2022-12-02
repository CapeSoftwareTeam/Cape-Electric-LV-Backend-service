package com.capeelectric.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class ReturnPDFService {

	private static final Logger logger = LoggerFactory.getLogger(ReturnPDFService.class);
	@Value("${s3.bucket.name}")
	private String s3BucketName;
	@Value("${access.key.id}")
	private String accessKeyId;
	@Value("${access.key.secret}")
	private String accessKeySecret;

	public ByteArrayOutputStream printFinalPDF(String userName, Integer siteId, String keyName) throws Exception {
		
		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {
			
//			String fileNameInS3 = "finalreport.pdf";
			try {
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, accessKeySecret);
				AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

				// 5 seconds of time for executing between FileUpload And FileDownload in AWS s3
				// bucket
                //Thread.sleep(5000);

				// Downloading the PDF File in AWS S3 Bucket with folderName + fileNameInS3
				S3Object fullObject;
				fullObject = s3Client.getObject(
						new GetObjectRequest(s3BucketName, "LV_Site Name_".concat(keyName) + "/" + keyName+".pdf"));
				
				logger.info("Downloading file done from AWS s3");
				InputStream is = fullObject.getObjectContent();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				int len;
				byte[] buffer = new byte[4096];
				while ((len = is.read(buffer, 0, buffer.length)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				return outputStream;
			} catch (IOException ioException) {
				logger.error("IOException: " + ioException.getMessage());
			} catch (AmazonServiceException serviceException) {
				logger.info("AmazonServiceException Message: " + serviceException.getMessage());
				throw serviceException;
			} catch (AmazonClientException clientException) {
				logger.info("AmazonClientException Message: " + clientException.getMessage());
				throw clientException;
			}

		}
		return null;
	}
}
