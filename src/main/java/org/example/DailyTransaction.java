package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyTransaction {
    private final String date;
    private double beginningBalance;
    private final List<Income> incomes;
    private final List<Expense> expenses;

    public DailyTransaction(String date) {
        this.date = date;
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.beginningBalance = 0.0;
    }

    public void addIncome(double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences) {
        incomes.add(new Income(amount, recurrencePeriod, name, stopDate, maxOccurrences));
    }

    public void addExpense(double amount, int recurrencePeriod, String name, LocalDate stopDate, int maxOccurrences) {
        expenses.add(new Expense(amount, recurrencePeriod, name, stopDate, maxOccurrences));
    }

    public void setBeginningBalance(double beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    public double getEndingBalance() {
        double totalIncomes = incomes.stream().mapToDouble(Income::getAmount).sum();
        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
        return beginningBalance + totalIncomes - totalExpenses;
    }

    public String getDate() {
        return date;
    }

    public double getBeginningBalance() {
        return beginningBalance;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Transaction> getRecurringTransactions() {
        List<Transaction> recurringTransactions = new ArrayList<>();
        incomes.stream().filter(i -> i.getRecurrencePeriod() > 0).forEach(recurringTransactions::add);
        expenses.stream().filter(e -> e.getRecurrencePeriod() > 0).forEach(recurringTransactions::add);
        return recurringTransactions;
    }
}
