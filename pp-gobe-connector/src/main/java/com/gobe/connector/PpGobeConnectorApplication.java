package com.gobe.connector;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class PpGobeConnectorApplication {

    /**
     * Tomcat AJP configurations
     */
    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.secure}")
    private boolean ajpSecure;

    @Value("${tomcat.ajp.allowtrace}")
    private boolean allowAjpTrace;
    
    @Value("${tomcat.ajp.maxThreads}")
    private String maxThreads;
    
    @Value("${tomcat.ajp.acceptCount}")
    private String acceptCount;
   
    private static final String AJP_MAX_THREADS = "maxThreads";
    
    private static final String AJP_ACCEPT_COUNT = "acceptCount";

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        Connector ajpConnector = new Connector("AJP/1.3");
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(ajpSecure);
        ajpConnector.setAllowTrace(allowAjpTrace);
        ajpConnector.setScheme("ajp");
        ajpConnector.setRedirectPort(ajpPort);
        ajpConnector.setProperty(AJP_MAX_THREADS, maxThreads);
        ajpConnector.setProperty(AJP_ACCEPT_COUNT, acceptCount);
        tomcat.addAdditionalTomcatConnectors(ajpConnector);

        return tomcat;
    }

    public static void main(String[] args) {
        //for Fiddler
        //System.setProperty("http.proxyHost", "127.0.0.1");
        //System.setProperty("https.proxyHost", "127.0.0.1");
        //System.setProperty("http.proxyPort", "8888");
        //System.setProperty("https.proxyPort", "8888");
        SpringApplication.run(PpGobeConnectorApplication.class, args);
    }
}