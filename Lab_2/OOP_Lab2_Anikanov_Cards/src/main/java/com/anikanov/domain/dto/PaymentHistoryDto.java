package com.anikanov.domain.dto;

import com.anikanov.domain.PaymentSide;
import com.anikanov.domain.model.Card;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentHistoryDto {
    private PaymentSide side;
    private BigDecimal balanceChange;
    private Card interactor;
}
