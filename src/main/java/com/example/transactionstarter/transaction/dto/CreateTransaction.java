package com.example.transactionstarter.transaction.dto;

import com.example.transactionstarter.transaction.entity.TransactionStatus;
import com.example.transactionstarter.transaction.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateTransaction {

    @NotBlank(message = "Transaction ID is required")
    @Pattern(regexp = "t\\d{11}", message = "Transaction ID must match t followed by 11 digits")
    private String transactionId;

    @NotBlank(message = "Customer ID is required")
    @Pattern(regexp = "c\\d{11}", message = "Customer ID must match c followed by 11 digits")
    private String customerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    @Pattern(regexp = "[A-Z]{3}", message = "Currency must contain three uppercase letters")
    private String currency;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Initial status is required")
    private TransactionStatus status;

    public String getTransactionId() { return transactionId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public TransactionType getTransactionType() { return transactionType; }
    public TransactionStatus getStatus() { return status; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    public void setStatus(TransactionStatus status) { this.status = status; }
}
