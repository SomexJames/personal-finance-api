package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;

import java.time.LocalDate;
import java.util.Map;

public class TransactionService {

    public Map<LocalDate, DailyTransaction> addTransaction(Map<LocalDate, DailyTransaction> dailyTransactions, Transaction transaction) {
        LocalDate startDate = transaction.getStartDate();
        int recurrencePeriod = transaction.getRecurrencePeriod();

        if (recurrencePeriod > 0) {
            return processRecurringTransactions(dailyTransactions, transaction);
        } else {
            DailyTransaction dailyTransaction = dailyTransactions.getOrDefault(startDate, new DailyTransaction(startDate, 0.0, 0.0));
            dailyTransaction.getTransactions().add(transaction);
            dailyTransactions.put(startDate, dailyTransaction);
        }

        return dailyTransactions;
    }

    public Map<LocalDate, DailyTransaction> removeTransaction(Map<LocalDate, DailyTransaction> dailyTransactions, Transaction transaction) {
        dailyTransactions.values().forEach(dailyTransaction ->
                dailyTransaction.getTransactions().removeIf(t -> t.equals(transaction))
        );
        return dailyTransactions;
    }

    public Map<LocalDate, DailyTransaction> processRecurringTransactions(Map<LocalDate, DailyTransaction> dailyTransactions, Transaction transaction) {
        LocalDate currentDate = transaction.getStartDate();
        int occurrences = 0;
        int recurrencePeriod = transaction.getRecurrencePeriod();
        LocalDate stopDate = transaction.getStopDate();
        int maxOccurrences = transaction.getMaxOccurrences();

        while (shouldContinue(currentDate, stopDate, occurrences, maxOccurrences)) {
            DailyTransaction dailyTransaction = dailyTransactions.getOrDefault(currentDate, new DailyTransaction(currentDate, 0.0, 0.0));
            dailyTransaction.getTransactions().add(transaction);
            dailyTransactions.put(currentDate, dailyTransaction);

            currentDate = currentDate.plusDays(recurrencePeriod);
            occurrences++;
        }

        return dailyTransactions;
    }

    private boolean shouldContinue(LocalDate currentDate, LocalDate stopDate, int occurrences, int maxOccurrences) {
        boolean hasNotReachedStopDate = (stopDate == null) || (currentDate.isBefore(stopDate) || currentDate.equals(stopDate));
        boolean hasNotExceededOccurrences = maxOccurrences <= 0 || occurrences < maxOccurrences;

        return hasNotReachedStopDate && hasNotExceededOccurrences;
    }
}
