package com.backend.project.repos;


import com.backend.project.Entities.ExpenseclaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseclaimRepo extends JpaRepository<ExpenseclaimEntity, Long> {
}
