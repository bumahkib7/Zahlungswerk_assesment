package com.bbmk.payment_process.mixin;

import com.bbmk.payment_process.models.Merchant;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class PaymentTransactionMixIn {
    @JsonIgnore
    private Merchant merchant;
}
