package com.placepass.connector.bemyguest.domain.bemyguest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.exutil.NotFoundException;

@Service
public class BmgProductDetailService {

    @Autowired
    private BmgProductDetailRepository bmgProductDetailRepository;

    public BmgProductDetail getBmgDetails(String productId) {

        BmgProductDetail bmgProductDetail = bmgProductDetailRepository.findByUuid(productId);

        return bmgProductDetail;
    }

}
