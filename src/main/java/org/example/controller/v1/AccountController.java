package org.example.controller.v1;

import org.example.enumeration.TransactionType;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<Transaction> addTransaction(@PathVariable String accountId, @RequestBody Transaction transaction) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.addTransaction(account, transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String accountId, @RequestParam("date") LocalDate date) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        double balance = accountService.getBalance(account, date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/{accountId}/balance/last-known")
    public ResponseEntity<Double> getLastKnownBalance(@PathVariable String accountId, @RequestParam("date") LocalDate date) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        double balance = accountService.getLastKnownBalance(account, date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable String accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Transaction> transactions = accountService.getAllTransactions(account);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}/transactions/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String accountId, @PathVariable String transactionId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteTransaction(account, transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
