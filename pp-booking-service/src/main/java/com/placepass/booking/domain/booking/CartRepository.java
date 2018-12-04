package com.placepass.booking.domain.booking;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

    public Cart findByCartId(String cartId);

    public void deleteByCartId(String cartId);
    
    public Cart findByCartIdAndPartnerId(String cartId, String partnerId);

}
