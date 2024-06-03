package org.example;

import java.time.LocalDate;

public class Income implements Transaction {
    private final double amount;
    private final int recurrencePeriod; // in days
    private final String name;
    private final LocalDate stopDate;
    private final int maxOccurrences;

    public Income(double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences) {
        this.amount = amount;
        this.recurrencePeriod = recurrencePeriod;
        this.name = name;
        this.stopDate = stopDate;
        this.maxOccurrences = maxOccurrences;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public int getRecurrencePeriod() {
        return recurrencePeriod;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getStopDate() {
        return stopDate;
    }

    @Override
    public int getMaxOccurrences() {
        return maxOccurrences;
    }
}
