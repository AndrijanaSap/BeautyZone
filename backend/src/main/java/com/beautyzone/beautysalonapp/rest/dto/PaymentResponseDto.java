package com.beautyzone.beautysalonapp.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    String orderNumber;
    String language;
    String approval_code;
    String signature;
}
