package com.anikanov.repository;

import com.anikanov.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
}
