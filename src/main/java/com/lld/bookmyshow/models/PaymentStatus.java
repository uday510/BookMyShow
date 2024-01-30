package com.lld.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

public enum PaymentStatus {
    SUCCESS,
    FAILURE,
    REFUNDED
}