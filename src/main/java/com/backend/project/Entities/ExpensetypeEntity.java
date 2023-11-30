package com.backend.project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "expensetype", schema = "project", catalog = "")
public class ExpensetypeEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;


}
