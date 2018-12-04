package com.placepass.userservice;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    /**
     * Rest template timeout configurations
     */
    @Value("${resttemplate.connection.timeout}")
    private int connectionTimeOut;

    @Value("${resttemplate.read.timeout}")
    private int readTimeOut;

    @Value("${resttemplate.con.maxperroot}")
    private int maxPerRoot;

    @Value("${resttemplate.con.maxtotal}")
    private int maxTotal;

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoot);
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager).build());
        requestFactory.setConnectTimeout(connectionTimeOut);
        requestFactory.setReadTimeout(readTimeOut);
        return new RestTemplate(requestFactory);
    }

}
