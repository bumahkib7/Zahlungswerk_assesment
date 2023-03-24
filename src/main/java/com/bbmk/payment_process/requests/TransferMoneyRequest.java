package com.bbmk.payment_process.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {
    private Long customerId;
    private Long merchantId;
    private BigDecimal amount;

}
