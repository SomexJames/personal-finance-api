package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinancePlannerController.class)
class FinancePlannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Any additional setup if needed
    }

    @Test
    void testSetBeginningBalance() throws Exception {
        mockMvc.perform(post("/api/finance/setBeginningBalance")
                        .param("date", "2024-08-16")
                        .param("amount", "1000.00"))
                .andExpect(status().isOk())
                .andExpect(content().string("Beginning balance set for 2024-08-16"));
    }

    @Test
    void testAddIncome() throws Exception {
        mockMvc.perform(post("/api/finance/setBeginningBalance")
                        .param("date", "2024-08-16")
                        .param("amount", "1000.00"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/finance/addIncome")
                        .param("date", "2024-08-16")
                        .param("amount", "500.00")
                        .param("recurring", "false")
                        .param("recurrencePeriod", "0")
                        .param("name", "Initial Income"))
                .andExpect(status().isOk())
                .andExpect(content().string("Income added on 2024-08-16"));
    }

    @Test
    void testGetEndingBalance() throws Exception {
        mockMvc.perform(post("/api/finance/setBeginningBalance")
                        .param("date", "2024-08-16")
                        .param("amount", "1000.00"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/finance/endingBalance")
                        .param("date", "2024-08-16"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    }

    @Test
    void testAddRecurringExpense() throws Exception {
        mockMvc.perform(post("/api/finance/setBeginningBalance")
                        .param("date", "2024-08-16")
                        .param("amount", "1000.00"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/finance/addExpense")
                        .param("date", "2024-08-16")
                        .param("amount", "100.00")
                        .param("recurring", "true")
                        .param("recurrencePeriod", "1")
                        .param("name", "Daily Expense")
                        .param("maxOccurrences", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense added on 2024-08-16"));
    }
}
