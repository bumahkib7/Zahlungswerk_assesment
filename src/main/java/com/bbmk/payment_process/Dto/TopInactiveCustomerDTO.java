package com.bbmk.payment_process.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopInactiveCustomerDTO {
    private Long numTransactions;
    private Long id;
    private String name;
    private String email;
    private Boolean isActive;

    public TopInactiveCustomerDTO(Long id, Long numTransactions) {
        this.id = id;
        this.numTransactions = numTransactions;
    }
}
