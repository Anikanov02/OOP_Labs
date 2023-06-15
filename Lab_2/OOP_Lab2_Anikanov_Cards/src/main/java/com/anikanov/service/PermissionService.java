package com.anikanov.service;

import com.anikanov.domain.Role;
import com.anikanov.domain.model.Payment;
import com.anikanov.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final UserService userService;
    private final PaymentService paymentService;

    public boolean isAuthenticated(Long userId, String authUserLogin) {
        if (userService.findById(userId) == null) {
            return false;
        }
        return userService.findById(userId).equals(userService.findByLogin(authUserLogin));
    }

    public boolean hasAccess(Long cardId, String userLogin) {
        return isAdmin(userLogin) || isOwner(cardId, userLogin);
    }

    public boolean isOwner(Long cardId, String userLogin) {
        final User user = userService.findByLogin(userLogin);
        return user.getCards().stream().anyMatch(card -> card.getId().equals(cardId));
    }

    public boolean isAdmin(String userLogin) {
        final User user = userService.findByLogin(userLogin);
        return user.getRole() == Role.ADMIN;
    }

    public boolean paymentParticipant(Long paymentId, String userLogin) {
        final User user = userService.findByLogin(userLogin);
        if (Objects.nonNull(user)) {
            final List<Payment> payments = user.getCards().stream()
                    .flatMap(card -> Stream.concat(card.getSentPayments().stream(), card.getReceivedPayments().stream()))
                    .toList();
            return payments.stream().anyMatch(payment -> payment.getId().equals(paymentId));
        } else {
            return false;
        }
    }
}
