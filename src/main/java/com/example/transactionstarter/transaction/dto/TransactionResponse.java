package com.example.transactionstarter.transaction.dto;

import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.entity.TransactionStatus;
import com.example.transactionstarter.transaction.entity.TransactionType;

import java.math.BigDecimal;

public record TransactionResponse(
        String transactionId,
        String customerId,
        BigDecimal amount,
        String currency,
        TransactionType transactionType,
        TransactionStatus status) {

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getTransactionType(),
                transaction.getStatus());
    }
}
