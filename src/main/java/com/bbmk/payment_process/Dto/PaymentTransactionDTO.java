package com.bbmk.payment_process.Dto;

import com.bbmk.payment_process.models.constants.VatRate;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class PaymentTransactionDTO {
    private UUID id;
    private ZonedDateTime transactionDate;
    private BigDecimal grossAmount;
    private VatRate vatRate;
    private String receiptId;
    private MerchantDTO merchant;

    private CustomerDTO customer;
}
