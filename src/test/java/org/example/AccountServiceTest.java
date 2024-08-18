package org.example;

import org.example.service.TransactionService;
import org.example.service.AccountService;
import org.example.enumeration.AccountType;
import org.example.enumeration.TransactionType;
import org.example.model.Account;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account(AccountType.CHECKING);
    }

    @Test
    void addTransaction_shouldInvokeTransactionService() {
        LocalDate date = LocalDate.of(2024, 8, 16);
        Transaction transaction = new Transaction("Salary", 1000.0, date, 0, null, 0, TransactionType.INCOME);
        accountService.addTransaction(account, transaction);
        verify(transactionService, times(1)).addTransaction(account.getDailyTransactions(), transaction);
    }

    @Test
    void getBalance_shouldReturnCorrectBalanceForDate() {
        LocalDate date = LocalDate.of(2024, 8, 16);
        DailyTransaction dailyTransaction = new DailyTransaction(date, 0.0, 0.0);
        dailyTransaction.getTransactions().add(new Transaction("Salary", 1000.0, date, 0, null, 0, TransactionType.INCOME));
        dailyTransaction.getTransactions().add(new Transaction("Rent", 500.0, date, 0, null, 0, TransactionType.EXPENSE));
        account.addDailyTransaction(date, dailyTransaction);

        double balance = accountService.getBalance(account, date);
        assertEquals(500.0, balance);
    }

    @Test
    void getBalance_shouldReturnLastKnownBalanceIfNoTransactionOnDate() {
        LocalDate date1 = LocalDate.of(2024, 8, 15);
        LocalDate date2 = LocalDate.of(2024, 8, 16);

        DailyTransaction dailyTransaction = new DailyTransaction(date1, 0.0, 500.0);
        dailyTransaction.getTransactions().add(new Transaction("Salary", 1000.0, date1, 0, null, 0, TransactionType.INCOME));
        dailyTransaction.getTransactions().add(new Transaction("Rent", 500.0, date1, 0, null, 0, TransactionType.EXPENSE));
        account.addDailyTransaction(date1, dailyTransaction);

        double balance = accountService.getBalance(account, date2);
        assertEquals(500.0, balance);
    }

    @Test
    void getBalance_shouldReturnZeroIfNoPreviousTransactionExists() {
        LocalDate date = LocalDate.of(2024, 8, 16);

        double balance = accountService.getBalance(account, date);
        assertEquals(0.0, balance);
    }
}
