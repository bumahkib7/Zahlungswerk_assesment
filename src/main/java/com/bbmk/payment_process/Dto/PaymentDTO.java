package com.bbmk.payment_process.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private String paymentType;
    private String paymentStatus;
    private Double amount;
    private Long customerId;
}
