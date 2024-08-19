package org.example.model;

import org.example.model.Transaction;
import org.example.enumeration.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyTransaction {
    private LocalDate date;
    private double startingBalance;
    private List<Transaction> transactions;
    private double endingBalance;

    public DailyTransaction() {
        this.transactions = new ArrayList<>();
    }

    public DailyTransaction(LocalDate date, double startingBalance, double endingBalance) {
        this.date = date;
        this.startingBalance = startingBalance;
        this.transactions = new ArrayList<>();
        this.endingBalance = endingBalance;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(double endingBalance) {
        this.endingBalance = endingBalance;
    }
}
