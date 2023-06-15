package com.anikanov.domain.model;

import com.anikanov.domain.CurrencyEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;
    @Column(name = "card_number", nullable = false, unique = true)
    private String number;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column(name = "double_conversion_enabled", nullable = false)
    private boolean doubleConversionEnabled = false;
    @Column(name = "card_currency", nullable = false)
    private CurrencyEnum currency;
    @Column(name = "card_balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User owner;
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> receivedPayments = new ArrayList<>();
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> sentPayments = new ArrayList<>();
}
