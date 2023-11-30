package com.backend.project.modelDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseTypeTotalDTO {

    private String expenseType;
    private BigDecimal totalAmount;



    public ExpenseTypeTotalDTO(String expenseType, BigDecimal totalAmount) {
        this.expenseType = expenseType;
        this.totalAmount = totalAmount;
    }
}
