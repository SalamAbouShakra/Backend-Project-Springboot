package com.backend.project.modelDTO;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class ExpenseclaimDTO {
    private long id;

    private Date date;

    private String description;

    private BigDecimal total;

    private String status;

    private Long employeeId;


}
