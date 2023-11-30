package com.backend.project.modelDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class ExpenseclaimentryDTO {
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    private Long expenseTypeId;

    private Long expenseClaimId;

    private String description;

    private BigDecimal total;



}
