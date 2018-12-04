package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<ActivityDisplay, String> {

    public ActivityDisplay findByActivityID(int activityId);
    
}
