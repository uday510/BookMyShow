package com.lld.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupResponseDTO {
    private ResponseStatus responseStatus;
    private Long userId;
}
