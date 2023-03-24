package com.bbmk.payment_process.Dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO  {
    private Long id;
    private String name;
    private boolean active;

    private List<PaymentTransactionDTO> transactions;
}
