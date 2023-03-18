package com.example.zahlungswerk.models;

import com.example.zahlungswerk.models.constants.VatRate;
import com.example.zahlungswerk.serializers.LocalDateTypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @JsonAdapter(LocalDateTypeAdapter.class)
    private LocalDate transactionDate;

    private BigDecimal grossAmount;
    @Enumerated(EnumType.ORDINAL)
    private VatRate vatRate;
    private String receiptId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentTransaction that = (PaymentTransaction) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
