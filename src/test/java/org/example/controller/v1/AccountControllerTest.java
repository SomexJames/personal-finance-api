package org.example.controller.v1;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private Account account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        transaction = new Transaction("Salary", 1000.00, LocalDate.now(), 0, null, 0, null);
    }

    @Test
    void addTransaction_shouldReturnCreatedTransaction() {
        String accountId = account.getId();
        when(accountService.getAccountById(accountId)).thenReturn(account);

        ResponseEntity<Transaction> response = accountController.addTransaction(accountId, transaction);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
        verify(accountService, times(1)).addTransaction(account, transaction);
    }

    @Test
    void addTransaction_shouldReturnNotFoundIfAccountDoesNotExist() {
        String accountId = "non-existent-id";
        when(accountService.getAccountById(accountId)).thenReturn(null);

        ResponseEntity<Transaction> response = accountController.addTransaction(accountId, transaction);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(accountService, times(0)).addTransaction(any(Account.class), any(Transaction.class));
    }

    @Test
    void getBalance_shouldReturnCorrectBalance() {
        String accountId = account.getId();
        LocalDate date = LocalDate.of(2024, 8, 16);
        double expectedBalance = 500.00;
        when(accountService.getAccountById(accountId)).thenReturn(account);
        when(accountService.getBalance(account, date)).thenReturn(expectedBalance);

        ResponseEntity<Double> response = accountController.getBalance(accountId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());
        verify(accountService, times(1)).getBalance(account, date);
    }

    @Test
    void getBalance_shouldReturnNotFoundIfAccountDoesNotExist() {
        String accountId = "non-existent-id";
        LocalDate date = LocalDate.of(2024, 8, 16);
        when(accountService.getAccountById(accountId)).thenReturn(null);

        ResponseEntity<Double> response = accountController.getBalance(accountId, date);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(accountService, times(0)).getBalance(any(Account.class), any(LocalDate.class));
    }

    @Test
    void getAllTransactions_shouldReturnAllTransactions() {
        String accountId = account.getId();
        List<Transaction> transactions = Arrays.asList(transaction);
        when(accountService.getAccountById(accountId)).thenReturn(account);
        when(accountService.getAllTransactions(account)).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = accountController.getAllTransactions(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(accountService, times(1)).getAllTransactions(account);
    }

    @Test
    void deleteTransaction_shouldReturnNoContentIfDeleted() {
        String accountId = account.getId();
        String transactionId = transaction.getId();
        when(accountService.getAccountById(accountId)).thenReturn(account);

        ResponseEntity<Void> response = accountController.deleteTransaction(accountId, transactionId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(accountService, times(1)).deleteTransaction(account, transactionId);
    }

    @Test
    void deleteTransaction_shouldReturnNotFoundIfAccountDoesNotExist() {
        String accountId = "non-existent-id";
        String transactionId = "some-transaction-id";
        when(accountService.getAccountById(accountId)).thenReturn(null);

        ResponseEntity<Void> response = accountController.deleteTransaction(accountId, transactionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(accountService, times(0)).deleteTransaction(any(Account.class), anyString());
    }
}
