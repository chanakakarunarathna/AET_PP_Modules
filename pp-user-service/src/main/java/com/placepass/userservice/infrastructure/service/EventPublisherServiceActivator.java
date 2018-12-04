package com.placepass.userservice.infrastructure.service;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import com.placepass.eventpublisher.application.EventPublisherService;
import com.placepass.userservice.platform.common.CommonConstants;
import com.placepass.utils.event.PlatformEvent;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.event.PlatformEventName;

@MessageEndpoint
public class EventPublisherServiceActivator {

	/** The logger. */
	private org.slf4j.Logger logger = LoggerFactory.getLogger(EventPublisherServiceActivator.class);
	
	@Value("${rabbitmq.platform.events.exchangename}")
    private String platformEventsExchangeName;
	
	@Autowired
	private EventPublisherService eventPublisherService;
	
	@ServiceActivator(inputChannel = "event.request.channel")
	public Map<String, String> publishEvent(Map<String, String> notificationEventParams) {

		String eventName = notificationEventParams.get(CommonConstants.PLATFORM_EVENT_NAME);
		String partnerId = notificationEventParams.get(CommonConstants.PARTNER_ID);
		String userId = notificationEventParams.get(CommonConstants.USER_ID);
		String firstName = notificationEventParams.get(CommonConstants.FIRST_NAME);
		String email = notificationEventParams.get(CommonConstants.EMAIL);
		String verificationUrl = notificationEventParams.get(CommonConstants.VERIFICATION_URL);

		PlatformEvent platformEvent = new PlatformEvent();
		platformEvent.setEventName(PlatformEventName.valueOf(eventName).name());
		platformEvent.getEventAttributes().put(PlatformEventKey.PARTNER_ID.name(), partnerId);
		platformEvent.getEventAttributes().put(PlatformEventKey.CUSTOMER_ID.name(), userId);
		platformEvent.getEventAttributes().put(PlatformEventKey.CUSTOMER_FIRST_NAME.name(), firstName);
		platformEvent.getEventAttributes().put(PlatformEventKey.CUSTOMER_EMAIL.name(), email);
		platformEvent.getEventAttributes().put(PlatformEventKey.VERIFICATION_URL.name(), verificationUrl);

		logger.debug("Event parameters : " + platformEvent.toString());

		eventPublisherService.sendEvent(partnerId, userId, userId, CommonConstants.USER_ENTITY_NAME, userId, platformEvent,
				platformEventsExchangeName);

		return notificationEventParams;
	}
}
