package com.bbmk.payment_process.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Merchant {

    @Column(name = "merchant_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance")
    private BigDecimal balance;

    @Version
    private Long version;
    @NotNull
    private String name;

    private boolean active;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PaymentTransaction> transactions = new ArrayList<>();

    public Merchant(String name, boolean active) {
        this.name = name;
        this.active = active;

    }

    public Merchant(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Merchant merchant)) return false;

        if (isActive() != merchant.isActive()) return false;
        if (getId() != null ? !getId().equals(merchant.getId()) : merchant.getId() != null) return false;
        if (getName() != null ? !getName().equals(merchant.getName()) : merchant.getName() != null) return false;
        return getTransactions() != null ? getTransactions().equals(merchant.getTransactions()) : merchant.getTransactions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
        return result;
    }
}
