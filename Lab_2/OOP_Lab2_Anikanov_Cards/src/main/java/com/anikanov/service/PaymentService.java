package com.anikanov.service;

import com.anikanov.config.GlobalConstants;
import com.anikanov.domain.dto.ExecutePaymentDto;
import com.anikanov.domain.model.Card;
import com.anikanov.domain.model.Payment;
import com.anikanov.domain.model.User;
import com.anikanov.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final CardService cardService;

    public Payment newPayment(ExecutePaymentDto dto) throws Exception {
        final Card source = cardService.getData(dto.getSource());
        final Card destination = cardService.getData(dto.getDestination());

        final BigDecimal fee = getFee(dto.getAmount());
        final BigDecimal totalSpentAmount = dto.isPayFee() ? dto.getAmount().add(fee) : dto.getAmount();
        final BigDecimal totalReceivedAmount = dto.isPayFee() ? dto.getAmount() : dto.getAmount().subtract(fee);
        if (source.getBalance().compareTo(totalSpentAmount) < 0) {
            throw new Exception(String.format("Balance insufficient: available = %s, requested = %s",
                    source.getBalance().add(source.getCreditLimit()).toPlainString(),
                    totalSpentAmount.toPlainString()));
        }

        source.setBalance(source.getBalance().subtract(totalSpentAmount));
        destination.setBalance(destination.getBalance().add(totalReceivedAmount));

        final Payment payment = Payment.builder()
                .fee(fee)
                .time(LocalDateTime.now())
                .source(source)
                .destination(destination)
                .feePayer(dto.isPayFee() ? source : destination)
                .amount(dto.getAmount())
                .build();

        source.getSentPayments().add(payment);
        destination.getReceivedPayments().add(payment);
        cardService.saveOrUpdate(source);
        cardService.saveOrUpdate(destination);
        return paymentRepository.save(payment);
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    public List<Payment> getAllPayments(Long cardId) {
        final Card card = cardService.getData(cardId);
        if (Objects.nonNull(card)) {
            final ArrayList<Payment> allPayments = new ArrayList<>();
            allPayments.addAll(card.getSentPayments());
            allPayments.addAll(card.getReceivedPayments());
            return allPayments.stream().sorted(Comparator.comparing(Payment::getTime)).collect(Collectors.toList());
        }
        return null;
    }

    public BigDecimal getFee(BigDecimal amount) {
        return amount.divide(new BigDecimal(100), GlobalConstants.MATH_CONTEXT);
    }
}
