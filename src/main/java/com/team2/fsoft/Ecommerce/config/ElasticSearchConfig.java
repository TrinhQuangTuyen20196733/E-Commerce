package com.team2.fsoft.Ecommerce.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestClient restClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        // Todo: set up more config

        return builder.build();
    }
}
