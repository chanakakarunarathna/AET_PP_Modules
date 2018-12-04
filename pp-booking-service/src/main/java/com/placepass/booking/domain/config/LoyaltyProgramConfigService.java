package com.placepass.booking.domain.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Service
public class LoyaltyProgramConfigService {

    @Autowired
    private LoyaltyProgramConfigRepository loyaltyProgramConfigRepository;

    public List<LoyaltyProgramConfig> getLoyaltyProgramConfigDetails(String partnerId) {

        return loyaltyProgramConfigRepository.findByPartnerId(partnerId);
    }

    public LoyaltyProgramConfig getLoyaltyProgramConfigDetail(String partnerId, String loyaltyProgramId) {

        LoyaltyProgramConfig loyaltyProgramConfig = loyaltyProgramConfigRepository.findByPartnerIdAndProgId(partnerId,
                loyaltyProgramId);

        if (loyaltyProgramConfig == null) {
            throw new BadRequestException(PlacePassExceptionCodes.LOYALTY_PROGRAMME_CONFIG_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.LOYALTY_PROGRAMME_CONFIG_NOT_FOUND.getDescription());
        }

        return loyaltyProgramConfig;
    }

}
