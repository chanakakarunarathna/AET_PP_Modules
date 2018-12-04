package com.placepass.booking.application.cart;

import java.util.ArrayList;
import java.util.List;

import com.placepass.connector.common.cart.GetProductPriceRQ;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.booking.domain.product.Price;

public class CartConnectorTransformer {

    public static GetProductPriceRQ toGetProductPriceCRQ(BookingOption bookingOption) {

        GetProductPriceRQ getProductPriceCRQ = new GetProductPriceRQ();

        getProductPriceCRQ.setProductId(bookingOption.getProductId());
        getProductPriceCRQ.setVendorProductId(bookingOption.getVendorProductId());
        getProductPriceCRQ.setProductOptionId(bookingOption.getProductOptionId());
        getProductPriceCRQ.setVendorProductOptionId(bookingOption.getVendorProductOptionId());

        getProductPriceCRQ.setBookingDate(bookingOption.getBookingDate());

        List<com.placepass.connector.common.cart.Quantity> quantityCDTOs = new ArrayList<>();
        if (bookingOption.getQuantities() != null) {
            for (Quantity quantity : bookingOption.getQuantities()) {

                com.placepass.connector.common.cart.Quantity quantityCDTO = new com.placepass.connector.common.cart.Quantity();
                quantityCDTO.setAgeBandId(quantity.getAgeBandId());
                quantityCDTO.setAgeBandLabel(quantity.getAgeBandLabel());
                quantityCDTO.setQuantity(quantity.getQuantity());

                quantityCDTOs.add(quantityCDTO);
            }
            getProductPriceCRQ.setQuantities(quantityCDTOs);
        }

        List<com.placepass.connector.common.cart.Price> priceCDTOs = new ArrayList<>();
        if (bookingOption.getPrices() != null) {
            for (Price price : bookingOption.getPrices()) {

                com.placepass.connector.common.cart.Price priceCDTO = new com.placepass.connector.common.cart.Price();

                priceCDTO.setPriceGroupSortOrder(price.getPriceGroupSortOrder());
                priceCDTO.setAgeBandId(price.getAgeBandId());
                priceCDTO.setAgeFrom(price.getAgeFrom());
                priceCDTO.setAgeTo(price.getAgeTo());
                priceCDTO.setCurrencyCode(price.getCurrencyCode());
                priceCDTO.setDescription(price.getDescription());
                priceCDTO.setFinalPrice(price.getFinalPrice());
                priceCDTO.setMaxBuy(price.getMaxBuy());
                priceCDTO.setMinBuy(price.getMinBuy());
                priceCDTO.setMerchantPrice(price.getMerchantPrice());
                priceCDTO.setPriceType(price.getPriceType());
                priceCDTO.setRetailPrice(price.getRetailPrice());
                priceCDTO.setPricingUnit(price.getPricingUnit());

                priceCDTOs.add(priceCDTO);

            }
            getProductPriceCRQ.setPrices(priceCDTOs);
        }
        return getProductPriceCRQ;

    }

}
