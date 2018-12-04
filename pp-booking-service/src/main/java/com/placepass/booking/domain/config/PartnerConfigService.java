package com.placepass.booking.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Service
public class PartnerConfigService {

    @Autowired
    PartnerConfigRepository partnerConfigRepository;

    public PartnerConfig getPartnerConfig(String partnerId) {
        PartnerConfig partnerConfig = partnerConfigRepository.findByPartnerId(partnerId);

        if (partnerConfig == null) {
            throw new NotFoundException(PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.toString(),PlacePassExceptionCodes.PARTNER_CONFIG_NOT_FOUND.getDescription());
        }

        return partnerConfig;
    }

}
