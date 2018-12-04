package com.placepass.search;

import java.util.List;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.placepass.search.infrastructure.ProductApiFilter;
import com.placepass.utils.vendorproduct.ProductHashGenerator;

@SpringBootApplication
@ComponentScan({"com.placepass.search"})
public class PpSearchDomainApplication {

    /**
     * Tomcat AJP configurations
     */
    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.secure}")
    private boolean ajpSecure;

    @Value("${tomcat.ajp.allowtrace}")
    private boolean allowAjpTrace;

    /**
     * Product hash configurations
     */
    @Value("${productid.hash.multiplier}")
    private int hashMultiplier;

    @Value("${productid.hash.adder}")
    private int hashAdder;

    /**
     * Supported partner ids
     */
    @Value("#{'${placepass.partnerids}'.split(',')}")
    private List<String> partnerIds;
    
    @Value("${tomcat.ajp.maxThreads}")
    private String maxThreads;
    
    @Value("${tomcat.ajp.acceptCount}")
    private String acceptCount;
    
    /** The Constant AJP_ACCEPT_COUNT. */
    private static final String AJP_ACCEPT_COUNT = "acceptCount";

    /** The Constant AJP_MAX_THREADS. */
    private static final String AJP_MAX_THREADS = "maxThreads";

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
        registration.addUrlPatterns("/search/*");
        return registration;
    }

    @Bean(name = "ProductHashGenerator")
    public ProductHashGenerator getProductHashGenerator() {
        return ProductHashGenerator.getInstance(hashMultiplier, hashAdder);
    }

    public static void main(String[] args) {
        SpringApplication.run(PpSearchDomainApplication.class, args);
    }
}
