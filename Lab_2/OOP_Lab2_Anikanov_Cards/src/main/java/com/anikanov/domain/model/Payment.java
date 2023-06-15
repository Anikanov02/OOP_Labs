package com.anikanov.domain.model;

import com.anikanov.domain.converter.DateConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "fee", nullable = false)
    private BigDecimal fee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_payer", referencedColumnName = "card_id")
    private Card feePayer;
    @Column(name = "time", nullable = false)
    @Convert(converter = DateConverter.class)
    private LocalDateTime time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_card_id", referencedColumnName = "card_id")
    private Card source;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_card_id", referencedColumnName = "card_id")
    private Card destination;
}
