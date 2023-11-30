package com.backend.project.repos;

import com.backend.project.Entities.DepartmentEntity;
import com.backend.project.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByDepartmentId(Long departmentId);
}
