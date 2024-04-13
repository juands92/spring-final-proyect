package com.cev.finalproyect.proyectservices.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cev.finalproyect.proyectservices.domain.Expense;
import com.cev.finalproyect.proyectservices.service.ExpensePersistanceService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpensePersistanceService expenseService;

    
    public ExpenseController(ExpensePersistanceService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpense(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}