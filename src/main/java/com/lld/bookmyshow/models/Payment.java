package com.lld.bookmyshow.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment extends BaseModel {
    private String referenceNumber;
    private int amount;
    private Booking booking;
    private PaymentStatus paymentStatus;
    private PaymentProvider paymentProvider;
}
