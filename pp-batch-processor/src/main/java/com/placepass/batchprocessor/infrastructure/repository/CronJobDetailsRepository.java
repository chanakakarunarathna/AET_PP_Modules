package com.placepass.batchprocessor.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.batchprocessor.domain.CronJobDetails;

@Repository
public interface CronJobDetailsRepository extends MongoRepository<CronJobDetails, String> {

    public CronJobDetails findByVendor(String vendor);
}
