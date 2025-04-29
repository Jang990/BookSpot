package com.bookspot.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.opensearch.client.RestClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {
    @Value("${opensearch.serverUrl}")
    private String serverUrl;

    @Value("${opensearch.username}")
    private String username;

    @Value("${opensearch.password}")
    private String password;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public OpenSearchClient openSearchClient() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, password)
        );

        return new OpenSearchClient(
                new RestClientTransport(
                        RestClient.builder(HttpHost.create(serverUrl))
                                .setHttpClientConfigCallback(
                                        httpClientBuilder -> httpClientBuilder
                                                .setDefaultCredentialsProvider(credentialsProvider)
                                                .setDefaultIOReactorConfig(
                                                        IOReactorConfig.custom()
                                                                .setIoThreadCount(
                                                                        5
                                                                        /*TaskExecutorConfig.MULTI_POOL_SIZE*/
                                                                )
                                                                .build()
                                                )
                                )
                                .build(),
                        new JacksonJsonpMapper(objectMapper())
                )
        );
    }

}
