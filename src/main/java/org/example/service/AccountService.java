package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.Account;
import org.example.model.DailyTransaction;
import org.example.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {

    private Map<String, Account> accountRepository;
    private TransactionService transactionService;

    public AccountService(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.accountRepository = new HashMap<>();
    }

    public Account getAccountById(String accountId) {
        return accountRepository.get(accountId);
    }

    public void addTransaction(Account account, Transaction transaction) {
        transactionService.addTransaction(account.getDailyTransactions(), transaction);
    }

    public double getBalance(Account account, LocalDate date) {
        DailyTransaction dailyTransaction = account.getDailyTransactionByDate(date);
        if (dailyTransaction == null) {
            return getLastKnownBalance(account, date);
        }

        double totalIncome = getAmount(dailyTransaction, TransactionType.INCOME);
        double totalExpenses = getAmount(dailyTransaction, TransactionType.EXPENSE);
        double balance = totalIncome - totalExpenses;

        if (dailyTransaction.getEndingBalance() != balance) {
            dailyTransaction.setEndingBalance(balance);
            account.setDailyTransaction(date, dailyTransaction);
        }

        return balance;
    }

    private double getAmount(DailyTransaction dailyTransaction, TransactionType type) {
        return dailyTransaction.getTransactions().stream()
                .filter(transaction -> transaction.getType() == type)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getLastKnownBalance(Account account, LocalDate date) {
        Map<LocalDate, DailyTransaction> dailyTransactions = account.getDailyTransactions();

        Optional<LocalDate> lastKnownDate = dailyTransactions.keySet().stream()
                .filter(d -> d.isBefore(date))
                .max(LocalDate::compareTo);

        return lastKnownDate.map(localDate -> dailyTransactions.get(localDate).getEndingBalance()).orElse(0.0);
    }

    public List<Transaction> getAllTransactions(Account account) {
        return account.getDailyTransactions().values().stream()
                .flatMap(dt -> dt.getTransactions().stream())
                .collect(Collectors.toList());
    }

    public void deleteTransaction(Account account, String transactionId) {
        Map<LocalDate, DailyTransaction> dailyTransactions = account.getDailyTransactions();
        dailyTransactions.values().forEach(dailyTransaction ->
                dailyTransaction.getTransactions().removeIf(t -> t.getId().equals(transactionId))
        );
    }
}
