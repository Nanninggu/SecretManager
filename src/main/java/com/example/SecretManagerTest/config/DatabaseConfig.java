package com.example.SecretManagerTest.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    private String getSecret() {
        String secretName = "prod/AppDev/Postgresql";
        Regions region = Regions.AP_NORTHEAST_2; // Example region
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        String secret = client.getSecretValue(getSecretValueRequest).getSecretString();
        return secret;
    }

    @Bean
    public DataSource dataSource() {
        try {
            String secretJson = getSecret();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> secretMap = objectMapper.readValue(secretJson, Map.class);
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/PracticeDB"); // Update with your URL
            dataSource.setUsername(secretMap.get("username"));
            dataSource.setPassword(secretMap.get("password"));
            dataSource.setDriverClassName("org.postgresql.Driver");
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}