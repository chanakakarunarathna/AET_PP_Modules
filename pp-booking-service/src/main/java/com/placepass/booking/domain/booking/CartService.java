package com.placepass.booking.domain.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart saveCart(Cart cartToSave) {

        return cartRepository.save(cartToSave);

    }

    public Cart updateCart(Cart cartToUpdate) {

        return cartRepository.save(cartToUpdate);

    }

    public void deleteCart(String cartId) {

        cartRepository.deleteByCartId(cartId);

    }

    public Cart getCart(String cartId, String partnerId) {

        Cart cart = cartRepository.findByCartIdAndPartnerId(cartId, partnerId);

        if (cart == null) {
            throw new NotFoundException(PlacePassExceptionCodes.CART_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.CART_NOT_FOUND.getDescription());
        }

        return cart;
    }

}
