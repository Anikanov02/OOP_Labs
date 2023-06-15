package com.anikanov.domain.dto;

import com.anikanov.domain.converter.DateConverter;
import com.anikanov.domain.model.Card;
import com.anikanov.domain.model.Payment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {
    private BigDecimal amount;
    private BigDecimal fee;
    private CardDto feePayer;
    private LocalDateTime time;
    private CardDto source;
    private CardDto destination;

    public static PaymentDto convert(Payment payment) {
       return PaymentDto.builder()
               .amount(payment.getAmount())
               .fee(payment.getFee())
               .feePayer(CardDto.convert(payment.getFeePayer()))
               .time(payment.getTime())
               .source(CardDto.convert(payment.getSource()))
               .destination(CardDto.convert(payment.getDestination()))
               .build();
    }
}
