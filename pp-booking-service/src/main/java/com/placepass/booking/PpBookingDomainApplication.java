package com.placepass.booking;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.catalina.connector.Connector;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.placepass.booking.application.authorize.BookingAuthorizationApplicationService;
import com.placepass.booking.infrastructure.BookingApiFilter;
import com.placepass.booking.infrastructure.BookingAsyncExceptionHandler;
import com.placepass.booking.infrastructure.BookingAuthorizationFilter;
import com.placepass.utils.vendorproduct.ProductHashGenerator;

@SpringBootApplication(scanBasePackages = {"com.placepass.booking", "com.placepass.eventpublisher"})
@IntegrationComponentScan
@EnableAsync
public class PpBookingDomainApplication extends AsyncConfigurerSupport {

    private final static String AJP_MAX_THREADS = "maxThreads";

    private final static String AJP_ACCEPT_COUNT = "acceptCount";

    /**
     * Product hash configurations
     */
    @Value("${vendorproduct.hash.multiplier}")
    private int hashMultiplier;

    @Value("${vendorproduct.hash.adder}")
    private int hashAdder;

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
     * Supported partner ids
     */
    @Value("#{'${placepass.partnerids}'.split(',')}")
    private List<String> partnerIds;

    @Value("${com.placepass.bookingservice.async.corepoolsize:3}")
    private int asyncCorePoolSize;

    @Value("${com.placepass.bookingservice.async.maxpoolsize:3}")
    private int asyncMaxPoolSize;

    @Value("${com.placepass.bookingservice.async.queuecapacity:500}")
    private int asyncQueueCapacity;

    @Value("${com.placepass.bookingservice.async.threadnameprefix:pp-booking-service-async-thread-}")
    private String asyncThreadNamePrefix;

    @Value("#{'${booking.service.filter.validatePartnerUrlPatterns}'.split(',')}")
    private String[] validatePartnerUrlPatterns;

    @Value("#{'${booking.service.filter.authenticateUserUrlPatterns}'.split(',')}")
    private String[] authenticateUserUrlPatterns;

    @Value("${tomcat.ajp.maxThreads}")
    private String maxThreads;

    @Value("${tomcat.ajp.acceptCount}")
    private String acceptCount;

    @Autowired
    private BookingAuthorizationApplicationService bookingAuthorizationApplicationService;

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
        registration.setFilter(new BookingApiFilter(partnerIds));
        // apply the filter to specific URL patterns only
        registration.addUrlPatterns(validatePartnerUrlPatterns);
        return registration;
    }

    @Bean
    public FilterRegistrationBean authenticateFilter() {
        FilterRegistrationBean authorization = new FilterRegistrationBean();
        authorization.setFilter(new BookingAuthorizationFilter(bookingAuthorizationApplicationService));
        authorization.addUrlPatterns(authenticateUserUrlPatterns);
        return authorization;
    }

    @Bean(name = "productHashGenerator")
    public ProductHashGenerator getProductHashGenerator() {
        return ProductHashGenerator.getInstance(hashMultiplier, hashAdder);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncCorePoolSize);
        executor.setMaxPoolSize(asyncMaxPoolSize);
        executor.setQueueCapacity(asyncQueueCapacity);
        executor.setThreadNamePrefix(asyncThreadNamePrefix);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new BookingAsyncExceptionHandler();
    }

    public static void main(String[] args) {
        SpringApplication.run(PpBookingDomainApplication.class, args);
    }
}
