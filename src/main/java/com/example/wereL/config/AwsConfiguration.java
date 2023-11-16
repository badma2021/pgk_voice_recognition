package com.example.wereL.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;


@Configuration
@PropertySource(value = "classpath:aws.properties")
public class AwsConfiguration {
    @Autowired
    Environment env;
    public AWSStaticCredentialsProvider awsCredentials() {
        System.out.println(env.getProperty("accessKey"));
        System.out.println(env.getProperty("secretKey"));
        BasicAWSCredentials credentials =
        new BasicAWSCredentials(Objects.requireNonNull(env.getProperty("accessKey")), Objects.requireNonNull(env.getProperty("secretKey")));
        return new AWSStaticCredentialsProvider(credentials);
    }

    @Bean
    public AmazonSimpleEmailService getAmazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsCredentials())
                .withRegion("eu-central-1").build();
    }


}
