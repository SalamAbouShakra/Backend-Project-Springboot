package com.backend.project.repos;


import com.backend.project.Entities.ExpenseclaimentryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseclaimentryRepo extends JpaRepository<ExpenseclaimentryEntity, Long> {

    List<ExpenseclaimentryEntity> findByExpenseClaimId(Long expenseClaimId);

    @Query("SELECT COALESCE(SUM(e.total), 0) FROM ExpenseclaimentryEntity e " +
            "WHERE e.expenseTypeId = :expenseTypeId AND e.expenseClaimId IN " +
            "(SELECT ec.id FROM ExpenseclaimEntity ec WHERE ec.employeeId = :employeeId)")
    BigDecimal getTotalAmountByTypeAndEmployee(@Param("expenseTypeId") Long expenseTypeId, @Param("employeeId") Long employeeId);
}
