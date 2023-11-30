package com.backend.project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "expenseclaim", schema = "project", catalog = "")
public class ExpenseclaimEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "date")
    private Date date;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;  // Initialize with BigDecimal.ZERO
    @Basic@Column(name = "status")
    private String status;
    @Basic@Column(name = "employee_id")
    private Long employeeId;


}
