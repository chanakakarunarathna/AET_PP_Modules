package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.user.User;

/**
 * The Interface UserRepository.
 * 
 * @author shanakak
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find by email and partner id and deleted.
     *
     * @param email the email
     * @param partnerId the partner id
     * @param deleted the deleted
     * @return the user
     */
    public User findByEmailAndPartnerIdAndDeleted(String email, String partnerId, boolean deleted);

    /**
     * Find by id and partner id and deleted.
     *
     * @param id the id
     * @param partnerId the partner id
     * @param deleted the deleted
     * @return the user
     */
    public User findByIdAndPartnerIdAndDeleted(String id, String partnerId, boolean deleted);

    /**
     * Find by external user id and partner id and deleted.
     *
     * @param externalUserId the external user id
     * @param partnerId the partner id
     * @param deleted the deleted
     * @return the user
     */
    public User findByExternalUserIdAndPartnerIdAndDeleted(String externalUserId, String partnerId, boolean deleted);

}
