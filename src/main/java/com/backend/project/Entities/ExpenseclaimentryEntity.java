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
@Table(name = "expenseclaimentry", schema = "project", catalog = "")
public class ExpenseclaimentryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "date")
    private Date date;
    @Basic@Column(name = "expense_type_id")
    private Long expenseTypeId;
    @Basic@Column(name = "expense_claim_id")
    private Long expenseClaimId;
    @Basic@Column(name = "description")
    private String description;
    @Basic@Column(name = "total")
    private BigDecimal total;


}
