package com.lld.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDTO {
     private ResponseStatus responseStatus;
     private long bookingId;
     private int amount;
}
