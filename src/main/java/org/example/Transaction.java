package org.example;

import java.time.LocalDate;

public interface Transaction {
    double getAmount();
    int getRecurrencePeriod(); // Returns the recurrence period in days (0 means no recurrence)
    String getName();
    LocalDate getStopDate(); // Stop recurrence after this date (optional)
    int getMaxOccurrences(); // Stop recurrence after this many occurrences (optional)
}
