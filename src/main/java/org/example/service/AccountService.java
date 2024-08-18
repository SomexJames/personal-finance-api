package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.Account;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class AccountService {
    private final TransactionService transactionService;

    public AccountService() {
        this.transactionService = new TransactionService();
    }

    public void addTransaction(Account account, Transaction transaction) {
        Map<LocalDate, DailyTransaction> dailyTransactions = account.getDailyTransactions();
        transactionService.addTransaction(dailyTransactions, transaction);
    }

    public double getBalance(Account account, LocalDate date) {
        DailyTransaction dailyTransaction = account.getDailyTransactionByDate(date);
        if (dailyTransaction == null) {
            return getLastKnownBalance(account, date); // No transactions on this date
        }

        double totalIncome = getAmount(dailyTransaction, TransactionType.INCOME);
        double totalExpenses = getAmount(dailyTransaction, TransactionType.EXPENSE);

        return totalIncome - totalExpenses;
    }

    private double getLastKnownBalance(Account account, LocalDate date) {
        Map<LocalDate, DailyTransaction> dailyTransactions = account.getDailyTransactions();

        // Find the most recent transaction before the given date
        Optional<LocalDate> lastKnownDate = dailyTransactions.keySet().stream()
                .filter(d -> d.isBefore(date))
                .max(LocalDate::compareTo);

        // Return the ending balance of the most recent transaction, or 0 if no previous transactions exist
        return lastKnownDate.map(localDate -> dailyTransactions.get(localDate).getEndingBalance()).orElse(0.0);
    }

    private double getAmount(DailyTransaction dailyTransaction, TransactionType type) {
        return dailyTransaction.getTransactions().stream()
                .filter(transaction -> transaction.getType() == type)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private void processRecurringTransactions(Account account, Transaction transaction) {
        transactionService.processRecurringTransactions(account.getDailyTransactions(), transaction);
    }
}
