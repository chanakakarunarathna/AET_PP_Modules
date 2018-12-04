package com.placepass.userservice.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.placepass.userservice.domain.user.UserAuthenticationToken;

/**
 * The Interface UserTokenRepository. This is a Redis repository
 */
@Repository
public interface UserAuthenticationTokenRepository extends CrudRepository<UserAuthenticationToken, String> {

	

}
