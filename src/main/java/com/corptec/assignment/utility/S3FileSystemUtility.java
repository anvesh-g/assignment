package com.corptec.assignment.utility;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;

@Profile({"prod"})
@Service
public class S3FileSystemUtility implements FileSystemUtility {

    @Value("${resource.folder}")
    private String resourcePath;

    @Value("${aws-s3.access-key}")
    private String accessKey;

    @Value("${aws-s3.secret-key}")
    private String secretKey;

    @Value("${aws-s3.secret-key}")
    private String bucketName;

    @Override
    public File getFilesLocation() {
        try {
            downloadFilesFromS3();
        } catch (Exception e) {
            System.out.println("Hello");
            e.printStackTrace();
        }
        return new File(resourcePath);
    }


    public void downloadFilesFromS3() {
        final String BUCKET_NAME = "corptec-files";
//
//        final String ACCESS_KEY = "AKIA2KWVZLJLYGVDF2XE";
//        final String SECRET_KEY = "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";

        // 1. Setup S3 client
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        //2. Get object from s3 - write to disk
       ObjectListing objectListing = s3Client
                .listObjects(new ListObjectsRequest().withBucketName(BUCKET_NAME));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            GetObjectRequest request = new GetObjectRequest(BUCKET_NAME, objectSummary.getKey());
            File newFile = new File(resourcePath+"/"+objectSummary.getKey());
            s3Client.getObject(request, newFile);
        }
    }

}
