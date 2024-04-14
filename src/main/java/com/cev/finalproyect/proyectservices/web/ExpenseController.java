package com.cev.finalproyect.proyectservices.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cev.finalproyect.proyectservices.domain.Expense;
import com.cev.finalproyect.proyectservices.service.ExpensePersistanceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpensePersistanceService expenseService;

    @Autowired
    public ExpenseController(ExpensePersistanceService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable UUID id) {
        Expense expense = expenseService.getExpense(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable UUID id, @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}