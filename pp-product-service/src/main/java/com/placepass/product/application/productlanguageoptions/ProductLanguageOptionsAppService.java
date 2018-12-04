package com.placepass.product.application.productlanguageoptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.product.application.productlanguageoptions.dto.GetProductLanguageOptions;
import com.placepass.product.infrastructure.AlgoliaSearchClient;
import com.placepass.product.infrastructure.RestClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.VendorProduct;

@Service
public class ProductLanguageOptionsAppService {

    @Autowired
    RestClient restClient;

    @Autowired
    AlgoliaSearchClient algoliaSearchClient;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    public GetProductLanguageOptions getProductLanguageOptions(String bookingOptionId, String productId) {
        VendorProduct vc = null;
        try {
            vc = VendorProduct.getInstance(productId, productHashGenerator);
        } catch (Exception e) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        }
        GetProductLanguageOptions getProductLanguageOptions = algoliaSearchClient.getProductLanguageOptions(bookingOptionId, productId);
        return getProductLanguageOptions;
    }

}
