package com.example.transactionstarter.transaction.controller;

import com.example.transactionstarter.transaction.dto.CreateTransaction;
import com.example.transactionstarter.transaction.dto.TransactionResponse;
import com.example.transactionstarter.transaction.dto.UpdateTransactionStatus;
import com.example.transactionstarter.transaction.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    private static final String ID_PATTERN = "t\\d{11}";
    private static final String CUSTOMER_PATTERN = "c\\d{11}";

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransaction request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse get(
            @PathVariable @Pattern(regexp = ID_PATTERN, message = "Invalid transaction ID") String transactionId) {
        return service.get(transactionId);
    }

    @PatchMapping("/{transactionId}/status")
    public TransactionResponse updateStatus(
            @PathVariable @Pattern(regexp = ID_PATTERN, message = "Invalid transaction ID") String transactionId,
            @Valid @RequestBody UpdateTransactionStatus request) {
        return service.updateStatus(transactionId, request.getStatus());
    }

    @GetMapping
    public List<TransactionResponse> getCustomerTransactions(
            @RequestParam @Pattern(regexp = CUSTOMER_PATTERN, message = "Invalid customer ID") String customerId) {
        return service.getByCustomer(customerId);
    }
}
