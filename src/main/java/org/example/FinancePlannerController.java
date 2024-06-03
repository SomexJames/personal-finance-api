package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinancePlannerController {

    private final FinancePlanner financePlanner = new FinancePlanner();

    @PostMapping("/setBeginningBalance")
    public ResponseEntity<String> setBeginningBalance(@RequestParam String date, @RequestParam double amount) {
        financePlanner.setBeginningBalance(date, amount);
        return new ResponseEntity<>("Beginning balance set for " + date, HttpStatus.OK);
    }

    @PostMapping("/addIncome")
    public ResponseEntity<String> addIncome(
            @RequestParam String date,
            @RequestParam double amount,
            @RequestParam int recurrencePeriod,
            @RequestParam String name,
            @RequestParam(required = false) String stopDate,
            @RequestParam(required = false) Integer maxOccurrences) {

        LocalDate stopLocalDate = stopDate != null ? LocalDate.parse(stopDate) : null;
        financePlanner.addIncome(date, amount,recurrencePeriod, name, stopLocalDate, maxOccurrences != null ? maxOccurrences : 0);
        return new ResponseEntity<>("Income added on " + date, HttpStatus.OK);
    }

    @PostMapping("/addExpense")
    public ResponseEntity<String> addExpense(
            @RequestParam String date,
            @RequestParam double amount,
            @RequestParam int recurrencePeriod,
            @RequestParam String name,
            @RequestParam(required = false) String stopDate,
            @RequestParam(required = false) Integer maxOccurrences) {

        LocalDate stopLocalDate = stopDate != null ? LocalDate.parse(stopDate) : null;
        financePlanner.addExpense(date, amount, recurrencePeriod, name, stopLocalDate, maxOccurrences != null ? maxOccurrences : 0);
        return new ResponseEntity<>("Expense added on " + date, HttpStatus.OK);
    }

    @GetMapping("/endingBalance")
    public ResponseEntity<Double> getEndingBalance(@RequestParam String date) {
        double balance = financePlanner.getEndingBalance(date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/allTransactions")
    public ResponseEntity<Map<String, DailyTransaction>> getAllTransactions() {
        return new ResponseEntity<>(financePlanner.getAllTransactions(), HttpStatus.OK);
    }
}
