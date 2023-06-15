package com.anikanov.controller;

import com.anikanov.domain.dto.ExecutePaymentDto;
import com.anikanov.domain.dto.PaymentDto;
import com.anikanov.domain.model.Payment;
import com.anikanov.service.PaymentService;
import com.anikanov.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final PermissionService permissionService;

    @PostMapping("/new")
    public ResponseEntity<?> execute(@RequestBody @Valid ExecutePaymentDto paymentDto, Principal auth) {
        try {
            return new ResponseEntity<>(PaymentDto.convert(paymentService.newPayment(paymentDto)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentData(@PathVariable Long paymentId, Principal auth) {
        if (permissionService.paymentParticipant(paymentId, auth.getName()) || permissionService.isAdmin(auth.getName())) {
            return new ResponseEntity<>(PaymentDto.convert(paymentService.getPayment(paymentId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/all/{cardId}")
    public ResponseEntity<?> getAllPayments(@PathVariable Long cardId, Principal auth) {
        if (permissionService.hasAccess(cardId, auth.getName())) {
            return new ResponseEntity<>(paymentService.getAllPayments(cardId).stream().map(PaymentDto::convert).collect(Collectors.toSet()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
