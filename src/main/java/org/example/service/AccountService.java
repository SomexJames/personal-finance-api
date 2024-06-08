package org.example.service;

import org.example.enumeration.AccountType;
import org.example.model.Account;
import org.example.model.DailyTransaction;
import org.example.enumeration.TransactionType;
import org.example.model.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountService {
    private Account account;
    private TransactionService transactionService;
    private Map<LocalDate, DailyTransaction> dailyTransactions;

    public void addTransaction(Account account, Transaction transaction) {
        dailyTransactions = account.getDailyTransactions();
        transactionService.addTransaction(dailyTransactions, transaction);
    }

    private void addDailyTransaction(LocalDate date, DailyTransaction dailyTransaction) {
        dailyTransactions.put(date, dailyTransaction);
    }

    public double getBalance(Account account, String date) {
        // find DailyTransaction by date
        // get total income amounts
        // get total expense amounts
        // return income - expense
    }

    public double getAmount(Account account, DailyTransaction dailyTransaction, TransactionType type) {
        // find DailyTransaction
        // sum all amounts in type
        // return sum
    }

    private void processRecurringTransactions(String dateStr, double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences, TransactionType type) {
        // for every recurring date, if there's a DailyTransaction object with the assoc. account, addIncome/addExpense
            // if not, create new DailyTransaction for that date
    }
}
