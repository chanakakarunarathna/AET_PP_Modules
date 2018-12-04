package com.stripe.payment.connector.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.stripe.net.RequestOptions;

@Configuration
public class ConnectorConfig {

	@Value("${stripe.connect.timeout}")
	private int connectTimeout;

	@Value("${stripe.read.timeout}")
	private int readTimeout;

	@Scope("prototype")
	@Bean(name = "stripeRequestOptions")
	public RequestOptions gateway() {

	    RequestOptions options = RequestOptions.builder().setConnectTimeout(connectTimeout).setReadTimeout(readTimeout)
                .build();

		return options;
	}
}
