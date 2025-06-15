package com.shinom.blogging.imageUpload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public AmazonS3 client() {
		
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setMaxConnections(200);
		clientConfig.setConnectionTimeout(5000);        // Connection timeout in ms
		clientConfig.setSocketTimeout(5000);            // Socket timeout in ms
		clientConfig.withTcpKeepAlive(true);             // Enable TCP keep-alive
		clientConfig.setUseThrottleRetries(true); 
		
		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region).withClientConfiguration(clientConfig).build();
		return amazonS3;
	}
	
	
	
}
