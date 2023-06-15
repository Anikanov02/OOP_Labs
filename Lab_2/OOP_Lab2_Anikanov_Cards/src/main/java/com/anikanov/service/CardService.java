package com.anikanov.service;

import com.anikanov.domain.CurrencyEnum;
import com.anikanov.domain.model.Card;
import com.anikanov.domain.model.User;
import com.anikanov.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserService userService;

    public Card saveOrUpdate(Card card) {
        return cardRepository.save(card);
    }

    public Card getData(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    public Card getData(String number) {
        return cardRepository.findByNumber(number);
    }

    public Card newCard(CurrencyEnum currency, String userLogin) {
        final User user = userService.findByLogin(userLogin);
        final Card newCard = Card.builder()
                .balance(BigDecimal.ZERO)
                .creditLimit(BigDecimal.ZERO)
                .currency(currency)
                .doubleConversionEnabled(false)
                .enabled(true)
                .number(generateCardNumber())
                .owner(user)
                .build();
        return cardRepository.save(newCard);
    }

    public Card switchDoubleConversion(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (Objects.nonNull(card)) {
            card.setDoubleConversionEnabled(!card.isDoubleConversionEnabled());
            card = cardRepository.save(card);
        }
        return card;
    }

    public Card switchAccessibility(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (Objects.nonNull(card)) {
            card.setEnabled(!card.isEnabled());
            card = cardRepository.save(card);
        }
        return card;
    }

    public void delete(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    private static String generateCardNumber() {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }
}
