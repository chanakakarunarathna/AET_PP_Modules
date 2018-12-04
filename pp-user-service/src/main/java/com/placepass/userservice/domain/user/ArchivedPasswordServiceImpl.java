package com.placepass.userservice.domain.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.placepass.userservice.infrastructure.repository.ArchivedPasswordRepository;

/**
 * The Class PasswordHistoryServiceImpl.
 */
@Service
public class ArchivedPasswordServiceImpl implements ArchivedPasswordService {

	@Autowired
	private ArchivedPasswordRepository passwordHistoryRepository;

	@Override
	public ArchivedPassword createArchivedPassword(ArchivedPassword passwordHistory) {
		Date createdDate = new Date();
		passwordHistory.setCreatedDate(createdDate);
		passwordHistory.setModifiedDate(createdDate);

		return passwordHistoryRepository.save(passwordHistory);
	}

	@Override
	public List<ArchivedPassword> retrieveArchivedPassword(String partnerId, String userId, Pageable pageable) {
		return passwordHistoryRepository.findByUserIdAndPartnerId(userId, partnerId, pageable);
	}

}
