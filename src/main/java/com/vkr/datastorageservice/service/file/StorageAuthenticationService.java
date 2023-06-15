package com.vkr.datastorageservice.service.file;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageAuthenticationService {
    @Value("${storage.serviceUrl}")
    @Getter
    private String host;

    @Value("${storage.region}")
    private String region;

    @Value("${storage.username}")
    private String userName;

    @Value("${storage.password}")
    private String password;

    @Value("${storage.source_bucket}")
    @Getter
    private String sourceBucket;


    public AmazonS3 getMinIOClient() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(host.toLowerCase().startsWith("https") ? Protocol.HTTPS : Protocol.HTTP);
        AWSCredentials credentials = new BasicAWSCredentials(userName, password);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, region))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
