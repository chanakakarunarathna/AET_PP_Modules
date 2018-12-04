package com.placepass.userservice.domain.user;

import java.util.List;

import org.springframework.data.domain.Pageable;

/**
 * The Interface ArchivedPasswordService.
 */
public interface ArchivedPasswordService {
	
	
	/**
	 * Creates the archived password.
	 *
	 * @param archivedPassword the archived password
	 * @return the archived password
	 */
	public ArchivedPassword createArchivedPassword(ArchivedPassword archivedPassword);
	
	/**
	 * Retrieve archived password.
	 *
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the list
	 */
	public List<ArchivedPassword> retrieveArchivedPassword(String partnerId, String userId, Pageable pageable);
	

}
