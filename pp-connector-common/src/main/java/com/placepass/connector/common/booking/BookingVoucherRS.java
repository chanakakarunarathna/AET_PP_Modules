package com.placepass.connector.common.booking;

import com.placepass.connector.common.common.BaseRS;
import com.placepass.connector.common.common.ResultType;

public class BookingVoucherRS extends BaseRS {

    private Voucher voucher;

    private String referenceNo;

    private String voucherUrl;

    private String voucherDetails;

    private ResultType resultType;

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    public String getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(String voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "BookingVoucherRS [" + (voucher != null ? "voucher=" + voucher + ", " : "") + "]";
    }

}
