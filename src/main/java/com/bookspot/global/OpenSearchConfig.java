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
import org.opensearch.client.transport.OpenSearchTransport;
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
        return new OpenSearchClient(
                openSearchTransport()
        );
    }

    @Bean(destroyMethod = "close")
    public OpenSearchTransport openSearchTransport() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, password)
        );

        final RestClient restClient = RestClient.builder(HttpHost.create(serverUrl))
                .setRequestConfigCallback(
                        req -> req.setConnectTimeout(2_000)        // 서버 TCP 연결까지 최대 2초
                                .setSocketTimeout(5_000)         // 데이터 응답 대기 최대 5초
                                .setConnectionRequestTimeout(2_000) // 커넥션 풀에서 가져올 때 최대 2초
                )
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setDefaultIOReactorConfig(
                                        IOReactorConfig.custom()
                                                .setIoThreadCount(5)
                                                .build()
                                )
                )
                .build();

        return new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper()));
    }

}
