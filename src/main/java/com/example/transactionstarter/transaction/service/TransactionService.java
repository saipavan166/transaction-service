package com.example.transactionstarter.transaction.service;

import com.example.transactionstarter.transaction.dto.CreateTransaction;
import com.example.transactionstarter.transaction.dto.TransactionResponse;
import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.entity.TransactionStatus;
import com.example.transactionstarter.transaction.exception.DuplicateTransactionException;
import com.example.transactionstarter.transaction.exception.InvalidStatusTransitionException;
import com.example.transactionstarter.transaction.exception.TransactionNotFoundException;
import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TransactionResponse create(CreateTransaction request) {
        if (repository.existsById(request.getTransactionId())) {
            throw new DuplicateTransactionException(request.getTransactionId());
        }
        if (request.getStatus() != TransactionStatus.PENDING) {
            throw new IllegalArgumentException("New transactions must start with PENDING status");
        }

        Transaction transaction = new Transaction(
                request.getTransactionId(),
                request.getCustomerId(),
                request.getAmount(),
                request.getCurrency(),
                request.getTransactionType(),
                request.getStatus());

        return TransactionResponse.from(repository.save(transaction));
    }

    @Transactional(readOnly = true)
    public TransactionResponse get(String transactionId) {
        return repository.findById(transactionId)
                .map(TransactionResponse::from)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }

    @Transactional
    public TransactionResponse updateStatus(String transactionId, TransactionStatus requested) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        TransactionStatus current = transaction.getStatus();
        if (!isAllowed(current, requested)) {
            throw new InvalidStatusTransitionException(current, requested);
        }

        transaction.setStatus(requested);
        return TransactionResponse.from(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getByCustomer(String customerId) {
        return repository.findByCustomerIdOrderByTransactionIdAsc(customerId)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }

    private boolean isAllowed(TransactionStatus current, TransactionStatus requested) {
        if (current == requested) {
            return true;
        }
        return current == TransactionStatus.PENDING
                && (requested == TransactionStatus.COMPLETED
                    || requested == TransactionStatus.FAILED
                    || requested == TransactionStatus.CANCELLED);
    }
}
