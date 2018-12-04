package com.placepass.connector.sendgrid.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.event.PlatformEventName;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Value("${rabbitmq.port}")
    private int rabbitPort;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.heartbeat.frequency}")
    private int heartbeatFrequency;

    @Value("${rabbitmq.automatic.recovery.enabled:false}")
    private boolean automaticRecoveryEnabled;

    @Value("${rabbitmq.topology.recovery.enabled:true}")
    private boolean topologyRecoveryEnabled;

    @Value("${rabbitmq.network.recovery.interval}")
    private int networkRecoveryInterval;

    // FIXME: see how we would deploy
    @Value("${rabbitmq.addresses}")
    private String addressList;

    @Value("${rabbitmq.connector.event.queuename:sendgrid.connector.events.queue}")
    private String eventQueueName;

    @Value("${rabbitmq.connector.booking.event.queuename:sendgrid.connector.booking.events.queue}")
    private String bookingEventQueueName;

    @Value("${rabbitmq.connector.user.event.queuename:sendgrid.connector.user.events.queue}")
    private String userEventQueueName;
    
    @Value("${rabbitmq.connector.admin.event.queuename:sendgrid.connector.admin.events.queue}")
    private String adminEventQueueName;    

    @Value("${rabbitmq.platform.events.exchangename:platform.events.exchange}")
    private String platformEventsExchangeName;

    // default set to production value.
    @Value("${rabbitmq.connector.fixed.queue.durable:true}")
    private boolean fixedQueueDurable;

    @Value("${rabbitmq.concurrent.consumers:15}")
    private int concurrentConsumers;

    @Value("${rabbitmq.max.concurrent.consumers:25}")
    private int maxConcurrentConsumers;
    
    @Value("${rabbitmq.listener.auto.startup:false}")
    private boolean autoStartup;


    // FIXME: for now these are read from properties. Need to see how we scale to multiple nodes if needed based on
    // queue usage. May need to have one connector since SendGrid has limits.
    // private static String PLATFORM_EVENTS_EXCHANGENAME = "platform.events.exchange";
    // private static String EVENT_QUEUENAME = "sendgrid.connector.event.queue";

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitmqClientConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitmqClientConnectionFactory.setPassword(this.password);
        rabbitmqClientConnectionFactory.setUsername(this.username);
        rabbitmqClientConnectionFactory.setRequestedHeartbeat(this.heartbeatFrequency);// check default vs what we want
        
        log.info("com.rabbitmq.client.ConnectionFactory.isAutomaticRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isAutomaticRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.isTopologyRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isTopologyRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.getNetworkRecoveryInterval():"
                + rabbitmqClientConnectionFactory.getNetworkRecoveryInterval());

        // FIXME: Spring AMQP may not be compatible
        // Spring AMQP now uses the 4.0.x version of amqp-client, which has auto recovery enabled by default.
        rabbitmqClientConnectionFactory.setAutomaticRecoveryEnabled(this.automaticRecoveryEnabled); // check default vs
                                                                                                    // what we want
        rabbitmqClientConnectionFactory.setTopologyRecoveryEnabled(this.topologyRecoveryEnabled);// check default vs
                                                                                                 // what we want
        rabbitmqClientConnectionFactory.setNetworkRecoveryInterval(this.networkRecoveryInterval);// check default vs
                                                                                                 // what we want

        // supports multiple broker addresses
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqClientConnectionFactory);
        connectionFactory.setAddresses(this.addressList);
        connectionFactory.setRequestedHeartBeat(this.heartbeatFrequency);

        log.info("CachingConnectionFactory isAutoStartup:" + connectionFactory.isAutoStartup());

        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
        messageConverter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        return messageConverter;
    }

    /**
     * To enable support for @RabbitListener annotations.
     *
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(15);
        factory.setMaxConcurrentConsumers(25);
        factory.setAutoStartup(autoStartup);
        
        // factory.setIdleEventInterval(60000L);
        return factory;
    }

    /**
     * Not enabled for request/reply pattern (no replyQueue set).
     * 
     * FIXME: RabbitMessagingTemplate vs RabbitTemplate
     * 
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public HeadersExchange platformEventsExchange() {
        return new HeadersExchange(platformEventsExchangeName);
    }

    @Deprecated
    @Bean
    public Queue eventQueue() {

        // FIXME: advanced queue arguments to evaluate
        // Map<String, Object> arguments = new HashMap<String, Object>();
        // arguments.put("x-message-ttl", 10000L); // 10 seconds.. ttl might not be needed
        // arguments.put("x-ha-policy", "");
        // arguments.put("x-ha-policy-params", "");
        // return new Queue(eventQueueName, false, true, true, arguments);

        // FIXME: queue settings.
        // Exclusive queues while prevents other connectors (connections) from reusing the queue, it will however
        // deleted when that connection closes.

        // Durable (configurable), non-exclusive and non auto-delete queue.
        return new Queue(eventQueueName, false, false, false);
    }

    @Bean
    public Queue bookingEventsQueue() {

        // FIXME: advanced queue arguments to evaluate
        // Map<String, Object> arguments = new HashMap<String, Object>();
        // arguments.put("x-message-ttl", 10000L); // 10 seconds.. ttl might not be needed
        // arguments.put("x-ha-policy", "");
        // arguments.put("x-ha-policy-params", "");
        // return new Queue(eventQueueName, false, true, true, arguments);

        // FIXME: queue settings.
        // Exclusive queues while prevents other connectors (connections) from reusing the queue, it will however
        // deleted when that connection closes.

        // Durable (configurable), non-exclusive and non auto-delete queue.
        return new Queue(bookingEventQueueName, fixedQueueDurable, false, false);
    }

    /**
     * Headers exchange, x-match all. "PLATFORM_EVENT_NAME":"BOOKING_CONFIRMATION|BOOKING_CANCELLED_FULL_REFUND|BOOKING_CANCELLED_NO_REFUND|RESEND_VOUCHER"
     */
    @Bean
    public List<Binding> bookingEventBinding(HeadersExchange platformEventsExchange, Queue bookingEventsQueue) {
        
        Map<String, Object> headerValues = new HashMap<String, Object>();
        List<Binding> bindings = new ArrayList<Binding>();
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_CONFIRMATION.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_CANCELLED_FULL_REFUND.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_CANCELLED_PARTIAL_REFUND.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_CANCELLED_NO_REFUND.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_REJECTED.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.BOOKING_PENDING.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.RESEND_VOUCHER.name());
        bindings.add(BindingBuilder.bind(bookingEventsQueue).to(platformEventsExchange).whereAll(headerValues).match());
        
        return bindings;
    }

    @Bean
    public Queue userEventQueue() {
        return new Queue(userEventQueueName, fixedQueueDurable, false, false);
    }
    
    
    @Bean
    public List<Binding> userEventBinding(HeadersExchange platformEventsExchange, Queue userEventQueue) {
        Map<String, Object> headerValues = new HashMap<String, Object>();
        List<Binding> bindings = new ArrayList<Binding>();
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.USER_CREATED.name());
        bindings.add(BindingBuilder.bind(userEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.USER_FORGOT_PASSWORD.name());
        bindings.add(BindingBuilder.bind(userEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        
        return bindings;
    }
    
    @Bean
    public Queue adminEventQueue() {
        return new Queue(adminEventQueueName, fixedQueueDurable, false, false);
    }
    
    
    @Bean
    public List<Binding> adminEventBinding(HeadersExchange platformEventsExchange, Queue adminEventQueue) {
        Map<String, Object> headerValues = new HashMap<String, Object>();
        List<Binding> bindings = new ArrayList<Binding>();
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_VERIFICATION.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_FORGOT_PASSWORD.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_ROLE_CHANGE.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_ROLE_ASSIGNMENT.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_ROLE_DELETION.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());       
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_SUPER_ADMIN_ROLE_ASSIGNMENT.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        headerValues.put(PlatformEventKey.PLATFORM_EVENT_NAME.name(), PlatformEventName.ADMIN_USER_SUPER_ADMIN_ROLE_DELETION.name());
        bindings.add(BindingBuilder.bind(adminEventQueue).to(platformEventsExchange).whereAll(headerValues).match());
        
        return bindings;
    }

    @Deprecated
    @Bean
    public Queue testQueue() {

        // FIXME: remove after initial tests!!
        return new Queue("testQueue", false, true, true);
    }

    /**
     * Headers exchange, x-match any. "PLATFORM_EVENT_NAME":"BOOKING_CONFIRMATION_EVENT", "First":"A", "Third":"C".
     */
    @Deprecated
    @Bean
    public Binding testBinding(HeadersExchange platformEventsExchange, Queue testQueue) {
        Map<String, Object> headerValues = new HashMap<String, Object>();
        headerValues.put("PLATFORM_EVENT_NAME", "A");
        headerValues.put("First", "A");
        headerValues.put("Third", "C");

        // FIXME: remove after initial tests!!
        return BindingBuilder.bind(testQueue).to(platformEventsExchange).whereAny(headerValues).match();
    }

}