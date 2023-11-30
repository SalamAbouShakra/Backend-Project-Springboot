package com.backend.project.repos;

import com.backend.project.Entities.EmployeeEntity;
import com.backend.project.Entities.LeavetypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeavetypeRepo extends JpaRepository<LeavetypeEntity, Long> {
}
