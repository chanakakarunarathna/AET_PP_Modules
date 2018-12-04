package com.placepass.booking.domain.booking.cancel;

import java.util.HashMap;
import java.util.Map;

public class VendorCancellationReasons {

    public static Map<String, String> getViatorCancellationReasons() {

        Map<String, String> viatorCancellationReasons = new HashMap<>();
        
        viatorCancellationReasons.put("00", "Testing");
        viatorCancellationReasons.put("51", "Airline Flight Cancellation - Affects Customer/Traveller");
        viatorCancellationReasons.put("52", "Airline Schedule Change - Unacceptable to Customer/Traveller");
        viatorCancellationReasons.put("53", "Death - Customer/Traveller or Immediate Family");
        viatorCancellationReasons.put("54", "Jury Duty/Court Summons - Affects Customer/Traveller");
        viatorCancellationReasons.put("55", "Discretionary Cancellation (Viator Use Only)");
        viatorCancellationReasons.put("56", "Medical Emergency/Hospitalization - Customer/Traveller or Immediate Family");
        viatorCancellationReasons.put("57", "Military Service - Affects Customer/Traveller");
        viatorCancellationReasons.put("58", "National Disaster (Insurrection, Terrorism, War) -Affects Customer/Traveller");
        viatorCancellationReasons.put("59", "Natural Disaster (Earthquake, Fire, Flood) - AffectsCustomer/Traveller");
        viatorCancellationReasons.put("62", "Service Complaint - Denied Trip Add On Service");
        viatorCancellationReasons.put("63", "Transport Strike/Labor Dispute - Affects Customer/Traveller");
        viatorCancellationReasons.put("66", "Trip Add On Supplier Cancellation");
        viatorCancellationReasons.put("71", "Credit Card Fraud");
        viatorCancellationReasons.put("72", "Car Segment Cancellation - Affects Customer/Traveller");
        viatorCancellationReasons.put("73", "Package Segment Cancellation - Affects Customer/Traveller");
        viatorCancellationReasons.put("74", "Hotel Segment Cancellation - Affects Customer/Traveller");
        viatorCancellationReasons.put("77", "Re-book");
        viatorCancellationReasons.put("78", "Duplicate Purchase");
        viatorCancellationReasons.put("82", "Honest Mistake - Incorrect Purchase");
        viatorCancellationReasons.put("87", "Non-Refundable Cancellation (Outside 2 Days of Travel/Not Cencellation Event)");
        viatorCancellationReasons.put("88", "Non-Refundable Cancellation (Within 2 Days of Travel)");
        viatorCancellationReasons.put("98", "Customer Service/Technical Support Response Outside Time Limit");
        viatorCancellationReasons.put("99", "Duplicate Processing");
        
        return viatorCancellationReasons;
    }

    public static Map<String, String> getUACancellationReasons() {

        Map<String, String> uaCancellationReasons = new HashMap<>();

        return uaCancellationReasons;
    }
}
