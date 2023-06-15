package com.anikanov.controller;

import com.anikanov.domain.CurrencyEnum;
import com.anikanov.domain.dto.CardDto;
import com.anikanov.service.CardService;
import com.anikanov.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cards")
public class CardController {
    private final CardService cardService;
    private final PermissionService permissionService;

    @PostMapping("/new")
    public ResponseEntity<?> newCard(@RequestBody CurrencyEnum currency, Principal auth) {
        return new ResponseEntity<>(CardDto.convert(cardService.newCard(currency, auth.getName())), HttpStatus.OK);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getData(@PathVariable Long cardId, Principal auth) {
        if (permissionService.hasAccess(cardId, auth.getName())) {
            return new ResponseEntity<>(CardDto.convert(cardService.getData(cardId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{cardId}/doubleConversionSwitch")
    public ResponseEntity<?> changeDoubleConversionOption(@PathVariable Long cardId, Principal auth) {
        if (permissionService.hasAccess(cardId, auth.getName())) {
            return new ResponseEntity<>(CardDto.convert(cardService.switchDoubleConversion(cardId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{cardId}/switchAccessibility")
    public ResponseEntity<?> switchAccessibility(@PathVariable Long cardId, Principal auth) {
        if (permissionService.isAdmin(auth.getName())) {
            return new ResponseEntity<>(CardDto.convert(cardService.switchAccessibility(cardId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long cardId, Principal auth) {
        if (permissionService.isAdmin(auth.getName())) {
            cardService.delete(cardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
