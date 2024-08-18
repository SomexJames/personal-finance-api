package org.example.model;

import org.example.enumeration.TransactionType;

import java.time.LocalDate;

public class Transaction {
    private String name;
    private double amount;
    private LocalDate startDate;
    private int recurrencePeriod; // in days
    private LocalDate stopDate;
    private int maxOccurrences;
    private TransactionType type;

    public Transaction(String name, double amount, LocalDate startDate, int recurrencePeriod, LocalDate stopDate, int maxOccurrences, TransactionType type) {
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.recurrencePeriod = recurrencePeriod;
        this.stopDate = stopDate;
        this.maxOccurrences = maxOccurrences;
        this.type = type;
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
}
