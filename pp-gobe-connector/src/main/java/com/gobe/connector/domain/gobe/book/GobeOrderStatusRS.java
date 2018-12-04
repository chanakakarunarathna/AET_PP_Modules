package com.gobe.connector.domain.gobe.book;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by duyvu on 8/8/2017.
 */
@Document(collection = "GobeOrderStatus")
public class GobeOrderStatusRS {

    private String orderStatus;

    private String  orderCode;

    private List<OrderStat> bookings;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<OrderStat> getBookings() {
        return bookings;
    }

    public void setBookings(List<OrderStat> bookings) {
        this.bookings = bookings;
    }
}
