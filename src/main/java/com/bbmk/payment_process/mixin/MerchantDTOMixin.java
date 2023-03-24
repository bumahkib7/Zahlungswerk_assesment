package com.bbmk.payment_process.mixin;

import com.bbmk.payment_process.Dto.PaymentTransactionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;


public abstract class MerchantDTOMixin {

    @JsonIgnore
    public BigDecimal balance;

    @JsonIgnore
    public Long version;

    @JsonIgnore
    public abstract List<PaymentTransactionDTO> getTransactions();


}

