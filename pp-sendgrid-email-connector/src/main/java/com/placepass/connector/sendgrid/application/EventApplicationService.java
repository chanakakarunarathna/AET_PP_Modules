package com.placepass.connector.sendgrid.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.placepass.connector.sendgrid.domain.EmailPlaceholderKey;
import com.placepass.connector.sendgrid.domain.EmailTemplate;
import com.placepass.connector.sendgrid.domain.EmailTemplateRepository;
import com.placepass.connector.sendgrid.infrastructure.JsonUtils;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.event.PlatformEventKey;
import com.placepass.utils.event.PlatformEventName;
import com.placepass.utils.vendorproduct.Vendor;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EventApplicationService {

    private static final Logger log = LoggerFactory.getLogger(EventApplicationService.class);

    @Autowired
    private EmailTemplateRepository emailTemplateRepo;

    @Value("${default.product.image.url}")
    private String defaultProductImageUrl;

    // FIXME: be careful of any hard-coded encoding due to internationalization
    private static final String UTF_8 = "UTF-8";

    @RabbitListener(queues = {"#{@bookingEventsQueue}", "#{@userEventQueue}", "#{@adminEventQueue}"})
    public void receiveMessage(final Message message) throws JsonParseException, JsonMappingException, IOException {

        try {
            log.info("Message received. Event message body: {}", new String(message.getBody()));
            log.debug("Message received EVENT class: {}", message.getClass().getName());
            log.debug("Message received EVENT message: {}", message);
            log.debug("Message received EVENT message body: {}", new String(message.getBody()));
            log.debug("Received message as generic: {}", message.toString());

            final String jsonMessageBody = new String(message.getBody(), UTF_8);
            Map<String, String> eventAttributes = JsonUtils.fromJson(jsonMessageBody,
                    new TypeReference<Map<String, String>>() {
                    });

            // get PartnerId
            String partnerId = (String) message.getMessageProperties().getHeaders()
                    .get(PlatformEventKey.PARTNER_ID.name());
            // get event name
            String eventName = (String) message.getMessageProperties().getHeaders()
                    .get(PlatformEventKey.PLATFORM_EVENT_NAME.name());

            // get emailTemplate from Mongo
            // ::CAREFUL:: Mongo searches are case sensitive
            log.info("Retrieving email template for partnerId: {}, eventName: {}", partnerId, eventName);
            EmailTemplate template = emailTemplateRepo.findByPartnerIdAndEventName(partnerId, eventName);

            if (template == null) {
                log.error("Email Template not found for the partnerId: {}, eventName: {}", partnerId, eventName);
            } else {
                // Send email via SendGrid
                sendEmail(template, eventAttributes, eventName, false);
            }
        } catch (Exception e) {
            log.error("Exception occurred while processing the event", e);

        }

    }

    public void sendEmail(String partnerId, String eventName, Map<String, String> eventAttributes){
    	
    	log.info("Retrieving email template for partnerId: {}, eventName: {}", partnerId, eventName);
    	EmailTemplate template = emailTemplateRepo.findByPartnerIdAndEventName(partnerId, eventName);
    	if (template != null){
    		sendEmail(template, eventAttributes, eventName, true);
    	}else{
        	log.error("Couldn't retrieve email template for partnerId: {}, eventName: {}", partnerId, eventName);
    		throw new NotFoundException(PlacePassExceptionCodes.EMAIL_TEMPLATE_NOT_FOUND.toString(), PlacePassExceptionCodes.EMAIL_TEMPLATE_NOT_FOUND.getDescription());
    	}
    	
    }
    
    public void sendEmail(EmailTemplate template, Map<String, String> eventAttributes, String eventName, boolean isRestCall)
         {
        // build base email object
        Mail mail = new Mail();
        mail.setFrom(new Email(template.getFromEmail()));
        mail.setTemplateId(template.getTemplateId());

        // Subject to be pulled from Mongo
        mail.setSubject(template.getSubject());

        // personalize email
        // cant seem to put a TO directly in the Mail object without providing
        // "content"
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(eventAttributes.get(PlatformEventKey.CUSTOMER_EMAIL.name())));

        // Should we pass in everything that comes in the Event or pull the
        // Template and try to map that way?
        // Template placeholders will be retrieved from Mongo?

        this.updateEventAttributes(template, eventName, eventAttributes);
        log.info("--------- Event attributes: {}", eventAttributes);

        for (String placeholder : template.getEmailPlaceholders()) {
            // check if configured placeholder came as EventAttribute
            if (eventAttributes.containsKey(placeholder)) {
                // if so - replace with value sendgrid placeholders are prefixed with a :
                personalization.addSubstitution("-" + placeholder + "-", eventAttributes.get(placeholder));
            } else {
                // if not - replace with empty string to avoid the having the
                // placeholder still be present in email sent
                personalization.addSubstitution("-" + placeholder + "-", "");
            }
        }

        // Attach personalization to base email object
        mail.addPersonalization(personalization);

        // APIKey - to be pulled from Mongo?
        SendGrid sendGrid = new SendGrid(template.getApiKey());
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("content-type", "text/html; charset=utf-8");
            request.headers = headers;
            Response response = sendGrid.api(request);

            log.info("sendgrid api response statusCode: {}", response.statusCode);
            log.info("sendgrid api response body: {}", response.body);
            log.info("sendgrid api response headers: {}", response.headers);
        } catch (IOException ex) {
            log.error("IOException occurred while sending email", ex);
            if (isRestCall){
            	throw new InternalErrorException(PlacePassExceptionCodes.EMAIL_SEND_FAILED.toString(), PlacePassExceptionCodes.EMAIL_SEND_FAILED.getDescription(), ex); 
            }
        } catch (Exception ex) {
            log.error("Exception occurred while sending email", ex);
            if (isRestCall){
            	throw new InternalErrorException(PlacePassExceptionCodes.EMAIL_SEND_FAILED.toString(), PlacePassExceptionCodes.EMAIL_SEND_FAILED.getDescription(), ex); 
            }
        }
    }

    /**
     * Update event attributes based on connector configurations or template.
     * 
     * @param template
     * @param eventName
     * @param eventAttributes
     */
    private void updateEventAttributes(EmailTemplate template, String eventName, Map<String, String> eventAttributes) {

        if (PlatformEventName.BOOKING_CONFIRMATION.name().equals(eventName)
                || (PlatformEventName.BOOKING_CANCELLED_FULL_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_CANCELLED_PARTIAL_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_CANCELLED_NO_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_PENDING.name().equals(eventName))
                || (PlatformEventName.BOOKING_REJECTED.name().equals(eventName))) {

            // if product image is empty, set the default image
            if ((eventAttributes.get(PlatformEventKey.PRODUCT_IMAGE_URL.name()) == null
                    || eventAttributes.get(PlatformEventKey.PRODUCT_IMAGE_URL.name()).isEmpty())) {
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), defaultProductImageUrl);
            }

            // if image URL starts with '//' prepend 'https:'
            String productImageUrl = eventAttributes.get(PlatformEventKey.PRODUCT_IMAGE_URL.name());
            if (productImageUrl.startsWith("//")) {
                StringBuilder productImageUrlBuilder = new StringBuilder(productImageUrl);
                productImageUrlBuilder = productImageUrlBuilder.insert(0, "https:");
                eventAttributes.put(PlatformEventKey.PRODUCT_IMAGE_URL.name(), productImageUrlBuilder.toString());
            }

            // Construct MoreDetailsURL with per partner base URL.
            constructMoreDetailsURLEventAttribute(template, eventAttributes);
        }

        if ((PlatformEventName.BOOKING_CONFIRMATION.name().equals(eventName))
                || (PlatformEventName.BOOKING_CANCELLED_FULL_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_CANCELLED_PARTIAL_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_CANCELLED_NO_REFUND.name().equals(eventName))
                || (PlatformEventName.BOOKING_PENDING.name().equals(eventName))
                || (PlatformEventName.BOOKING_REJECTED.name().equals(eventName))) {

            if (eventAttributes.get(PlatformEventKey.VENDOR.name()) != null && template.getVendorImageUrls() != null
                    && !template.getVendorImageUrls().isEmpty()) {
                String vendorImageURL = template.getVendorImageUrls()
                        .get(eventAttributes.get(PlatformEventKey.VENDOR.name()));
                if (StringUtils.hasText(vendorImageURL)) {
                    eventAttributes.put(PlatformEventKey.VENDOR_IMAGE_URL.name(), vendorImageURL);
                }
            }
        }

    }

    /**
     * This is a specific case where, URL construction happens in the connector due to per partner nature of it.
     * 
     * @param template
     * @param eventAttributes
     */
    private void constructMoreDetailsURLEventAttribute(EmailTemplate template, Map<String, String> eventAttributes) {

        String bookingID = eventAttributes.get(PlatformEventKey.BOOKING_ID.name());
        String moreDetailsFullURLWithTemplate = template.getMoreDetailsFullURLWithTemplate();
        log.info("template.getMoreDetailsFullURLWithTemplate(): " + moreDetailsFullURLWithTemplate);
        log.info("Booking ID: " + bookingID);

        if (StringUtils.hasText(bookingID) && StringUtils.hasText(moreDetailsFullURLWithTemplate)) {

            // 1. with partner base URL from template instance, construct full URL by replacing bookingid with value
            String moreDetailsURL = moreDetailsFullURLWithTemplate.replace("{bookingid}", bookingID);
            // 2. set the enriched value in eventAttributes
            eventAttributes.put(EmailPlaceholderKey.MORE_DETAILS_URL.name(), moreDetailsURL);
        }
    }

}