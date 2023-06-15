package com.anikanov.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExecutePaymentDto {
    private BigDecimal amount;
    private boolean payFee;
    private String source;
    private String destination;
}
