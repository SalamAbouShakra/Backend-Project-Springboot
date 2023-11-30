package com.backend.project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "department", schema = "project", catalog = "")
public class DepartmentEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "name")
    private String name;


}
