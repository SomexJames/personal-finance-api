package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinancePlannerTest {

    private FinancePlanner financePlanner;

    @BeforeEach
    void setUp() {
        financePlanner = new FinancePlanner();
    }

    @Test
    void testSetBeginningBalance() {
        financePlanner.setBeginningBalance("1998-08-16", 1000.00);
        assertEquals(1000.00, financePlanner.getEndingBalance("1998-08-16"));
    }

    @Test
    void testAddIncome() {
        financePlanner.setBeginningBalance("1999-08-16", 1000.00);
        financePlanner.addIncome("1999-08-16", 500.00, 0, "Initial Income", null, 0);
        assertEquals(1500.00, financePlanner.getEndingBalance("1999-08-16"));
    }

    @Test
    void testAddExpense() {
        financePlanner.setBeginningBalance("2000-08-16", 1000.00);
        financePlanner.addExpense("2000-08-16", 200.00, 0, "Expense", null, 0);
        assertEquals(800.00, financePlanner.getEndingBalance("2000-08-16"));
    }

    @Test
    void testRecurringIncome() {
        financePlanner.setBeginningBalance("2001-08-16", 1000.00);
        financePlanner.addIncome("2001-08-16", 200.00, 7, "Weekly Salary", null, 3);
        assertEquals(1200.00, financePlanner.getEndingBalance("2001-08-16"));
        assertEquals(1400.00, financePlanner.getEndingBalance("2001-08-23"));
        assertEquals(1600.00, financePlanner.getEndingBalance("2001-08-30"));
    }

    @Test
    void testRecurringExpenseWithMaxOccurrences() {
        financePlanner.setBeginningBalance("2002-08-16", 1000.00);
        financePlanner.addExpense("2002-08-16", 100.00, 1, "Daily Expense", null, 5);
        assertEquals(900.00, financePlanner.getEndingBalance("2002-08-16"));
        assertEquals(800.00, financePlanner.getEndingBalance("2002-08-17"));
        assertEquals(700.00, financePlanner.getEndingBalance("2002-08-18"));
        assertEquals(600.00, financePlanner.getEndingBalance("2002-08-19"));
        assertEquals(500.00, financePlanner.getEndingBalance("2002-08-21"));
    }

    @Test
    void testRecurringExpenseWithStopDate() {
        financePlanner.setBeginningBalance("2003-08-16", 1000.00);
        financePlanner.addExpense("2003-08-16", 100.00, 1, "Daily Expense", LocalDate.parse("2003-08-20"), 0);
        assertEquals(900.00, financePlanner.getEndingBalance("2003-08-16"));
        assertEquals(600.00, financePlanner.getEndingBalance("2003-08-19"));
        assertEquals(500.00, financePlanner.getEndingBalance("2003-08-20"));
        assertEquals(500.00, financePlanner.getEndingBalance("2003-08-21"));  // Should not decrease further
    }
}
