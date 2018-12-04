package com.placepass.userservice;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.placepass.userservice", "com.placepass.eventpublisher"}, exclude = {
    MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableAsync
public class PpUserManagementApplication {

    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.secure}")
    private boolean ajpSecure;

    @Value("${tomcat.ajp.allowtrace}")
    private boolean allowAjpTrace;

    @Value("${tomcat.ajp.max.threads}")
    private String ajpMaxThreads;

    @Value("${tomcat.ajp.accept.count}")
    private String ajpAcceptCount;

    private static final String ACCEPT_COUNT = "acceptCount";

    private static final String MAX_THREADS = "maxThreads";

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        Connector ajpConnector = new Connector("AJP/1.3");
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(ajpSecure);
        ajpConnector.setAllowTrace(allowAjpTrace);
        ajpConnector.setScheme("ajp");
        ajpConnector.setRedirectPort(ajpPort);
        ajpConnector.setProperty(MAX_THREADS, ajpMaxThreads);
        ajpConnector.setProperty(ACCEPT_COUNT, ajpAcceptCount);
        tomcat.addAdditionalTomcatConnectors(ajpConnector);

        return tomcat;
    }

    public static void main(String[] args) {
        SpringApplication.run(PpUserManagementApplication.class, args);
    }

}
