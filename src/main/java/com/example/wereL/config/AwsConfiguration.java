package com.example.wereL.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsConfiguration {

    public AWSStaticCredentialsProvider awsCredentials() {
        BasicAWSCredentials credentials =
                new BasicAWSCredentials("AKIAWPIHGSNUGTFZIYKV", "XUkt1nbVHBU9GB8VaR58DH+HnYuP0hsmPGLn3JN0");
        return new AWSStaticCredentialsProvider(credentials);
    }

    @Bean
    public AmazonSimpleEmailService getAmazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsCredentials())
                .withRegion("eu-central-1").build();
    }
}
