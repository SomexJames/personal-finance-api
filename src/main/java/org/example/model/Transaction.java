package org.example.model;

import org.example.enumeration.TransactionType;
import org.example.service.TransactionService;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private String id;
    private String name;
    private double amount;
    private LocalDate startDate;
    private int recurrencePeriod; // in days
    private LocalDate stopDate;
    private int maxOccurrences;
    private TransactionType type;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(String name, double amount, LocalDate startDate, int recurrencePeriod, LocalDate stopDate, int maxOccurrences, TransactionType type) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.recurrencePeriod = recurrencePeriod;
        this.stopDate = stopDate;
        this.maxOccurrences = maxOccurrences;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public int getMaxOccurrences() {
        return maxOccurrences;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
