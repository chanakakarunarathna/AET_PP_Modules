package com.placepass.booking.application.booking;

import java.util.ArrayList;
import java.util.List;

import com.placepass.booking.application.booking.dto.VoucherDTO;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.Voucher;
import com.placepass.utils.voucher.VoucherType;

public class VoucherDTOTransformer {

    private static final String PP_VOUCHER = "PP_VOUCHER";

    public static VoucherDTO toVoucherDTO(Voucher voucher) {

        VoucherDTO voucherDTO = new VoucherDTO();
        if (voucher != null) {
            voucherDTO.setId(voucher.getId());
            voucherDTO.setVendorReference(voucher.getVendorReference());
            voucherDTO.setUrls(voucher.getUrls());
            if (VoucherType.NO_VOUCHER == voucher.getType()) {
                voucherDTO.setVoucherType(PP_VOUCHER);
            } else {
                if (voucher.getType() != null) {
                    voucherDTO.setVoucherType(voucher.getType().toString());
                }
            }
            voucherDTO.setExtendedAttributes(voucher.getExtendedAttributes());
        }
        return voucherDTO;
    }

    public static VoucherDTO toVoucherDTO(Voucher voucher, List<BookingOption> bookingOptions) {

        List<BookingOptionDTO> bookingOptionsDTO = new ArrayList<>();
        VoucherDTO voucherDTO = toVoucherDTO(voucher);
        if (!bookingOptions.isEmpty()) {
            bookingOptionsDTO.add(BookingRequestTransformer.toBookingOptionDTO(bookingOptions.get(0)));
            bookingOptionsDTO.get(0).setTotal(null);
        }
        voucherDTO.setBookingOptions(bookingOptionsDTO);
        return voucherDTO;
    }
}
