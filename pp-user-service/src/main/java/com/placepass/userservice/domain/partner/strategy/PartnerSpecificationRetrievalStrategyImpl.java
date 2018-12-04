package com.placepass.userservice.domain.partner.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.userservice.domain.partner.PartnerAuthenticationTokenExpirationSpecification;
import com.placepass.userservice.domain.partner.PartnerVerificationSpecification;
import com.placepass.userservice.infrastructure.repository.PartnerAuthenticationTokenExpirationSpecificationRepository;
import com.placepass.userservice.infrastructure.repository.PartnerVerificationSpecificationRepository;

@Service
public class PartnerSpecificationRetrievalStrategyImpl implements PartnerSpecificationRetrievalStrategy {

	@Autowired
	private PartnerVerificationSpecificationRepository partnerVerificationSpecificationRepository;
	
	@Autowired
    private PartnerAuthenticationTokenExpirationSpecificationRepository partnerAuthenticationTokenExpirationSpecificationRepository;
	
	@Value("${placepass.verification.check.enabled}")
	private boolean verificationCheckEnabled;
	
	@Value("${placepass.user.authentication.token.timeout}")
    private long userAuthenticationTokenTimeout;
	
	@Override
	public boolean checkVerificationEnabled(String partnerId) {
		PartnerVerificationSpecification partnerVerificationSpecification = partnerVerificationSpecificationRepository
				.findByPartnerId(partnerId);

		if (partnerVerificationSpecification != null) {
			return partnerVerificationSpecification.isVerificationEnabled();
		}

		return verificationCheckEnabled;
	}

    @Override
    public long retrieveUserAuthenticationTokenTimeout(String partnerId) {
        PartnerAuthenticationTokenExpirationSpecification partnerAuthenticationTokenExpirationSpecification = partnerAuthenticationTokenExpirationSpecificationRepository.findByPartnerId(partnerId);
        
        if (partnerAuthenticationTokenExpirationSpecification != null) {
            return partnerAuthenticationTokenExpirationSpecification.getUserAuthenticationTokenTimeout();
        }
        
        return userAuthenticationTokenTimeout;
    }

}
