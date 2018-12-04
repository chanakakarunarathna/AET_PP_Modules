package com.placepass.connector.sendgrid.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {
    public EmailTemplate findByPartnerIdAndEventName(String partnerId, String eventName);
}