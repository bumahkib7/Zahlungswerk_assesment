package com.bbmk.payment_process.models;

import com.bbmk.payment_process.models.constants.VatRate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PaymentTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @CreatedDate
    private ZonedDateTime transactionDate;

    private BigDecimal grossAmount;
    @Enumerated(EnumType.ORDINAL)
    private VatRate vatRate;
    private String receiptId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTransaction that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTransactionDate() != null ? !getTransactionDate().equals(that.getTransactionDate()) : that.getTransactionDate() != null)
            return false;
        if (getGrossAmount() != null ? !getGrossAmount().equals(that.getGrossAmount()) : that.getGrossAmount() != null)
            return false;
        if (getVatRate() != that.getVatRate()) return false;
        if (getReceiptId() != null ? !getReceiptId().equals(that.getReceiptId()) : that.getReceiptId() != null)
            return false;
        if (getCustomer() != null ? !getCustomer().equals(that.getCustomer()) : that.getCustomer() != null)
            return false;
        return getMerchant() != null ? getMerchant().equals(that.getMerchant()) : that.getMerchant() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTransactionDate() != null ? getTransactionDate().hashCode() : 0);
        result = 31 * result + (getGrossAmount() != null ? getGrossAmount().hashCode() : 0);
        result = 31 * result + (getVatRate() != null ? getVatRate().hashCode() : 0);
        result = 31 * result + (getReceiptId() != null ? getReceiptId().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getMerchant() != null ? getMerchant().hashCode() : 0);
        return result;
    }
}
