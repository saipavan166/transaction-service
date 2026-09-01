package com.example.transactionstarter.transaction.exception;

import com.example.transactionstarter.transaction.entity.TransactionStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(TransactionStatus current, TransactionStatus requested) {
        super("Cannot change status from " + current + " to " + requested);
    }
}
