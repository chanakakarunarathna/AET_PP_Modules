package com.placepass.booking.application.cart.dto;

import java.util.ArrayList;
import java.util.List;

import com.placepass.booking.application.booking.dto.FeeDTO;

public class CreateCartRS {

    private String cartId;
    
    private List<FeeDTO> fees;
    
    private TotalDTO total;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

    public List<FeeDTO> getFees() {
        if (fees == null) {
            fees = new ArrayList<FeeDTO>();
        }
        return fees;
    }
    
}
