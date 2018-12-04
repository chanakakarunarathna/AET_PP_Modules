package com.placepass.product;

import java.util.List;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.placepass.product.infrastructure.ProductApiFilter;

@SpringBootApplication
public class PpProductDomainApplication {

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

    /**
     * Supported partner ids
     */
    @Value("#{'${placepass.partnerids}'.split(',')}")
    private List<String> partnerIds;

    /** The Constant AJP_ACCEPT_COUNT. */
    private static final String AJP_ACCEPT_COUNT = "acceptCount";

    /** The Constant AJP_MAX_THREADS. */
    private static final String AJP_MAX_THREADS = "maxThreads";

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

    @Bean
    public FilterRegistrationBean bookingApiFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ProductApiFilter(partnerIds));
        // apply the filter to specific URL patterns only
        registration.addUrlPatterns("/products/*");
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(PpProductDomainApplication.class, args);
    }
}
