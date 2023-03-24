package com.bbmk.payment_process.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long id;

    @Version
    private Long version;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfRegistration;

    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "balance")
    private BigDecimal balance;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PaymentTransaction> transactions = new ArrayList<>();


    public Customer(Long id, @NotNull String name, @NotNull String email, LocalDate dateOfRegistration, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.isActive = active;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
