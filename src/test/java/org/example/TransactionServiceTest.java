package org.example;

import org.example.service.TransactionService;
import org.example.service.AccountService;
import org.example.enumeration.TransactionType;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {

    private TransactionService transactionService;
    private Map<LocalDate, DailyTransaction> dailyTransactions;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
        dailyTransactions = new HashMap<>();
    }

    @Test
    void addTransaction_shouldAddTransactionToCorrectDate() {
        LocalDate date = LocalDate.of(2024, 8, 16);
        Transaction transaction = new Transaction("Salary", 1000.0, date, 0, null, 0, TransactionType.INCOME);

        transactionService.addTransaction(dailyTransactions, transaction);
        assertEquals(1, dailyTransactions.size());
        assertTrue(dailyTransactions.containsKey(date));
        assertEquals(1, dailyTransactions.get(date).getTransactions().size());
        assertEquals(transaction, dailyTransactions.get(date).getTransactions().get(0));
    }

    @Test
    void addTransaction_shouldProcessRecurringTransactions() {
        LocalDate startDate = LocalDate.of(2024, 8, 16);
        LocalDate stopDate = LocalDate.of(2024, 8, 18);
        Transaction transaction = new Transaction("Subscription", 100.0, startDate, 1, stopDate, 0, TransactionType.EXPENSE);

        transactionService.addTransaction(dailyTransactions, transaction);
        assertEquals(3, dailyTransactions.size()); // 3 days: 16th, 17th, and 18th

        for (LocalDate date : dailyTransactions.keySet()) {
            assertEquals(1, dailyTransactions.get(date).getTransactions().size());
            assertEquals(transaction.getName(), dailyTransactions.get(date).getTransactions().get(0).getName());
            assertEquals(transaction.getAmount(), dailyTransactions.get(date).getTransactions().get(0).getAmount());
        }
    }

    @Test
    void removeTransaction_shouldRemoveTransactionFromCorrectDate() {
        LocalDate date = LocalDate.of(2024, 8, 16);
        Transaction transaction = new Transaction("Salary", 1000.0, date, 0, null, 0, TransactionType.INCOME);
        transactionService.addTransaction(dailyTransactions, transaction);

        transactionService.removeTransaction(dailyTransactions, transaction);
        assertEquals(0, dailyTransactions.get(date).getTransactions().size());
    }

    @Test
    void processRecurringTransactions_shouldAddTransactionsForRecurringDates() {
        LocalDate startDate = LocalDate.of(2024, 8, 16);
        LocalDate stopDate = LocalDate.of(2024, 8, 18);
        Transaction transaction = new Transaction("Subscription", 100.0, startDate, 1, stopDate, 0, TransactionType.EXPENSE);

        Map<LocalDate, DailyTransaction> result = transactionService.processRecurringTransactions(dailyTransactions, transaction);
        assertEquals(3, result.size()); // 3 days: 16th, 17th, and 18th

        for (LocalDate date : result.keySet()) {
            assertEquals(1, result.get(date).getTransactions().size());
            assertEquals(transaction.getName(), result.get(date).getTransactions().get(0).getName());
            assertEquals(transaction.getAmount(), result.get(date).getTransactions().get(0).getAmount());
        }
    }
}
