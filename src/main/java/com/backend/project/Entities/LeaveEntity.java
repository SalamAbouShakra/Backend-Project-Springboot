package com.backend.project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "leavetable", schema = "project", catalog = "")
public class LeaveEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "leave_type_id")
    private Long leaveTypeId;
    @Basic@Column(name = "leave_from")
    private Date leaveFrom;
    @Basic@Column(name = "leave_to")
    private Date leaveTo;
    @Basic@Column(name = "number_of_days")
    private int numberOfDays;
    @Basic@Column(name = "note")
    private String note;
    @Basic@Column(name = "employee_id")
    private Long employeeId;


}
