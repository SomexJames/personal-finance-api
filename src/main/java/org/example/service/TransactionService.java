package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;

import java.time.LocalDate;
import java.util.Map;

public class TransactionService {
    private Transaction transaction;
    private String name;
    private double amount;
    private LocalDate startDate;
    private int recurrencePeriod; // in days
    private LocalDate stopDate;
    private int maxOccurrences;
    private TransactionType type;

    public Map<LocalDate, DailyTransaction> addTransaction(Map<LocalDate, DailyTransaction> dailyTransactions, Transaction transaction) {
        name = transaction.getName();
        amount = transaction.getAmount();
        startDate = transaction.getStartDate();
        recurrencePeriod = transaction.getRecurrencePeriod();
        stopDate = transaction.getStopDate();
        maxOccurrences = transaction.getMaxOccurrences();
        type = transaction.getType();

        if (recurrencePeriod > 0) {
            dailyTransactions = processRecurringTransactions(dailyTransactions, name, amount, startDate, recurrencePeriod, stopDate, maxOccurrences, type);
        } else {
            // get or create DailyTransaction at startDate
                // add transaction to the DailyTransaction's transactions list
        }

        return dailyTransactions;
    }

    public Map<LocalDate, DailyTransaction> removeTransaction(Map<LocalDate, DailyTransaction> dailyTransactions, Transaction transaction) {
        // remove all instances of transaction object in dailyTransactions
        // return updated dailyTransactions
    }

    private Map<LocalDate, DailyTransaction> processRecurringTransactions(Map<LocalDate, DailyTransaction> dailyTransactions, String name, double amount, LocalDate startDate, int recurrencePeriod, LocalDate stopDate, int maxOccurrences, TransactionType type) {
        // for all recurring dates until the stop condition (stopDate or maxOccurrences),
            // if the date is not found in dailyTransactions, instantiate a LocalDate, DailyTransaction pair in dailyTransactions
        // add the transaction item to each DailyTransaction's transactions list
        // return updated dailyTransactions
    }
}
