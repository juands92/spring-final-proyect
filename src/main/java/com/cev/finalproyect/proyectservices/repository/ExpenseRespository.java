package com.cev.finalproyect.proyectservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Expense;

public interface ExpenseRespository extends JpaRepository<Expense, Long>{

}
