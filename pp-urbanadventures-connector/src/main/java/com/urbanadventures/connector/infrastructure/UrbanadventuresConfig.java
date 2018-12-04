package com.urbanadventures.connector.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.urbanadventures.connector.application.soapclient.SoapClientServiceImpl;

@Configuration
public class UrbanadventuresConfig {

	@Value("${ua.endpointURL}")
	private String endpointURL;

	@Value("${ua.soap.connectionTimeout}")
	private int connectionTimeout;

	@Value("${ua.soap.readTimeout}")
	private int readTimeout;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.urbanadventures.connector.domain.urbanadventures");
		return marshaller;
	}

	@Bean
	public SoapClientServiceImpl soapClientServiceImpl(Jaxb2Marshaller marshaller) {
		SoapClientServiceImpl soapClientServiceImpl = new SoapClientServiceImpl();
		soapClientServiceImpl.setDefaultUri(endpointURL);
		soapClientServiceImpl.setMarshaller(marshaller);
		soapClientServiceImpl.setUnmarshaller(marshaller);
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
		httpComponentsMessageSender.setConnectionTimeout(connectionTimeout);
		httpComponentsMessageSender.setReadTimeout(readTimeout);
		soapClientServiceImpl.setMessageSender(httpComponentsMessageSender);
		return soapClientServiceImpl;
	}
}
