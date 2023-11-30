package com.backend.project.modelDTO;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EmployeeDTO {
    private long id;

    private String name;

    private String email;

    private String address;

    private Long departmentId;
}
