package com.backend.project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "employee", schema = "project", catalog = "")
public class EmployeeEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "email")
    private String email;
    @Basic@Column(name = "address")
    private String address;
    @Basic@Column(name = "department_id")
    private Long departmentId;

}
