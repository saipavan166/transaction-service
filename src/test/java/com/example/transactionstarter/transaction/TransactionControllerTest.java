package com.example.transactionstarter.transaction;

import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TransactionRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    private String validJson(String id) {
        return """
                {
                  "transactionId":"%s",
                  "customerId":"c12345678901",
                  "amount":125.50,
                  "currency":"USD",
                  "transactionType":"PAYMENT",
                  "status":"PENDING"
                }
                """.formatted(id);
    }

    @Test
    void createsTransactionSuccessfully() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson("t12345678901")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("t12345678901"))
                .andExpect(jsonPath("$.customerId").value("c12345678901"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void rejectsInvalidTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "transactionId":"bad",
                                  "customerId":"c12345678901",
                                  "amount":-5,
                                  "currency":"usd",
                                  "transactionType":"PAYMENT",
                                  "status":"PENDING"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors").exists());
    }

    @Test
    void rejectsDuplicateTransactionId() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson("t12345678901")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson("t12345678901")))
                .andExpect(status().isConflict());
    }

    @Test
    void returnsNotFoundForMissingTransaction() throws Exception {
        mockMvc.perform(get("/api/transactions/t12345678901"))
                .andExpect(status().isNotFound());
    }
}
