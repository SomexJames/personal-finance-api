package org.example;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class FinancePlanner {
    private final Map<String, DailyTransaction> transactionsByDate;

    public FinancePlanner() {
        this.transactionsByDate = new TreeMap<>();
    }

    public void addIncome(String date, double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences) {
        DailyTransaction dailyTransaction = transactionsByDate.computeIfAbsent(date, _ -> new DailyTransaction(date));
        dailyTransaction.addIncome(amount, recurrencePeriod, name, stopDate, maxOccurrences);
        updateCarryoverBalance(LocalDate.parse(date), recurrencePeriod);
        if (recurrencePeriod != 0) {
            processRecurringTransactions(date, amount, recurrencePeriod, name, stopDate, maxOccurrences, "income");
        }
    }

    public void addExpense(String date, double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences) {
        DailyTransaction dailyTransaction = transactionsByDate.computeIfAbsent(date, _ -> new DailyTransaction(date));
        dailyTransaction.addExpense(amount, recurrencePeriod, name, stopDate, maxOccurrences);
        updateCarryoverBalance(LocalDate.parse(date), recurrencePeriod);
        if (recurrencePeriod != 0) {
            processRecurringTransactions(date, amount, recurrencePeriod, name, stopDate, maxOccurrences, "expense");
        }
    }

    public void setBeginningBalance(String date, double amount) {
        DailyTransaction dailyTransaction = transactionsByDate.computeIfAbsent(date, _ -> new DailyTransaction(date));
        dailyTransaction.setBeginningBalance(amount);
    }

    public double getEndingBalance(String date) {
        DailyTransaction dailyTransaction = transactionsByDate.get(date);
        return dailyTransaction != null ? dailyTransaction.getEndingBalance() : 0.0;
    }

    public Map<String, DailyTransaction> getAllTransactions() {
        return transactionsByDate;
    }

    private void updateCarryoverBalance(LocalDate date, int plusDays) {
        if (plusDays > 0) {
            LocalDate nextDate = date.plusDays(plusDays);
            DailyTransaction currentDay = transactionsByDate.get(date.toString());

            if (currentDay != null && nextDate != null) {
                DailyTransaction nextDay = transactionsByDate.computeIfAbsent(nextDate.toString(), _ -> new DailyTransaction(nextDate.toString()));
                nextDay.setBeginningBalance(currentDay.getEndingBalance());
            }
        }
    }

    private void processRecurringTransactions(String dateStr, double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences, String type) {
        LocalDate nextDate = LocalDate.parse(dateStr);
        int occurrences = 0;

        while (true) {
            nextDate = nextDate.plusDays(recurrencePeriod);
            occurrences++;

            // Stop conditions
            if ((stopDate != null && !nextDate.isBefore(stopDate.plusDays(1))) ||
                    (maxOccurrences > 0 && occurrences >= maxOccurrences)) {
                break;
            }

            String nextDateStr = nextDate.toString();
            DailyTransaction nextDayTransaction = transactionsByDate.computeIfAbsent(nextDateStr, _ -> new DailyTransaction(nextDateStr));
            if (type.equals("income")) {
                nextDayTransaction.addIncome(amount, recurrencePeriod, name, stopDate, maxOccurrences);
            } else if (type.equals("expense")) {
                nextDayTransaction.addExpense(amount, recurrencePeriod, name, stopDate, maxOccurrences);
            }
            updateCarryoverBalance(nextDate, recurrencePeriod);
        }
    }
}

