package com.aravinth.pdfsplitter.pdfsplitdatadapi.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleStorageConfig {

    @Value("${GCP_API_HOST}")
    private String gcpStorageURL;

    @Bean
    public Storage storage(){
        StorageOptions storageOptions =  StorageOptions.newBuilder().setHost(gcpStorageURL).build();
        return storageOptions.getService();
    }
}
