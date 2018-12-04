package com.placepass.product.application.productoptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.placepass.exutil.BadRequestException;
import com.placepass.product.application.productoptions.dto.GetProductOptionsRS;
import com.placepass.product.application.productoptions.dto.PolicyDTO;
import com.placepass.product.application.productoptions.dto.PriceDTO;
import com.placepass.product.application.productoptions.dto.ProductOptionDTO;
import com.placepass.product.application.productoptions.dto.ProductOptionGroupDTO;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.VendorProduct;

public class ProductOptionsTransformer {

    public static GetProductOptionsRS toGetProductOptionsRS(ProductHashGenerator productHashGenerator,
            VendorProduct vendorProduct, com.placepass.connector.common.product.GetProductOptionsRS productOptionGroup) {

        String hashedProductOptionId = null;
        List<ProductOptionDTO> productOptionDTOs = new ArrayList<>();

        if (productOptionGroup.getResultType().getCode() == 0) {
            for (com.placepass.connector.common.product.ProductOption productOption : productOptionGroup.getProductOptionGroup().getProductOptions()) {
                ProductOptionDTO productOptionDTO = new ProductOptionDTO();
                productOptionDTO.setAvailability(productOption.getAvailability());
                productOptionDTO.setStartTime(productOption.getStartTime());
                productOptionDTO.setEndTime(productOption.getEndTime());
                productOptionDTO.setName(productOption.getName());
                productOptionDTO.setDescription(productOption.getDescription());

                List<PriceDTO> priceDTOs = new ArrayList<>();
                for (com.placepass.connector.common.product.Price price : productOption.getPrices()) {
                    PriceDTO priceDTO = new PriceDTO();
                    priceDTO.setCurrencyCode(price.getCurrencyCode());
                    priceDTO.setDescription(price.getDescription());
                    priceDTO.setPriceType(price.getPriceType());
                    if (PlacePassAgeBandType.ADULT.name().equals(price.getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                    } else if (PlacePassAgeBandType.SENIOR.name().equals(price.getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.SENIOR.ageBandId);
                    } else if (PlacePassAgeBandType.CHILD.name().equals(price.getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
                    } else if (PlacePassAgeBandType.INFANT.name().equals(price.getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
                    }else if (PlacePassAgeBandType.YOUTH.name().equals(price.getPriceType())) {
                        priceDTO.setAgeBandId(PlacePassAgeBandType.YOUTH.ageBandId);
                    }
                    priceDTO.setFinalPrice(price.getFinalPrice());
                    priceDTO.setRetailPrice(price.getRetailPrice());
                    priceDTO.setRoundedFinalPrice(
                            AmountFormatter.floatToFloatRoundingFinalTotal(price.getFinalPrice()));
                    priceDTO.setMaxBuy(price.getMaxBuy());
                    priceDTO.setMinBuy(price.getMinBuy());
                    priceDTO.setAgeFrom(price.getAgeFrom());
                    priceDTO.setAgeTo(price.getAgeTo());
                    priceDTO.setPriceGroupSortOrder(price.getPriceGroupSortOrder());
                    priceDTO.setPricingUnit(price.getPricingUnit());
                    priceDTOs.add(priceDTO);
                }
                productOptionDTO.setPrices(priceDTOs);
                productOptionDTO.setType(productOption.getType());

                if (productOption.getProductOptionId() != null) {
                    hashedProductOptionId = productHashGenerator
                            .generateHash(vendorProduct.getVendor().name() + productOption.getProductOptionId());
                    productOptionDTO.setProductOptionId(hashedProductOptionId);
                } else {
                    // FIXME: log error
                }

                if (productOption.getPolicy() != null) {
                    PolicyDTO policyDTO = new PolicyDTO();
                    policyDTO.setChildAllowed(productOption.getPolicy().isChildAllowed());
                    policyDTO.setChildPolicyMsg(productOption.getPolicy().getChildPolicyMsg());
                    policyDTO.setMaxChildAge(productOption.getPolicy().getMaxChildAge());
                    policyDTO.setMinChildAge(productOption.getPolicy().getMinChildAge());
                    productOptionDTO.setPolicy(policyDTO);
                }
                productOptionDTOs.add(productOptionDTO);
            }

        } else if (productOptionGroup.getResultType().getCode() == 3
                || productOptionGroup.getResultType().getCode() == 5) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(),
                    productOptionGroup.getResultType().getMessage());
        }
        ProductOptionGroupDTO productOptionGroupDTO = new ProductOptionGroupDTO();
        productOptionGroupDTO.setProductOptionList(productOptionDTOs);
        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        getProductOptionsRS.setProductOptionList(productOptionGroupDTO.getProductOptionList());

        return getProductOptionsRS;
    }
}
