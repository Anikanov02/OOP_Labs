package com.anikanov.domain.dto;

import com.anikanov.domain.CurrencyEnum;
import com.anikanov.domain.model.Card;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CardDto {
    private String number;
    private boolean enabled;
    private boolean doubleConversionEnabled;
    private CurrencyEnum currency;
    private BigDecimal balance;
    private BigDecimal creditLimit;

    public static CardDto convert(Card card) {
        return CardDto.builder()
                .number(card.getNumber())
                .enabled(card.isEnabled())
                .doubleConversionEnabled(card.isDoubleConversionEnabled())
                .currency(card.getCurrency())
                .balance(card.getBalance())
                .creditLimit(card.getCreditLimit())
                .build();
    }
}
