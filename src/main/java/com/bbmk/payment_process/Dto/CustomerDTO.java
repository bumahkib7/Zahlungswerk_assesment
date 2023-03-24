package com.bbmk.payment_process.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfRegistration;
    @JsonIgnore
    private List<PaymentTransactionDTO> transactions;
    private boolean active;

}
