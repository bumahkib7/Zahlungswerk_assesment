package com.bbmk.payment_process.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CustomerMixIn {
    @JsonIgnore
    private String email;
}
