package com.backend.project.modelDTO;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDepartmentDTO {
    private DepartmentDTO department;
    private List<EmployeeDTO> employees;
}
