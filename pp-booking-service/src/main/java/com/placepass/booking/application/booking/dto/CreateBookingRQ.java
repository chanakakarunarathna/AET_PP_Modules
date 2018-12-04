package com.placepass.booking.application.booking.dto;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CreateBookingRequest")
public class CreateBookingRQ {

    // No validations added here, since this is extracted from the header
    private String partnerId = null;

    @Valid
    @NotNull(message = "Payment details is required")
    private PaymentRQ payment = null;

    // No validations added here, since this is extracted from the URL param
    private String cartId = null;

    /**
     * A booking cannot be accepted without an email, therefore either in the guest checkout flow or logged in user
     * flow, for the known email; caller is responsible for creating/retrieving a customer and then populating
     * customerId here. In the guest checkout flow; a guest user can be generated with default password.
     */
    @NotNull(message = "Customer id is required")
    @NotEmpty(message = "Customer id can not be empty")
    private String customerId;

    // FIXME: optional or required??
    // if a lookup is made to pp-customer-service or auth-server to validate userId/email then can make this optional
    // (can optimize later)??
    // does booking call require authentication for both guest and typical logged in user??
    @NotNull(message = "Customer email is required")
    @NotEmpty(message = "Customer email can not be empty")
    private String email;

    private Map<String, String> extendedAttributes;

    private LoyaltyAccountDTO loyaltyAccount;
    
    private List<DiscountDTO> discounts;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the payment
     */
    public PaymentRQ getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(PaymentRQ payment) {
        this.payment = payment;
    }

    /**
     * Gets the cart id.
     *
     * @return the cart id
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * Sets the cart id.
     *
     * @param cartId the new cart id
     */
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public LoyaltyAccountDTO getLoyaltyAccount() {
        return loyaltyAccount;
    }

    public void setLoyaltyAccount(LoyaltyAccountDTO loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
    }

    public List<DiscountDTO> getDiscounts() {
        return discounts;
    }
    
}
