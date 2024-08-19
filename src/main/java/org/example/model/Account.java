package org.example.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.example.enumeration.AccountType;
import org.example.service.AccountService;

public class Account {
    private String id;
    private AccountType accountType;
    private Map<LocalDate, DailyTransaction> dailyTransactions;

    public Account() {
        this.id = UUID.randomUUID().toString();
        this.dailyTransactions = new HashMap<>();
    }

    public Account(AccountType accountType) {
        this.id = UUID.randomUUID().toString();
        this.accountType = accountType;
        this.dailyTransactions = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Map<LocalDate, DailyTransaction> getDailyTransactions() {
        return dailyTransactions;
    }

    public void setDailyTransactions(Map<LocalDate, DailyTransaction> dailyTransactions) {
        this.dailyTransactions = dailyTransactions;
    }

    public void addDailyTransaction(LocalDate date, DailyTransaction dailyTransaction) {
        this.dailyTransactions.put(date, dailyTransaction);
    }

    public DailyTransaction getDailyTransactionByDate(LocalDate date) {
        return dailyTransactions.get(date);
    }

    public void setDailyTransaction(LocalDate date, DailyTransaction dailyTransaction) {
        this.dailyTransactions.put(date, dailyTransaction);
    }
}
