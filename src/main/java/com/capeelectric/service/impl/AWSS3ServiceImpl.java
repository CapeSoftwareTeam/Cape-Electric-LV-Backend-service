package com.capeelectric.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.capeelectric.exception.PdfException;

@Service
public class AWSS3ServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(AWSS3ServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;


    public byte[] findByName(String fileName) throws PdfException {
        LOG.debug("Downloading file with name {}", fileName);
        S3Object s3Object =  amazonS3.getObject(s3BucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
			byte[] content =  IOUtils.toByteArray(inputStream);
			return content;
		} catch (Exception e) {
			LOG.debug("AWSS3ServiceImpl findbyName function Downloading PDFfile Failed for filename"
					+ " -->"+fileName+e.getMessage());
			throw new PdfException("AWSS3ServiceImpl findbyName function Downloading PDFfile Failed for filename"
					+ " -->"+fileName+e.getMessage());
		}
       // return null;
    }


}