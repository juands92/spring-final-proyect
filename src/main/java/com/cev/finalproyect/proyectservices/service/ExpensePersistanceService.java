package com.cev.finalproyect.proyectservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Expense;
import com.cev.finalproyect.proyectservices.repository.ExpenseRespository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpensePersistanceService {

    private final ExpenseRespository expenseRepository;

    @Autowired
    public ExpensePersistanceService(ExpenseRespository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense getExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with ID: " + id));
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }


    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense expense = getExpense(id); 
        expense.setDescription(updatedExpense.getDescription());
        expense.setDate(updatedExpense.getDate());
        expense.setAmount(updatedExpense.getAmount());
        return expenseRepository.save(expense);
    }


    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found with ID: " + id);
        }
        expenseRepository.deleteById(id);
    }
    
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}