package com.placepass.booking.application.booking;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.placepass.booking.application.cart.CartApplicationService;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.PaymentType;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.booking.cancel.RefundSummary;
import com.placepass.booking.domain.product.CancellationRules;
import com.placepass.booking.domain.product.Rules;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.currency.AmountFormatter;
import com.placepass.utils.date.DateFormatter;
import com.placepass.utils.logging.PlatformLoggingKey;

@Component
public class RefundStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RefundStrategy.class);
    
    public RefundSummary getRefundSummary(Booking booking, CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction) {

        Payment payment = booking.getPayments().stream().filter(pay -> PaymentType.SALE.equals(pay.getPaymentType()))
                .findFirst().get();

        // calculate the time difference between booking date and the cancellation date in hours
        LocalDate bookingDate = booking.getBookingOptions().get(0).getBookingDate();
        Instant bookingTime = DateFormatter.getDate(bookingDate).toInstant();
        Instant cancelTime = cancelBookingTransaction.getCreatedTime();

        Integer hoursBetweenDates = DateFormatter.getHoursBetweenDates(bookingTime, cancelTime, BigDecimal.ROUND_DOWN);
       
        if(hoursBetweenDates < 0) {
            logger.error(PlatformLoggingKey.BOOKING_REFERENCE.name()+" : "+booking.getBookingReference());
            logger.error(PlacePassExceptionCodes.BOOKING_TIME_EXCEEDED_REFUND_NOT_SUPPORTED.getDescription());
            throw new BadRequestException(PlacePassExceptionCodes.BOOKING_TIME_EXCEEDED_REFUND_NOT_SUPPORTED.toString(),
                    PlacePassExceptionCodes.BOOKING_TIME_EXCEEDED_REFUND_NOT_SUPPORTED.getDescription());
        }

        CancellationRules cRules = booking.getBookingOptions().get(0).getProductDetails().getCancellationRules();
        List<Rules> rulesList = cRules != null ? cRules.getRules() : null;
        Rules rule = null;
        if (rulesList != null) {
            Collections.sort(rulesList, new ChangeComparator());
            rule = rulesList.stream().filter(r -> {
                Integer maxHours = (r.getMaxHoursInAdvance() != null) ? r.getMaxHoursInAdvance() + 1 : 0;
                Integer minHours = r.getMinHoursInAdvance();
                return minHours <= hoursBetweenDates && (maxHours == 0 || maxHours > hoursBetweenDates);
            }).findFirst().orElse(null);
        }

        RefundSummary refundSummary = new RefundSummary();
        if (rule == null) {
            refundSummary.setMode(RefundMode.FULL);
            refundSummary.setMultiplier(1);
        } else {
            refundSummary.setMode(rule.getRefundMultiplier() == 1.0 ? RefundMode.FULL
                    : rule.getRefundMultiplier() == 0.0 ? RefundMode.NONE : RefundMode.PARTIAL);
            refundSummary.setMultiplier(rule.getRefundMultiplier());
        }

        if (refundSummary.getMode().equals(RefundMode.FULL)) {
            // payment connector handles it as a full refund if the value is set to 0
            refundSummary.setCentAmount(0);
        } else if (refundSummary.getMode().equals(RefundMode.PARTIAL)) {
            refundSummary.setCentAmount(getRefundCentValue(refundSummary.getMultiplier(), payment.getAmountTendered()));
        }

        return refundSummary;
    }

    private int getRefundCentValue(float multiplier, int salePayment) {

        float floatAmount = multiplier * salePayment;
        return AmountFormatter.floatToInt(floatAmount);
    }

    private class ChangeComparator implements Comparator<Rules> {
        public int compare(Rules r1, Rules r2) {
            return Float.compare(r2.getRefundMultiplier(), r1.getRefundMultiplier());
        }
    }
}
