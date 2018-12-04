package com.placepass.connector.sendgrid;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
// @EnableRabbit
// @EnableScheduling
@EnableMongoRepositories
public class PlacepassEmailApplication {
    
    /**
     * Tomcat AJP configurations
     */
    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.secure}")
    private boolean ajpSecure;

    @Value("${tomcat.ajp.allowtrace}")
    private boolean allowAjpTrace;
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        Connector ajpConnector = new Connector("AJP/1.3");
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(ajpSecure);
        ajpConnector.setAllowTrace(allowAjpTrace);
        ajpConnector.setScheme("ajp");
        ajpConnector.setRedirectPort(ajpPort);
        tomcat.addAdditionalTomcatConnectors(ajpConnector);

        return tomcat;
    }

	public static void main(String[] args) {
		SpringApplication.run(PlacepassEmailApplication.class, args);
	}

	
    // @Bean
    // public TopicExchange appExchange() {
    // return new TopicExchange(EXCHANGE_NAME);
    // }
    //
    // @Bean
    // public Queue appQueueGeneric() {
    // return new Queue(QUEUE_NAME);
    // }
    //
    // @Bean
    // public Binding declareBindingGeneric() {
    // return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    // }
    //
    // // You can comment all methods below and remove interface's implementation
    // // to use the default serialization /
    // // deserialization
    // @Bean
    // public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    // final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    // rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    // return rabbitTemplate;
    // }
    //
    // @Bean
    // public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    // return new Jackson2JsonMessageConverter();
    // }
    //
    // @Bean
    // public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    // return new MappingJackson2MessageConverter();
    // }
    //
    // @Bean
    // public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
    // DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    // factory.setMessageConverter(consumerJackson2MessageConverter());
    // return factory;
    // }
    //
    // @Override
    // public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
    // registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    // }
}
