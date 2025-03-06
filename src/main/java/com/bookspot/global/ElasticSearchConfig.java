package com.bookspot.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Value("${elasticsearch.uri}")
    private String uri;

    @Value("${elasticsearch.api_key}")
    private String apiKey;

    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "ApiKey " + apiKey);

        return ClientConfiguration.builder()
                .connectedTo(uri)
//                .usingSsl()
                .withHeaders(() -> headers)
                .build();
    }
}
